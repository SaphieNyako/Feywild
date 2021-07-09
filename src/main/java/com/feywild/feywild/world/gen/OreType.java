package com.feywild.feywild.world.gen;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.util.Configs.Config;
import net.minecraft.block.Block;

public enum OreType {
    FEY_GEM_ORE(ModBlocks.FEY_GEM_BLOCK.get(), Config.FEY_GEM_CONFIG.getCachedSize(), Config.FEY_GEM_CONFIG.getCachedMinHeight(), Config.FEY_GEM_CONFIG.getCachedMaxHeight(), Config.FEY_GEM_CONFIG.getCachedWeight());


    private final Block block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;
    private final int spawnWeight;

    OreType(Block block, int maxVeinSize, int minHeight, int maxHeight, int spawnWeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.spawnWeight = spawnWeight;
    }

    public static OreType get(Block block) {

        for (OreType ore : values()) {

            if (block == ore.block) {

                return ore;
            }
        }

        return null;
    }

    public Block getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getSpawnWeight() {
        return spawnWeight;
    }
}
