package com.feywild.feywild.block.trees;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FeyStrippedLogBlock extends RotatedPillarBlock {

    public FeyStrippedLogBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG));
    }
}
