package com.feywild.feywild.block.trees;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class SpringTreeSapling extends BaseSapling {

    public SpringTreeSapling() {
        super(SpringTree::new, AbstractBlock.Properties.from(Blocks.OAK_SAPLING));
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {

        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        //50% chance of Bonemeal working.
        return (double)worldIn.rand.nextFloat() < 0.50;
    }


}
