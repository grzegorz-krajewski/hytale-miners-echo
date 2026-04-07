package com.minersecho.integration;

import com.minersecho.core.CooldownTracker;
import com.minersecho.core.Player;
import com.minersecho.core.ScanResult;
import com.minersecho.core.Scanner;
import com.minersecho.core.World;

public final class MinerEchoService {

    private final CooldownTracker cooldownTracker;
    private final FeedbackAdapter feedbackAdapter;

    public MinerEchoService(
        CooldownTracker cooldownTracker,
        FeedbackAdapter feedbackAdapter
    ) {
        this.cooldownTracker = cooldownTracker;
        this.feedbackAdapter = feedbackAdapter;
    }

    public ScanResult activate(
        PlayerContext playerContext,
        World world,
        double nowSeconds
    ) {
        boolean onCooldown = cooldownTracker.isOnCooldown(playerContext.playerId(), nowSeconds);

        Player player = new Player(
            playerContext.playerId(),
            playerContext.x(),
            playerContext.y(),
            playerContext.z(),
            playerContext.yaw()
        );

        ScanResult result = Scanner.scanForCavity(world, player, onCooldown);

        if ("hit".equals(result.reason()) || "miss".equals(result.reason())) {
            cooldownTracker.startCooldown(playerContext.playerId(), nowSeconds);
        }

        String direction = result.direction() != null ? result.direction().name() : null;
        String message = ActivationResultMapper.toMessage(result.reason(), direction);

        if ("cooldown".equals(result.reason())) {
            double remaining = cooldownTracker.remainingSeconds(playerContext.playerId(), nowSeconds);
            message = message + String.format(" %.1fs remaining.", remaining);
        }

        feedbackAdapter.sendMessage(playerContext.playerId(), message);

        return result;
    }
}