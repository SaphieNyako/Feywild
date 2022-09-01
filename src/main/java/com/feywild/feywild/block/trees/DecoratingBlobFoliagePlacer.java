package com.feywild.feywild.block.trees;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class DecoratingBlobFoliagePlacer extends BlobFoliagePlacer {

    public static <T extends DecoratingBlobFoliagePlacer> FoliagePlacerType<T> makeType(Function3<IntProvider, IntProvider, Integer, T> factory) {
        return new FoliagePlacerType<>(RecordCodecBuilder.create(instance -> blobParts(instance).apply(instance, factory)));
    }

    public DecoratingBlobFoliagePlacer(IntProvider radiusSpread, IntProvider heightSpread, int height) {
        super(radiusSpread, heightSpread, height);
    }

    @Nonnull
    @Override
    protected abstract FoliagePlacerType<?> type();

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
