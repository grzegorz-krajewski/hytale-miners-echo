package com.minersecho.plugin;

import com.hypixel.hytale.common.plugin.PluginManifest;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.minersecho.plugin.command.MinerEchoCommand;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.minersecho.plugin.interaction.MinerEchoArtifactInteraction;

public final class MinerEchoPlugin extends JavaPlugin {

    public static final PluginManifest MANIFEST =
        PluginManifest.corePlugin(MinerEchoPlugin.class)
            .description("Miner's Echo prototype plugin")
            .build();

    public MinerEchoPlugin(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCodecRegistry(Interaction.CODEC)
            .register(
                "minersecho_artifact_use",
                MinerEchoArtifactInteraction.class,
                MinerEchoArtifactInteraction.CODEC
            );

        System.out.println("[MinerEcho] Artifact interaction registered.");
        getCommandRegistry().registerCommand(new MinerEchoCommand());
        System.out.println("[MinerEcho] Plugin loaded successfully.");
        System.out.println("[MinerEcho] /minerecho command registered.");
    }
}