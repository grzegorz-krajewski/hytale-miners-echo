package com.minersecho.core;

import java.util.ArrayList;
import java.util.List;

public final class UndergroundRules {
    public static final int MAX_OVERHEAD_CHECK_HEIGHT = 6;
    public static final int MIN_SOLID_BLOCKS_OVERHEAD = 3;

    private UndergroundRules() {}

    public static boolean isUnderground(World world, Vec3i position) {
        return !isExposedToSky(world, position) && hasSolidCoverOverhead(world, position);
    }

    public static boolean isExposedToSky(World world, Vec3i position) {
        return getBlocksAbove(position, MAX_OVERHEAD_CHECK_HEIGHT)
                .stream()
                .allMatch(world::isAir);
    }

    public static boolean hasSolidCoverOverhead(World world, Vec3i position) {
        long solidCount = getBlocksAbove(position, MAX_OVERHEAD_CHECK_HEIGHT)
                .stream()
                .filter(world::isSolid)
                .count();
        return solidCount >= MIN_SOLID_BLOCKS_OVERHEAD;
    }

    private static List<Vec3i> getBlocksAbove(Vec3i position, int height) {
        List<Vec3i> result = new ArrayList<>();
        for (int offset = 1; offset <= height; offset++) {
            result.add(new Vec3i(position.x(), position.y() + offset, position.z()));
        }
        return result;
    }
}
