package com.feywild.feywild.block.trees;

import net.minecraft.world.level.block.RotatedPillarBlock;

public class FeyStrippedLogBlock extends RotatedPillarBlock implements ILogBlock {

    private final FeyStrippedWoodBlock feyWoodStripped;
    
    public FeyStrippedLogBlock(FeyStrippedWoodBlock feyWoodStripped, Properties properties) {
        super(properties);
        this.feyWoodStripped = feyWoodStripped;
    }

    @Override
    public FeyStrippedWoodBlock getWoodBlock() {
        return this.feyWoodStripped;
    }
}
