package com.minersecho.core;

import java.util.List;

public record ScanResult(
        boolean success,
        String reason,
        Direction direction,
        List<Vec3i> clusterPositions,
        List<Vec3i> checkedPositions
) {}
