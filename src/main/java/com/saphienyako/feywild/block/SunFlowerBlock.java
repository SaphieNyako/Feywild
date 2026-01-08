package com.saphienyako.feywild.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class SunFlowerBlock extends GiantFlowerBlock{
    public static final IntegerProperty TIME_VARIANT = IntegerProperty.create("time_variant", 0, 2);

    public SunFlowerBlock(int height) {
        super(height);
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TIME_VARIANT);
    }

    @Override
    protected void tickFlower(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (level.getDayTime() < 2800) {
            level.setBlock(pos, state.setValue(TIME_VARIANT, 0), 3);
        } else if (level.getDayTime() < 8400) {
            level.setBlock(pos, state.setValue(TIME_VARIANT, 1), 3);
        } else {
            level.setBlock(pos, state.setValue(TIME_VARIANT, 2), 3);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void animateFlower(BlockState state, World level, BlockPos pos, Random random) {
        //
    }

    @Override
    public BlockState flowerState(IWorld level, BlockPos pos, Random random) {
        return this.defaultBlockState();
    }
}
