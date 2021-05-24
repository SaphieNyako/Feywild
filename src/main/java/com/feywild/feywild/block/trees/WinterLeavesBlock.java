package com.feywild.feywild.block.trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WinterLeavesBlock extends FeyLeavesBlock {

    @Override
    public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {

        if (!worldIn.isClientSide && !state.getValue(WinterLeavesBlock.PERSISTENT) && worldIn.isEmptyBlock(pos.above())) {

            worldIn.setBlockAndUpdate(pos.above(), Blocks.SNOW.defaultBlockState());

        }
    }

}
