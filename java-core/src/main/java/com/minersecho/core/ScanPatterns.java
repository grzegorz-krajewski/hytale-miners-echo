package com.minersecho.core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ScanPatterns {
    private static final Map<Direction, List<Vec3i>> BASE = new EnumMap<>(Direction.class);
    private static final Map<Direction, List<Vec3i>> EXPANDED = new EnumMap<>(Direction.class);

    static {
        BASE.put(Direction.N, List.of(
                new Vec3i(-1,0,-1), new Vec3i(0,0,-1), new Vec3i(1,0,-1),
                new Vec3i(-1,0,-2), new Vec3i(0,0,-2), new Vec3i(1,0,-2),
                new Vec3i(-1,0,-3), new Vec3i(0,0,-3), new Vec3i(1,0,-3),
                new Vec3i(-1,0,-4), new Vec3i(0,0,-4), new Vec3i(1,0,-4),
                new Vec3i(-1,0,-5), new Vec3i(0,0,-5), new Vec3i(1,0,-5)
        ));
        BASE.put(Direction.S, List.of(
                new Vec3i(-1,0,1), new Vec3i(0,0,1), new Vec3i(1,0,1),
                new Vec3i(-1,0,2), new Vec3i(0,0,2), new Vec3i(1,0,2),
                new Vec3i(-1,0,3), new Vec3i(0,0,3), new Vec3i(1,0,3),
                new Vec3i(-1,0,4), new Vec3i(0,0,4), new Vec3i(1,0,4),
                new Vec3i(-1,0,5), new Vec3i(0,0,5), new Vec3i(1,0,5)
        ));
        BASE.put(Direction.E, List.of(
                new Vec3i(1,0,-1), new Vec3i(1,0,0), new Vec3i(1,0,1),
                new Vec3i(2,0,-1), new Vec3i(2,0,0), new Vec3i(2,0,1),
                new Vec3i(3,0,-1), new Vec3i(3,0,0), new Vec3i(3,0,1),
                new Vec3i(4,0,-1), new Vec3i(4,0,0), new Vec3i(4,0,1),
                new Vec3i(5,0,-1), new Vec3i(5,0,0), new Vec3i(5,0,1)
        ));
        BASE.put(Direction.W, List.of(
                new Vec3i(-1,0,-1), new Vec3i(-1,0,0), new Vec3i(-1,0,1),
                new Vec3i(-2,0,-1), new Vec3i(-2,0,0), new Vec3i(-2,0,1),
                new Vec3i(-3,0,-1), new Vec3i(-3,0,0), new Vec3i(-3,0,1),
                new Vec3i(-4,0,-1), new Vec3i(-4,0,0), new Vec3i(-4,0,1),
                new Vec3i(-5,0,-1), new Vec3i(-5,0,0), new Vec3i(-5,0,1)
        ));
        BASE.put(Direction.NE, List.of(
                new Vec3i(0,0,-1), new Vec3i(1,0,-1), new Vec3i(1,0,0),
                new Vec3i(1,0,-2), new Vec3i(2,0,-2), new Vec3i(2,0,-1),
                new Vec3i(2,0,-3), new Vec3i(3,0,-3), new Vec3i(3,0,-2),
                new Vec3i(3,0,-4), new Vec3i(4,0,-4), new Vec3i(4,0,-3),
                new Vec3i(4,0,-5), new Vec3i(5,0,-5), new Vec3i(5,0,-4)
        ));
        BASE.put(Direction.NW, List.of(
                new Vec3i(0,0,-1), new Vec3i(-1,0,-1), new Vec3i(-1,0,0),
                new Vec3i(-1,0,-2), new Vec3i(-2,0,-2), new Vec3i(-2,0,-1),
                new Vec3i(-2,0,-3), new Vec3i(-3,0,-3), new Vec3i(-3,0,-2),
                new Vec3i(-3,0,-4), new Vec3i(-4,0,-4), new Vec3i(-4,0,-3),
                new Vec3i(-4,0,-5), new Vec3i(-5,0,-5), new Vec3i(-5,0,-4)
        ));
        BASE.put(Direction.SE, List.of(
                new Vec3i(0,0,1), new Vec3i(1,0,1), new Vec3i(1,0,0),
                new Vec3i(1,0,2), new Vec3i(2,0,2), new Vec3i(2,0,1),
                new Vec3i(2,0,3), new Vec3i(3,0,3), new Vec3i(3,0,2),
                new Vec3i(3,0,4), new Vec3i(4,0,4), new Vec3i(4,0,3),
                new Vec3i(4,0,5), new Vec3i(5,0,5), new Vec3i(5,0,4)
        ));
        BASE.put(Direction.SW, List.of(
                new Vec3i(0,0,1), new Vec3i(-1,0,1), new Vec3i(-1,0,0),
                new Vec3i(-1,0,2), new Vec3i(-2,0,2), new Vec3i(-2,0,1),
                new Vec3i(-2,0,3), new Vec3i(-3,0,3), new Vec3i(-3,0,2),
                new Vec3i(-3,0,4), new Vec3i(-4,0,4), new Vec3i(-4,0,3),
                new Vec3i(-4,0,5), new Vec3i(-5,0,5), new Vec3i(-5,0,4)
        ));

        for (Map.Entry<Direction, List<Vec3i>> entry : BASE.entrySet()) {
            EXPANDED.put(entry.getKey(), expandToTwoLayers(entry.getValue()));
        }
    }

    private ScanPatterns() {}

    public static List<Vec3i> getPattern(Direction direction) {
        return EXPANDED.get(direction);
    }

    private static List<Vec3i> expandToTwoLayers(List<Vec3i> pattern) {
        List<Vec3i> expanded = new ArrayList<>();
        for (Vec3i p : pattern) {
            expanded.add(new Vec3i(p.x(), 0, p.z()));
            expanded.add(new Vec3i(p.x(), 1, p.z()));
        }
        return expanded;
    }
}
