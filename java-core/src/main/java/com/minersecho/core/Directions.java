package com.minersecho.core;

import java.util.List;

public final class Directions {
    private static final List<Direction> ORDER = List.of(
            Direction.N,
            Direction.NE,
            Direction.E,
            Direction.SE,
            Direction.S,
            Direction.SW,
            Direction.W,
            Direction.NW
    );

    private Directions() {}

    public static double normalizeYaw(double yaw) {
        double normalized = yaw % 360.0;
        return normalized < 0 ? normalized + 360.0 : normalized;
    }

    public static Direction yawToDirection(double yaw) {
        double normalized = normalizeYaw(yaw);
        int index = (int) ((normalized + 22.5) / 45.0) % 8;
        return ORDER.get(index);
    }
}
