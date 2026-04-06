package com.minersecho;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CavityDetector {
    public static final int MIN_CLUSTER_SIZE = 3;
    public static final int MAX_SEARCH_SIZE = 12;

    public static List<Vec3> getMeaningfulAirCluster(World world, Vec3 start) {
        if (!world.isAir(start)) return List.of();

        Set<Vec3> visited = new HashSet<>();
        ArrayDeque<Vec3> queue = new ArrayDeque<>();
        List<Vec3> cluster = new ArrayList<>();
        queue.add(start);

        while (!queue.isEmpty() && cluster.size() < MAX_SEARCH_SIZE) {
            Vec3 pos = queue.removeFirst();
            if (!visited.add(pos)) continue;
            if (!world.isAir(pos)) continue;
            cluster.add(pos);
            for (Vec3 n : neighbors(pos)) {
                if (!visited.contains(n)) queue.addLast(n);
            }
        }

        return cluster.size() >= MIN_CLUSTER_SIZE ? cluster : List.of();
    }

    private static List<Vec3> neighbors(Vec3 p) {
        return List.of(
            new Vec3(p.x - 1, p.y, p.z),
            new Vec3(p.x + 1, p.y, p.z),
            new Vec3(p.x, p.y - 1, p.z),
            new Vec3(p.x, p.y + 1, p.z),
            new Vec3(p.x, p.y, p.z - 1),
            new Vec3(p.x, p.y, p.z + 1)
        );
    }
}
