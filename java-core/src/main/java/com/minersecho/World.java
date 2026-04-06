package com.minersecho;

import java.util.HashMap;
import java.util.Map;

public final class World {
    private final Map<Vec3, BlockType> blocks = new HashMap<>();
    private final BlockType defaultBlock;

    public World(BlockType defaultBlock) {
        this.defaultBlock = defaultBlock;
    }

    public BlockType getBlock(Vec3 pos) {
        return blocks.getOrDefault(pos, defaultBlock);
    }

    public void setBlock(Vec3 pos, BlockType type) {
        blocks.put(pos, type);
    }

    public boolean isAir(Vec3 pos) {
        return getBlock(pos) == BlockType.AIR;
    }

    public boolean isSolid(Vec3 pos) {
        return !isAir(pos);
    }
}
