package com.feywild.feywild.block.flower;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class Crocus extends GiantFlowerBlock {

    public static final IntegerProperty OPENING_STATE = IntegerProperty.create("opening_state", 0, 2);
    
    public Crocus(ModX mod) {
        super(mod, 3);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPENING_STATE);
    }
    
    @Override
    protected void tickFlower(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isNight()) {
            world.setBlock(pos, state.setValue(OPENING_STATE, 0), 2);
        } else if (random.nextDouble() <= 0.4) {
            world.setBlock(pos, state.setValue(OPENING_STATE, 1 + random.nextInt(2)), 2);
        }
    }

    @Override
    protected void animateFlower(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isNight()) {
            world.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
        } else {
            world.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
        }
    }

    @Override
    public BlockState flowerState(IWorld world, BlockPos pos, Random random) {
        if (world instanceof World) {
            if (((World) world).isNight()) {
                return this.defaultBlockState().setValue(OPENING_STATE, 0);
            } else {
                return this.defaultBlockState().setValue(OPENING_STATE, 1 + random.nextInt(2));
            }
        } else {
            return this.defaultBlockState();
        }
    }
}
