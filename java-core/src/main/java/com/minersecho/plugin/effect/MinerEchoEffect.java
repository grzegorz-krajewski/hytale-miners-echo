package com.minersecho.plugin.effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.protocol.packets.world.PlaySoundEvent2D;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.SetBlockSettings;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.Message;

public final class MinerEchoEffect {

    private static final int AIR_BLOCK_ID = 0;

    private static final int WIDTH = 3;   // szerokość
    private static final int HEIGHT = 3;  // wysokość
    private static final int DEPTH = 3;   // długość do przodu
    private static final int Y_OFFSET = -1; // o zjeden poziom w dół

    private static final long RESTORE_DELAY_MS = 2000L;
    private static final long COOLDOWN_MS = 2000L;

    private static volatile long lastActivationMs = 0L;

    private MinerEchoEffect() {
    }

    public static void activateSonar(
        PlayerRef playerRef,
        Store<EntityStore> entityStore,
        World world,
        Player player
    ) {
        if (playerRef == null) {
            world.getLogger().at(Level.WARNING).log("[MinerEcho] playerRef is null.");
            return;
        }

        Transform transform = playerRef.getTransform();
        if (transform == null) {
            world.getLogger().at(Level.WARNING).log("[MinerEcho] playerRef transform is null.");
            return;
        }

        activateSonar(
            transform.getPosition(),
            transform.getRotation(),
            world,
            player
        );
    }

    public static void activateSonar(
        Ref<EntityStore> entityRef,
        Store<EntityStore> entityStore,
        World world,
        Player player
    ) {
        try {
            TransformComponent transform = entityStore.getComponent(
                entityRef,
                TransformComponent.getComponentType()
            );

            if (transform == null) {
                world.getLogger().at(Level.WARNING).log("[MinerEcho] TransformComponent is null.");
                return;
            }

            activateSonar(
                transform.getPosition(),
                transform.getRotation(),
                world,
                player
            );

        } catch (Throwable throwable) {
            world.getLogger().at(Level.SEVERE).withCause(throwable)
                .log("[MinerEcho] Fatal error in entityRef sonar.");
        }
    }

    private static void activateSonar(
        Vector3d pos,
        Vector3f rot,
        World world,
        Player player
    ) {
        try {
            long now = System.currentTimeMillis();
            if (now - lastActivationMs < COOLDOWN_MS) {
                world.getLogger().at(Level.INFO).log("[MinerEcho] Cooldown active.");
                return;
            }
            lastActivationMs = now;

            playActivationSound(player, world);

            double yawRad = Math.toRadians(rot.y);
            int dx = (int) Math.round(Math.sin(yawRad));
            int dz = (int) Math.round(Math.cos(yawRad));

            if (dx == 0 && dz == 0) {
                dz = 1;
            }

            int centerX = (int) Math.floor(pos.x) + dx;
            int centerY = (int) Math.floor(pos.y) + Y_OFFSET;
            int centerZ = (int) Math.floor(pos.z) + dz;

            int perpX = -dz;
            int perpZ = dx;

            world.getLogger().at(Level.INFO).log(
                "[MinerEcho] SONAR DEBUG pos=(" + pos.x + "," + pos.y + "," + pos.z + ")"
                    + " rot=(" + rot.x + "," + rot.y + "," + rot.z + ")"
                    + " yawRad=" + yawRad
                    + " forward=(" + dx + "," + dz + ")"
                    + " center=" + centerX + "," + centerY + "," + centerZ
            );

            List<BlockSnapshot> changedBlocks = new ArrayList<>();
            int scanned = 0;

            for (int forward = 0; forward < DEPTH; forward++) {
                for (int side = -(WIDTH / 2); side <= (WIDTH / 2); side++) {
                    for (int up = 0; up < HEIGHT; up++) {
                        int targetX = centerX + (dx * forward) + (perpX * side);
                        int targetY = centerY + up;
                        int targetZ = centerZ + (dz * forward) + (perpZ * side);

                        scanned++;

                        long chunkIndex = com.hypixel.hytale.math.util.ChunkUtil
                            .indexChunkFromBlock(targetX, targetZ);

                        WorldChunk chunk = world.getChunkIfLoaded(chunkIndex);
                        if (chunk == null) {
                            continue;
                        }

                        int oldBlockId = chunk.getBlock(targetX, targetY, targetZ);
                        if (oldBlockId == AIR_BLOCK_ID) {
                            continue;
                        }

                        BlockType oldBlockType = BlockType.getAssetMap().getAsset(oldBlockId);
                        if (oldBlockType == null) {
                            continue;
                        }

                        // pomijamy minerały
                        String typeName = oldBlockType.getId().toLowerCase(Locale.ROOT);
                        if (typeName.contains("ore") || typeName.contains("mineral")) {
                            continue;
                        }

                        boolean changed = chunk.setBlock(
                            targetX,
                            targetY,
                            targetZ,
                            AIR_BLOCK_ID,
                            BlockType.getAssetMap().getAsset(AIR_BLOCK_ID),
                            SetBlockSettings.NONE,
                            0,
                            0
                        );

                        if (changed) {
                            changedBlocks.add(new BlockSnapshot(
                                targetX,
                                targetY,
                                targetZ,
                                oldBlockId,
                                oldBlockType
                            ));
                        }
                    }
                }
            }

            world.getLogger().at(Level.INFO).log(
                "[MinerEcho] SONAR DEBUG scanned=" + scanned
                    + " removed=" + changedBlocks.size()
            );

            CompletableFuture
                .delayedExecutor(RESTORE_DELAY_MS, TimeUnit.MILLISECONDS)
                .execute(() -> world.execute(() -> restoreBlocks(world, changedBlocks)));

        } catch (Throwable throwable) {
            world.getLogger().at(Level.SEVERE).withCause(throwable)
                .log("[MinerEcho] Fatal error in sonar.");
        }
    }

    private static void restoreBlocks(World world, List<BlockSnapshot> changedBlocks) {
        int restored = 0;

        for (BlockSnapshot snapshot : changedBlocks) {
            long chunkIndex = com.hypixel.hytale.math.util.ChunkUtil
                .indexChunkFromBlock(snapshot.x, snapshot.z);

            WorldChunk chunk = world.getChunkIfLoaded(chunkIndex);
            if (chunk == null) {
                continue;
            }

            boolean ok = chunk.setBlock(
                snapshot.x,
                snapshot.y,
                snapshot.z,
                snapshot.blockId,
                snapshot.blockType,
                SetBlockSettings.NONE,
                0,
                0
            );

            if (ok) {
                restored++;
            }
        }

        world.getLogger().at(Level.INFO).log(
            "[MinerEcho] SONAR DEBUG restored=" + restored + "/" + changedBlocks.size()
        );
    }

    private static void playActivationSound(Player player, World world) {
        try {
            if (player == null || player.getPlayerConnection() == null) {
                world.getLogger().at(Level.INFO).log("[MinerEcho] No player connection for sound.");
                return;
            }

            SoundEvent soundEvent = findSoundEvent(
                "SFX_Env_Emit_Fluid_Water",
                "SFX_Water_MoveIn",
                "SFX_Test_Blip_A"
            );

            if (soundEvent == null) {
                world.getLogger().at(Level.WARNING).log("[MinerEcho] No sound event found.");
                return;
            }

            player.getPlayerConnection().write(
                new PlaySoundEvent2D(
                    SoundEvent.getAssetMap().getIndex(soundEvent.getId()),
                    SoundCategory.SFX,
                    1.0f,
                    1.0f
                )
            );

            world.getLogger().at(Level.INFO).log("[MinerEcho] Played sound: " + soundEvent.getId());

        } catch (Throwable throwable) {
            world.getLogger().at(Level.WARNING).withCause(throwable)
                .log("[MinerEcho] Failed to play sound.");
        }
    }

    private static SoundEvent findSoundEvent(String... ids) {
        for (String id : ids) {
            SoundEvent soundEvent = SoundEvent.getAssetMap().getAsset(id);
            if (soundEvent != null) {
                return soundEvent;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    private static boolean looksLikeStone(String blockId) {
        if (blockId == null) {
            return false;
        }

        String id = blockId.toLowerCase(Locale.ROOT);
        return id.contains("stone")
            || id.contains("rock")
            || id.contains("dirt")
            || id.contains("gravel")
            || id.contains("sandstone")
            || id.contains("soil");
    }

    private static final class BlockSnapshot {
        final int x;
        final int y;
        final int z;
        final int blockId;
        final BlockType blockType;

        BlockSnapshot(int x, int y, int z, int blockId, BlockType blockType) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.blockId = blockId;
            this.blockType = blockType;
        }
    }

    public static void activateSonarAtTargetBlock(
        int targetX,
        int targetY,
        int targetZ,
        World world,
        Player player
    ) {
        try {
            long now = System.currentTimeMillis();
            if (now - lastActivationMs < COOLDOWN_MS) {
                world.getLogger().at(Level.INFO).log("[MinerEcho] Cooldown active.");
                return;
            }
            lastActivationMs = now;

            playActivationSound(player, world);

            // zaczynamy od klikniętego bloku, 1 niżej
            int startX = targetX - 1;
            int startY = targetY - 1;
            int startZ = targetZ - 1;

            List<BlockSnapshot> changedBlocks = new ArrayList<>();
            int scanned = 0;
            int finded = 0;

            world.getLogger().at(Level.INFO).log(
                "[MinerEcho] TARGET SONAR center="
                    + targetX + "," + targetY + "," + targetZ
                    + " start=" + startX + "," + startY + "," + startZ
            );

            for (int x = startX; x < startX + 3; x++) {
                for (int y = startY; y < startY + 3; y++) {
                    for (int z = startZ; z < startZ + 3; z++) {
                        scanned++;

                        long chunkIndex = com.hypixel.hytale.math.util.ChunkUtil
                            .indexChunkFromBlock(x, z);

                        WorldChunk chunk = world.getChunkIfLoaded(chunkIndex);
                        if (chunk == null) {
                            continue;
                        }

                        int oldBlockId = chunk.getBlock(x, y, z);
                        if (oldBlockId == AIR_BLOCK_ID) {
                            continue;
                        }

                        BlockType oldBlockType = BlockType.getAssetMap().getAsset(oldBlockId);
                        if (oldBlockType == null) {
                            continue;
                        }

                        // pomijamy minerały
                        String typeName = oldBlockType.getId().toLowerCase(Locale.ROOT);
                        if (typeName.contains("ore") || typeName.contains("mineral")) {
                            finded++;
                            continue;
                        }

                        boolean changed = chunk.setBlock(
                            x,
                            y,
                            z,
                            AIR_BLOCK_ID,
                            BlockType.getAssetMap().getAsset(AIR_BLOCK_ID),
                            SetBlockSettings.NONE,
                            0,
                            0
                        );

                        if (changed) {
                            changedBlocks.add(new BlockSnapshot(
                                x,
                                y,
                                z,
                                oldBlockId,
                                oldBlockType
                            ));
                        }
                    }
                }
            }

            world.getLogger().at(Level.INFO).log(
                "[MinerEcho] TARGET SONAR scanned=" + scanned
                    + " removed=" + changedBlocks.size()
            );

            if (finded > 0) {
                playActivationSound(player, world);
                player.sendMessage(Message.raw("[MinerEcho] Finded " + finded + " artifacts."));
            }
            
            CompletableFuture
                .delayedExecutor(RESTORE_DELAY_MS, TimeUnit.MILLISECONDS)
                .execute(() -> world.execute(() -> restoreBlocks(world, changedBlocks)));

        } catch (Throwable throwable) {
            world.getLogger().at(Level.SEVERE).withCause(throwable)
                .log("[MinerEcho] Fatal error in target sonar.");
        }
    }
}