package com.feywild.feywild.block.trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WinterLeavesBlock extends FeyLeavesBlock{


    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {

         if(!worldIn.isRemote && !state.get(WinterLeavesBlock.PERSISTENT) && worldIn.isAirBlock(pos.up())){

                worldIn.setBlockState(pos.up(), Blocks.SNOW.getDefaultState());

        }
    }




}
