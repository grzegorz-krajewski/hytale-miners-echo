package com.minersecho.core;

public record Player(String playerId, int x, int y, int z, double yaw) {
    public Vec3i position() {
        return new Vec3i(x, y, z);
    }

    public Vec3i scanOrigin() {
        return new Vec3i(x, y, z);
    }
}
