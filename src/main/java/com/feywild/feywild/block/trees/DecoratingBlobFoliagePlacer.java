package com.feywild.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class DecoratingBlobFoliagePlacer extends BlobFoliagePlacer {

    public DecoratingBlobFoliagePlacer(UniformInt radiusSpread, UniformInt heightSpread, int height) {
        super(radiusSpread, heightSpread, height);
    }

    @Override
    protected void placeLeavesRow(@Nonnull LevelSimulatedReader level, @Nonnull BiConsumer<BlockPos, BlockState> blockSetter, @Nonnull RandomSource random, @Nonnull TreeConfiguration config, @Nonnull BlockPos pos, int range, int height, boolean large) {
        List<BlockPos> positionsToDecorate = new ArrayList<>();
        BiConsumer<BlockPos, BlockState> setter = (BlockPos target, BlockState state) -> {
            blockSetter.accept(target, state);
            positionsToDecorate.add(target.immutable());
        };
        super.placeLeavesRow(level, setter, random, config, pos, range, height, large);
        if (level instanceof WorldGenLevel wg) {
            for (BlockPos decoratePos : positionsToDecorate) {
                this.decorateLeaves(wg.getBlockState(decoratePos), (WorldGenLevel) level, decoratePos, random);
            }
        }
    }

    protected abstract void decorateLeaves(BlockState state, WorldGenLevel world, BlockPos pos, RandomSource random);
}
