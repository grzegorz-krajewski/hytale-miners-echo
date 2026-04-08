package com.minersecho.plugin.interaction;

import javax.annotation.Nonnull;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.minersecho.plugin.effect.MinerEchoEffect;

public class MinerEchoArtifactInteraction extends SimpleInstantInteraction {

    public static final BuilderCodec<MinerEchoArtifactInteraction> CODEC =
        BuilderCodec.builder(
            MinerEchoArtifactInteraction.class,
            MinerEchoArtifactInteraction::new,
            SimpleInstantInteraction.CODEC
        ).build();

    public MinerEchoArtifactInteraction() {
    }

    @Override
    protected void firstRun(
        @Nonnull InteractionType interactionType,
        @Nonnull InteractionContext interactionContext,
        @Nonnull CooldownHandler cooldownHandler
    ) {
        try {
            System.out.println("[MinerEcho] Artifact interaction fired");

            CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
            if (commandBuffer == null) {
                interactionContext.getState().state = InteractionState.Failed;
                System.out.println("[MinerEcho] CommandBuffer is null.");
                return;
            }

            World world = commandBuffer.getExternalData().getWorld();
            Store<EntityStore> store = commandBuffer.getExternalData().getStore();
            Ref<EntityStore> ref = interactionContext.getEntity();

            if (world == null || store == null || ref == null) {
                interactionContext.getState().state = InteractionState.Failed;
                System.out.println("[MinerEcho] world/store/ref is null.");
                return;
            }

            Player player = commandBuffer.getComponent(ref, Player.getComponentType());
            if (player == null) {
                interactionContext.getState().state = InteractionState.Failed;
                System.out.println("[MinerEcho] Player is null.");
                return;
            }

            BlockPosition targetBlock = interactionContext.getTargetBlock();
            if (targetBlock == null) {
                interactionContext.getState().state = InteractionState.Failed;
                player.sendMessage(Message.raw("[MinerEcho] No target block."));
                System.out.println("[MinerEcho] No target block from right click.");
                return;
            }

            System.out.println(
                "[MinerEcho] targetBlock="
                    + targetBlock.x + ","
                    + targetBlock.y + ","
                    + targetBlock.z
            );

            MinerEchoEffect.activateSonarAtTargetBlock(
                targetBlock.x,
                targetBlock.y,
                targetBlock.z,
                world,
                player
            );

            player.sendMessage(Message.raw("[MinerEcho] Artifact activated."));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}