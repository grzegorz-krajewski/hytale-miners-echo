package com.minersecho;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ScanPatterns {
    private static final Map<Direction, List<Vec3>> SCAN_PATTERNS = new EnumMap<>(Direction.class);

    static {
        SCAN_PATTERNS.put(Direction.N, expand(List.of(
            new Vec3(-1,0,-1), new Vec3(0,0,-1), new Vec3(1,0,-1),
            new Vec3(-1,0,-2), new Vec3(0,0,-2), new Vec3(1,0,-2),
            new Vec3(-1,0,-3), new Vec3(0,0,-3), new Vec3(1,0,-3),
            new Vec3(-1,0,-4), new Vec3(0,0,-4), new Vec3(1,0,-4),
            new Vec3(-1,0,-5), new Vec3(0,0,-5), new Vec3(1,0,-5)
        )));
        SCAN_PATTERNS.put(Direction.S, expand(List.of(
            new Vec3(-1,0,1), new Vec3(0,0,1), new Vec3(1,0,1),
            new Vec3(-1,0,2), new Vec3(0,0,2), new Vec3(1,0,2),
            new Vec3(-1,0,3), new Vec3(0,0,3), new Vec3(1,0,3),
            new Vec3(-1,0,4), new Vec3(0,0,4), new Vec3(1,0,4),
            new Vec3(-1,0,5), new Vec3(0,0,5), new Vec3(1,0,5)
        )));
        SCAN_PATTERNS.put(Direction.E, expand(List.of(
            new Vec3(1,0,-1), new Vec3(1,0,0), new Vec3(1,0,1),
            new Vec3(2,0,-1), new Vec3(2,0,0), new Vec3(2,0,1),
            new Vec3(3,0,-1), new Vec3(3,0,0), new Vec3(3,0,1),
            new Vec3(4,0,-1), new Vec3(4,0,0), new Vec3(4,0,1),
            new Vec3(5,0,-1), new Vec3(5,0,0), new Vec3(5,0,1)
        )));
        SCAN_PATTERNS.put(Direction.W, expand(List.of(
            new Vec3(-1,0,-1), new Vec3(-1,0,0), new Vec3(-1,0,1),
            new Vec3(-2,0,-1), new Vec3(-2,0,0), new Vec3(-2,0,1),
            new Vec3(-3,0,-1), new Vec3(-3,0,0), new Vec3(-3,0,1),
            new Vec3(-4,0,-1), new Vec3(-4,0,0), new Vec3(-4,0,1),
            new Vec3(-5,0,-1), new Vec3(-5,0,0), new Vec3(-5,0,1)
        )));
        SCAN_PATTERNS.put(Direction.NE, expand(List.of(
            new Vec3(0,0,-1), new Vec3(1,0,-1), new Vec3(1,0,0),
            new Vec3(1,0,-2), new Vec3(2,0,-2), new Vec3(2,0,-1),
            new Vec3(2,0,-3), new Vec3(3,0,-3), new Vec3(3,0,-2),
            new Vec3(3,0,-4), new Vec3(4,0,-4), new Vec3(4,0,-3),
            new Vec3(4,0,-5), new Vec3(5,0,-5), new Vec3(5,0,-4)
        )));
        SCAN_PATTERNS.put(Direction.NW, expand(List.of(
            new Vec3(0,0,-1), new Vec3(-1,0,-1), new Vec3(-1,0,0),
            new Vec3(-1,0,-2), new Vec3(-2,0,-2), new Vec3(-2,0,-1),
            new Vec3(-2,0,-3), new Vec3(-3,0,-3), new Vec3(-3,0,-2),
            new Vec3(-3,0,-4), new Vec3(-4,0,-4), new Vec3(-4,0,-3),
            new Vec3(-4,0,-5), new Vec3(-5,0,-5), new Vec3(-5,0,-4)
        )));
        SCAN_PATTERNS.put(Direction.SE, expand(List.of(
            new Vec3(0,0,1), new Vec3(1,0,1), new Vec3(1,0,0),
            new Vec3(1,0,2), new Vec3(2,0,2), new Vec3(2,0,1),
            new Vec3(2,0,3), new Vec3(3,0,3), new Vec3(3,0,2),
            new Vec3(3,0,4), new Vec3(4,0,4), new Vec3(4,0,3),
            new Vec3(4,0,5), new Vec3(5,0,5), new Vec3(5,0,4)
        )));
        SCAN_PATTERNS.put(Direction.SW, expand(List.of(
            new Vec3(0,0,1), new Vec3(-1,0,1), new Vec3(-1,0,0),
            new Vec3(-1,0,2), new Vec3(-2,0,2), new Vec3(-2,0,1),
            new Vec3(-2,0,3), new Vec3(-3,0,3), new Vec3(-3,0,2),
            new Vec3(-3,0,4), new Vec3(-4,0,4), new Vec3(-4,0,3),
            new Vec3(-4,0,5), new Vec3(-5,0,5), new Vec3(-5,0,4)
        )));
    }

    private static List<Vec3> expand(List<Vec3> base) {
        List<Vec3> out = new ArrayList<>();
        for (Vec3 v : base) {
            out.add(new Vec3(v.x, 0, v.z));
            out.add(new Vec3(v.x, 1, v.z));
        }
        return out;
    }

    public static List<Vec3> get(Direction direction) {
        return SCAN_PATTERNS.get(direction);
    }
}
