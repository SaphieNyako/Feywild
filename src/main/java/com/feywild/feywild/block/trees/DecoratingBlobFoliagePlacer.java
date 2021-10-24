package com.feywild.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.BiConsumer;

public abstract class DecoratingBlobFoliagePlacer extends BlobFoliagePlacer {

    public DecoratingBlobFoliagePlacer(UniformInt radiusSpread, UniformInt heightSpread, int height) {
        super(radiusSpread, heightSpread, height);
    }

    @Override
    protected void placeLeavesRow(@Nonnull LevelSimulatedReader level, @Nonnull BiConsumer<BlockPos, BlockState> blockSetter, @Nonnull Random random, @Nonnull TreeConfiguration config, @Nonnull BlockPos pos, int range, int height, boolean large) {
        BiConsumer<BlockPos, BlockState> setter = (BlockPos target, BlockState state) -> {
            blockSetter.accept(target, state);
            if (level instanceof WorldGenLevel) {
                this.decorateLeaves(((WorldGenLevel) level).getBlockState(target), (WorldGenLevel) level, target, random);
            }
        };
        super.placeLeavesRow(level, setter, random, config, pos, range, height, large);
    }
    
    protected abstract void decorateLeaves(BlockState state, WorldGenLevel world, BlockPos pos, Random random);
}
