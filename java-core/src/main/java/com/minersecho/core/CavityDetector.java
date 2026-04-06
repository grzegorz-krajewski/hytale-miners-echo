package com.minersecho.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CavityDetector {
    public static final int MIN_MEANINGFUL_CLUSTER_SIZE = 3;
    public static final int MAX_CLUSTER_SEARCH_SIZE = 12;

    private CavityDetector() {}

    public static List<Vec3i> getMeaningfulAirCluster(World world, Vec3i startPosition) {
        List<Vec3i> cluster = getAirCluster(world, startPosition, MAX_CLUSTER_SEARCH_SIZE);
        return cluster.size() >= MIN_MEANINGFUL_CLUSTER_SIZE ? cluster : List.of();
    }

    private static List<Vec3i> getAirCluster(World world, Vec3i startPosition, int maxSearchSize) {
        if (!world.isAir(startPosition)) {
            return List.of();
        }

        Set<Vec3i> visited = new HashSet<>();
        ArrayDeque<Vec3i> queue = new ArrayDeque<>();
        List<Vec3i> cluster = new ArrayList<>();
        queue.add(startPosition);

        while (!queue.isEmpty() && cluster.size() < maxSearchSize) {
            Vec3i position = queue.removeFirst();
            if (!visited.add(position)) {
                continue;
            }
            if (!world.isAir(position)) {
                continue;
            }
            cluster.add(position);
            for (Vec3i neighbor : get6Neighbors(position)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        return cluster;
    }

    private static List<Vec3i> get6Neighbors(Vec3i p) {
        return List.of(
                new Vec3i(p.x() - 1, p.y(), p.z()),
                new Vec3i(p.x() + 1, p.y(), p.z()),
                new Vec3i(p.x(), p.y() - 1, p.z()),
                new Vec3i(p.x(), p.y() + 1, p.z()),
                new Vec3i(p.x(), p.y(), p.z() - 1),
                new Vec3i(p.x(), p.y(), p.z() + 1)
        );
    }
}
