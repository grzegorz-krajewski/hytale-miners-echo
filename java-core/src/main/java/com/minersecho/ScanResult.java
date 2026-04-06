package com.minersecho;

import java.util.List;

public record ScanResult(
    boolean success,
    String reason,
    Direction direction,
    List<Vec3> clusterPositions,
    List<Vec3> checkedPositions
) {}
