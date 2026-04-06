package com.minersecho.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class World {
    private final Map<Vec3i, BlockType> blocks = new HashMap<>();
    private final BlockType defaultBlock;

    public World(BlockType defaultBlock) {
        this.defaultBlock = defaultBlock;
    }

    public BlockType getBlock(Vec3i position) {
        return blocks.getOrDefault(position, defaultBlock);
    }

    public void setBlock(Vec3i position, BlockType blockType) {
        blocks.put(position, blockType);
    }

    public boolean isAir(Vec3i position) {
        return getBlock(position) == BlockType.AIR;
    }

    public boolean isSolid(Vec3i position) {
        return !isAir(position);
    }

    public void fillAir(List<Vec3i> positions) {
        for (Vec3i position : positions) {
            setBlock(position, BlockType.AIR);
        }
    }
}
