package com.feywild.feywild.block.trees;

import net.minecraft.block.RotatedPillarBlock;

public class FeyWoodBlock extends RotatedPillarBlock {

    private final RotatedPillarBlock logBlock;
    
    public FeyWoodBlock(RotatedPillarBlock logBlock, Properties properties) {
        super(properties);
        this.logBlock = logBlock;
    }

    public RotatedPillarBlock getLogBlock() {
        return this.logBlock;
    }
}
