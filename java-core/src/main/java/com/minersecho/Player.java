package com.minersecho;

public final class Player {
    public final String playerId;
    public final int x;
    public final int y;
    public final int z;
    public final double yaw;

    public Player(String playerId, int x, int y, int z, double yaw) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
    }

    public Vec3 position() {
        return new Vec3(x, y, z);
    }

    public Vec3 scanOrigin() {
        return position();
    }
}
