package com.feywild.feywild.block.trees;

import net.minecraft.world.level.block.RotatedPillarBlock;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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
