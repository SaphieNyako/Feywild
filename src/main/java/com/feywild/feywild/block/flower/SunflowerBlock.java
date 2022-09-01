package com.feywild.feywild.block.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;

public class SunflowerBlock extends GiantFlowerBlock {

    public static final IntegerProperty TIME_VARIANT = IntegerProperty.create("time_variant", 0, 2);

    public SunflowerBlock(ModX mod) {
        super(mod, 4);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TIME_VARIANT);
    }

    @Override
    protected void tickFlower(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
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
    protected void animateFlower(BlockState state, Level level, BlockPos pos, RandomSource random) {
        //
    }

    @Override
    public BlockState flowerState(LevelAccessor level, BlockPos pos, RandomSource random) {
        return this.defaultBlockState();
    }
}
