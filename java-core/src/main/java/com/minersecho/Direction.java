package com.minersecho;

public enum Direction {
    N, NE, E, SE, S, SW, W, NW;

    public static Direction fromYaw(double yaw) {
        double normalized = yaw % 360.0;
        if (normalized < 0) normalized += 360.0;
        Direction[] order = {N, NE, E, SE, S, SW, W, NW};
        int index = (int) Math.floor((normalized + 22.5) / 45.0) % 8;
        return order[index];
    }
}
