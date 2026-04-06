package com.minersecho;

import java.util.ArrayList;
import java.util.List;

public final class Scanner {
    public static ScanResult scanForCavity(World world, Player player, boolean onCooldown) {
        if (onCooldown) {
            return new ScanResult(false, "cooldown", null, List.of(), List.of());
        }
        if (!UndergroundChecker.isUnderground(world, player.position())) {
            return new ScanResult(false, "invalid_activation", null, List.of(), List.of());
        }

        Direction direction = Direction.fromYaw(player.yaw);
        List<Vec3> pattern = ScanPatterns.get(direction);
        Vec3 origin = player.scanOrigin();
        List<Vec3> checked = new ArrayList<>();

        for (Vec3 offset : pattern) {
            Vec3 pos = origin.add(offset);
            checked.add(pos);
            if (!world.isAir(pos)) continue;
            List<Vec3> cluster = CavityDetector.getMeaningfulAirCluster(world, pos);
            if (!cluster.isEmpty()) {
                return new ScanResult(true, "hit", direction, cluster, checked);
            }
        }

        return new ScanResult(false, "miss", direction, List.of(), checked);
    }
}
