package com.minersecho;

public final class UndergroundChecker {
    public static final int MAX_OVERHEAD_CHECK_HEIGHT = 6;
    public static final int MIN_SOLID_BLOCKS_OVERHEAD = 3;

    public static boolean isUnderground(World world, Vec3 position) {
        return !isExposedToSky(world, position) && hasSolidCoverOverhead(world, position);
    }

    private static boolean isExposedToSky(World world, Vec3 position) {
        for (int i = 1; i <= MAX_OVERHEAD_CHECK_HEIGHT; i++) {
            if (!world.isAir(new Vec3(position.x, position.y + i, position.z))) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasSolidCoverOverhead(World world, Vec3 position) {
        int solid = 0;
        for (int i = 1; i <= MAX_OVERHEAD_CHECK_HEIGHT; i++) {
            if (world.isSolid(new Vec3(position.x, position.y + i, position.z))) {
                solid++;
            }
        }
        return solid >= MIN_SOLID_BLOCKS_OVERHEAD;
    }
}
