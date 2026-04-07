package com.minersecho.plugin.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MinerEchoCommand extends AbstractPlayerCommand {

    public MinerEchoCommand() {
        super("minerecho", "MinerEcho test command");
    }

    @Override
    protected void execute(
            CommandContext ctx,
            Store<EntityStore> entityStore,
            Ref<EntityStore> playerEntityRef,
            PlayerRef playerRef,
            World world
    ) {
        try {
            TransformComponent transform = entityStore.getComponent(
                    playerEntityRef,
                    TransformComponent.getComponentType()
            );

            if (transform == null) {
                ctx.sendMessage(Message.raw("[MinerEcho] TransformComponent not found."));
                return;
            }

            Vector3d position = transform.getPosition();
            Vector3f rotation = transform.getRotation();

            double yawDeg = normalizeDegrees(Math.toDegrees(rotation.y));

            int dx = 0;
            int dz = 0;

            if (yawDeg >= 315 || yawDeg < 45) {
                dz = -1;
            } else if (yawDeg >= 45 && yawDeg < 135) {
                dx = -1;
            } else if (yawDeg >= 135 && yawDeg < 225) {
                dz = 1;
            } else {
                dx = 1;
            }

            int baseX = (int) Math.floor(position.x);
            int baseY = (int) Math.floor(position.y);
            int baseZ = (int) Math.floor(position.z);

            int targetX = baseX + dx;
            int targetY = baseY;
            int targetZ = baseZ + dz;

            int topY = targetY + 1;

            int targetX2 = targetX + dx;
            int targetZ2 = targetZ + dz;

            long chunkIndex = com.hypixel.hytale.math.util.ChunkUtil.indexChunkFromBlock(targetX, targetZ);

            double finalYawDeg = yawDeg;

            world.getChunkAsync(chunkIndex).thenAcceptAsync(chunk -> {
                if (chunk == null) {
                    ctx.sendMessage(Message.raw("[MinerEcho] Target chunk is null."));
                    return;
                }

                int oldBottomBlockId = chunk.getBlock(targetX, targetY, targetZ);
                boolean removedBottom = chunk.setBlock(targetX, targetY, targetZ, 0);

                int oldTopBlockId = chunk.getBlock(targetX, topY, targetZ);
                boolean removedTop = chunk.setBlock(targetX, topY, targetZ, 0);

                int oldBottomBlockId2 = chunk.getBlock(targetX2, targetY, targetZ2);
                boolean removedBottom2 = chunk.setBlock(targetX2, targetY, targetZ2, 0);
                
                int oldTopBlockId2 = chunk.getBlock(targetX2, topY, targetZ);
                boolean removedTop2 = chunk.setBlock(targetX2, topY, targetZ2, 0);
            

                ctx.sendMessage(Message.raw("[MinerEcho] Blocks removed"));

                CompletableFuture
                        .runAsync(() -> {}, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                        .thenRunAsync(() -> {
                            WorldChunk loadedChunk = world.getChunkIfLoaded(chunkIndex);
                            if (loadedChunk == null) {
                                ctx.sendMessage(Message.raw("[MinerEcho] Restore skipped - chunk not loaded."));
                                return;
                            }

                            boolean restoredBottom = loadedChunk.setBlock(targetX, targetY, targetZ, oldBottomBlockId);
                            boolean restoredTop = loadedChunk.setBlock(targetX, topY, targetZ, oldTopBlockId);

                            boolean restoredBottom2 = chunk.setBlock(targetX2, targetY, targetZ2, oldBottomBlockId2);
                            boolean restoredTop2 = chunk.setBlock(targetX2, topY, targetZ2, oldTopBlockId2);

                            ctx.sendMessage(Message.raw("[MinerEcho] Blocks restored"));
                        }, world);

            }, world).exceptionally(throwable -> {
                ctx.sendMessage(Message.raw("[MinerEcho] Error: " + throwable.getMessage()));
                throwable.printStackTrace();
                return null;
            });

        } catch (Exception e) {
            ctx.sendMessage(Message.raw("[MinerEcho] Exception: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    private static double normalizeDegrees(double deg) {
        deg = deg % 360.0;
        if (deg < 0) {
            deg += 360.0;
        }
        return deg;
    }
}