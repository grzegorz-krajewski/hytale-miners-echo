package com.minersecho.core;

public record Vec3i(int x, int y, int z) {
    public Vec3i add(Vec3i other) {
        return new Vec3i(x + other.x(), y + other.y(), z + other.z());
    }
}
