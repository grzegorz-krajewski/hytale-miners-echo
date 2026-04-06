package com.minersecho.core;

import java.util.HashMap;
import java.util.Map;

public final class CooldownTracker {
    private final double durationSeconds;
    private final Map<String, Double> readyAtByPlayer = new HashMap<>();

    public CooldownTracker(double durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public boolean isOnCooldown(String playerId, double now) {
        return now < readyAtByPlayer.getOrDefault(playerId, 0.0);
    }

    public void startCooldown(String playerId, double now) {
        readyAtByPlayer.put(playerId, now + durationSeconds);
    }

    public double remainingSeconds(String playerId, double now) {
        return Math.max(0.0, readyAtByPlayer.getOrDefault(playerId, 0.0) - now);
    }
}
