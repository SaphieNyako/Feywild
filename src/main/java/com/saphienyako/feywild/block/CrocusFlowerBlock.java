package com.saphienyako.feywild.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.state.IntegerProperty;


import java.util.Random;

public class CrocusFlowerBlock extends GiantFlowerBlock{
    public static final IntegerProperty OPENING_STATE = IntegerProperty.create("variant", 0, 2);
    public CrocusFlowerBlock(int height) {
        super(height);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPENING_STATE);
    }

    @Override
    protected void tickFlower(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (level.isNight()) {
            level.setBlock(pos, state.setValue(OPENING_STATE, 0), 2);
        } else if (random.nextDouble() <= 0.4) {
            level.setBlock(pos, state.setValue(OPENING_STATE, 1 + random.nextInt(2)), 2);
        }
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    protected void animateFlower(BlockState state, World level, BlockPos pos, Random random) {
        if (level.isNight()) {
            level.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
        } else {
            level.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
        }
    }

    @Override
    public BlockState flowerState(IWorld level, BlockPos pos, Random random) {
        if (level instanceof World) {
            if (((World) level).isNight()) {
                return this.defaultBlockState().setValue(OPENING_STATE, 0);
            } else {
                return this.defaultBlockState().setValue(OPENING_STATE, 1 + random.nextInt(2));
            }
        } else {
            return this.defaultBlockState();
        }
    }
}
