package com.feywild.feywild.block.trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WinterLeavesBlock extends FeyLeavesBlock {

    @Override
    public void onPlace(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {

        if (!worldIn.isClientSide && !state.getValue(WinterLeavesBlock.PERSISTENT) && worldIn.isEmptyBlock(pos.above())) {

            worldIn.setBlockAndUpdate(pos.above(), Blocks.SNOW.defaultBlockState());
        } else if (!worldIn.isClientSide && !state.getValue(WinterLeavesBlock.PERSISTENT) && worldIn.getBlockState(pos.below()).isAir()) {

            for (int i = 0; i < 30; i++) {
                if (worldIn.getBlockState(pos.below(i)).isAir() && (worldIn.getBlockState(pos.below(i + 1)).getBlock() instanceof GrassBlock)) {
                    worldIn.setBlock(pos.below(i), Blocks.SNOW.defaultBlockState(), 2);

                    if (worldIn.getBlockState(pos.below(i + 1)).hasProperty(SnowyDirtBlock.SNOWY))
                        worldIn.setBlock(pos.below(i + 1), worldIn.getBlockState(pos.below(i + 1)).setValue(SnowyDirtBlock.SNOWY, true), 2);
                }
            }
        }
    }

}
