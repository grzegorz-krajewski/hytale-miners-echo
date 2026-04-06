package com.minersecho.core;

import java.util.ArrayList;
import java.util.List;

public final class Scanner {
    private Scanner() {}

    public static ScanResult scanForCavity(World world, Player player, boolean onCooldown) {
        if (onCooldown) {
            return new ScanResult(false, "cooldown", null, List.of(), List.of());
        }

        if (!UndergroundRules.isUnderground(world, player.position())) {
            return new ScanResult(false, "invalid_activation", null, List.of(), List.of());
        }

        Direction direction = Directions.yawToDirection(player.yaw());
        List<Vec3i> pattern = ScanPatterns.getPattern(direction);
        Vec3i origin = player.scanOrigin();
        List<Vec3i> checkedPositions = new ArrayList<>();

        for (Vec3i offset : pattern) {
            Vec3i position = origin.add(offset);
            checkedPositions.add(position);

            if (!world.isAir(position)) {
                continue;
            }

            List<Vec3i> cluster = CavityDetector.getMeaningfulAirCluster(world, position);
            if (!cluster.isEmpty()) {
                return new ScanResult(true, "hit", direction, cluster, checkedPositions);
            }
        }

        return new ScanResult(false, "miss", direction, List.of(), checkedPositions);
    }
}
