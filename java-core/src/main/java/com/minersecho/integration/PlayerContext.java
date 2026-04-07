package com.minersecho.integration;

public record PlayerContext(
    String playerId,
    int x,
    int y,
    int z,
    float yaw
) {
}