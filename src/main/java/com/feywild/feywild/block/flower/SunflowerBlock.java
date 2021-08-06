package com.feywild.feywild.block.flower;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class SunflowerBlock extends GiantFlowerBlock {

    public static final IntegerProperty TIME_VARIANT = IntegerProperty.create("time_variant", 0, 2);

    public SunflowerBlock(ModX mod) {
        super(mod, 4);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TIME_VARIANT);
    }

    @Override
    protected void tickFlower(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getDayTime() < 2800) {
            world.setBlock(pos, state.setValue(TIME_VARIANT, 0), 3);
        } else if (world.getDayTime() < 8400) {
            world.setBlock(pos, state.setValue(TIME_VARIANT, 1), 3);
        } else {
            world.setBlock(pos, state.setValue(TIME_VARIANT, 2), 3);
        }
    }

    @Override
    protected void animateFlower(BlockState state, World world, BlockPos pos, Random random) {
        //
    }

    @Override
    public BlockState flowerState(IWorld world, BlockPos pos, Random random) {
        return this.defaultBlockState();
    }
}
