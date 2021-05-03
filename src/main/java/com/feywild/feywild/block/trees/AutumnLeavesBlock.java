package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class AutumnLeavesBlock extends FeyLeavesBlock {


    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

        super.animateTick(stateIn, worldIn, pos, rand);

        if (worldIn.isAirBlock(pos.down()) && rand.nextInt(2) == 1) {
            double windStrength = 5 + Math.cos((double) worldIn.getGameTime() / 2000) * 2;
            double windX = Math.cos((double) worldIn.getGameTime() / 1200) * windStrength;
            double windZ = Math.sin((double) worldIn.getGameTime() / 1000) * windStrength;

            worldIn.addParticle(new BlockParticleData(ParticleTypes.BLOCK, stateIn), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, windX, -1.0, windZ);
        }

    }



    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {

        if (worldIn.isRemote) return;

        if (!state.get(WinterLeavesBlock.PERSISTENT) && (state.get(WinterLeavesBlock.DISTANCE) <= 6 && state.get(WinterLeavesBlock.DISTANCE) != 0)
                && worldIn.getBlockState(pos.down(3)).getBlock() instanceof AutumnTreeLog) {


            BlockPos position = pos;


            while (true) {

                position = position.down();

                if (worldIn.getBlockState(position).getBlock() instanceof AutumnTreeLog) {
                    if (worldIn.getRandom().nextDouble() < 0.1) {

                        if (worldIn.isAirBlock(position.north())) {

                            worldIn.setBlockState(position.north(), ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState(), 2);

                        }

                        else

                        if (worldIn.isAirBlock(position.east())) {

                            Rotation rotation = Rotation.CLOCKWISE_90;

                            worldIn.setBlockState(position.east(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState(), rotation), 2);

                        }

                        else

                        if (worldIn.isAirBlock(position.south())) {

                            Rotation rotation = Rotation.CLOCKWISE_180;

                            worldIn.setBlockState(position.south(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState(), rotation), 2);

                        }

                        else

                        if (worldIn.isAirBlock(position.west())) {

                            Rotation rotation = Rotation.COUNTERCLOCKWISE_90;

                            worldIn.setBlockState(position.west(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState(), rotation), 2);


                        }

                    }

                } else break;

            }
        }
    }

}

