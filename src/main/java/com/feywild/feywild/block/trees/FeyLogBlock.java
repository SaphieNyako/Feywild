package com.feywild.feywild.block.trees;

import net.minecraft.world.level.block.RotatedPillarBlock;

public class FeyLogBlock extends RotatedPillarBlock {

    private final FeyWoodBlock feyWood;
    
    public FeyLogBlock(FeyWoodBlock feyWood, Properties properties) {
        super(properties);
        this.feyWood = feyWood;
    }

    public FeyWoodBlock getWoodBlock() {
        return feyWood;
    }
}
