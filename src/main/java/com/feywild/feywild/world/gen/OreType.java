package com.feywild.feywild.world.gen;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Block;

public enum OreType {
    FEY_GEM_ORE(ModBlocks.FEY_GEM_BLOCK.get(),3,11, 45, 4);

    private final Block block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;
    private final int spawnWeight;

    OreType(Block block, int maxVeinSize, int minHeight, int maxHeight, int spawnWeight)
    {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.spawnWeight = spawnWeight;
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

    public static OreType get(Block block){

        for(OreType ore : values()) {

            if(block == ore.block){

                return ore;
            }
        }

        return null;
    }

    public int getSpawnWeight() {
        return spawnWeight;
    }
}
