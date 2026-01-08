package com.saphienyako.feywild.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class DandelionFlowerBlock extends GiantFlowerBlock{
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 3);

    public DandelionFlowerBlock(int height) {
        super(height);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Override
    protected void tickFlower(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (state.getValue(VARIANT) == 3 && level.random.nextInt(3) == 0) {
            level.setBlock(pos, state.setValue(VARIANT, 2), 3);
        }
    }

    @Override
    public boolean removedByPlayer(BlockState state, World level, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (this.replaceFlower(level, pos.above(3 - state.getValue(PART)))) {
            if (!level.isClientSide && player instanceof ServerPlayerEntity) {
                BlockState newState = state.setValue(VARIANT, 3);
            //send packet?
            level.setBlock(pos, newState, 3);
            //    level.markAndNotifyBlock(pos, state, newState, 3);
            }
            return false;
        }
        return super.removedByPlayer(state, level, pos, player, willHarvest, fluid);
    }


    private boolean replaceFlower(@Nonnull World level, @Nonnull BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == this && state.getValue(PART) == 3 && state.getValue(VARIANT) == 2) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(VARIANT, 3), 3);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRemove(BlockState oldState, @Nonnull World level, @Nonnull BlockPos pos, BlockState newState, boolean moving) {
        super.onRemove(oldState, level, pos, newState, moving);
        BlockPos midPos = new BlockPos(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);

        if (oldState.getValue(VARIANT) == 2) {
            //  FeywildNetwork.sendParticles(level, ParticleMessage.Type.DANDELION_FLUFF, midPos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getDestroyProgress(BlockState state, PlayerEntity player, IBlockReader reader, BlockPos pos) {
        return state.getValue(PART) == 3 && state.getValue(VARIANT) == 2 ? 1 : super.getDestroyProgress(state, player, reader, pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void animateFlower(BlockState state, World level, BlockPos pos, Random random) {
        if (state.getValue(VARIANT) == 2 && random.nextDouble() < 0.4) {
            double windStrength = Math.cos((double) level.getGameTime() / 2000) / 8;
            double windX = Math.cos((double) level.getGameTime() / 1200) * windStrength;
            double windZ = Math.sin((double) level.getGameTime() / 1000) * windStrength;
            level.addParticle(ParticleTypes.END_ROD, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), windX, 0, windZ);
        }
    }

    @Override
    public BlockState flowerState(IWorld level, BlockPos pos, Random random) {
        return this.defaultBlockState().setValue(VARIANT, random.nextInt(3));
    }
}
