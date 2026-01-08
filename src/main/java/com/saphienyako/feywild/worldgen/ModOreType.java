package com.saphienyako.feywild.worldgen;


import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.block.Block;
import java.util.function.Supplier;


public enum ModOreType {
    FEY_GEM_ORE(() -> ModBlocks.FEY_GEM_ORE.get(), 8, 25, 50);

    private final Supplier<Block> block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;

    ModOreType(Supplier<Block> block, int maxVeinSize, int minHeight, int maxHeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Block getBlock() {
        return block.get();
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

    public static ModOreType get(Block block) {
        for (ModOreType ore : values()) {
            if (block == ore.getBlock()) {
                return ore;
            }
        }
        return null;
    }
}
