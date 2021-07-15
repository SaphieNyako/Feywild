package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnLeavesBlock extends FeyLeavesBlock {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

        super.animateTick(stateIn, worldIn, pos, rand);

        if (worldIn.isEmptyBlock(pos.below()) && rand.nextInt(2) == 1) {
            double windStrength = 5 + Math.cos((double) worldIn.getGameTime() / 2000) * 2;
            double windX = Math.cos((double) worldIn.getGameTime() / 1200) * windStrength;
            double windZ = Math.sin((double) worldIn.getGameTime() / 1000) * windStrength;

            worldIn.addParticle(new BlockParticleData(ParticleTypes.BLOCK, stateIn), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, windX, -1.0, windZ);
        }

    }

    // TODO i feel placing mushrooms here is the wrong way of doing it.
    // Could cause incompatibilities with other mods I think
    @Override
    public void onPlace(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {

        if (worldIn.isClientSide) return;

        if (!state.getValue(WinterLeavesBlock.PERSISTENT) && (state.getValue(WinterLeavesBlock.DISTANCE) <= 6 && state.getValue(WinterLeavesBlock.DISTANCE) != 0)
                && worldIn.getBlockState(pos.below(3)).getBlock() instanceof AutumnTreeLog) {

            BlockPos position = pos;

            while (true) {

                position = position.below();

                if (worldIn.getBlockState(position).getBlock() instanceof AutumnTreeLog) {
                    if (worldIn.getRandom().nextDouble() < 0.1) {

                        if (worldIn.isEmptyBlock(position.north())) {

                            worldIn.setBlock(position.north(), ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), 2);

                        } else if (worldIn.isEmptyBlock(position.east())) {

                            Rotation rotation = Rotation.CLOCKWISE_90;

                            worldIn.setBlock(position.east(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), rotation), 2);

                        } else if (worldIn.isEmptyBlock(position.south())) {

                            Rotation rotation = Rotation.CLOCKWISE_180;

                            worldIn.setBlock(position.south(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), rotation), 2);

                        } else if (worldIn.isEmptyBlock(position.west())) {

                            Rotation rotation = Rotation.COUNTERCLOCKWISE_90;

                            worldIn.setBlock(position.west(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), rotation), 2);

                        }

                    }

                } else break;

            }
        }
    }

}

