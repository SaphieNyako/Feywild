package com.feywild.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public abstract class DecoratingGiantTrunkPlacer extends MegaJungleTrunkPlacer {

    public DecoratingGiantTrunkPlacer(int p_i232058_1_, int p_i232058_2_, int p_i232058_3_) {
        super(p_i232058_1_, p_i232058_2_, p_i232058_3_);
    }

    @Nonnull
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(@Nonnull LevelSimulatedReader level, @Nonnull BiConsumer<BlockPos, BlockState> blockSetter, @Nonnull Random random, int height, @Nonnull BlockPos pos, @Nonnull TreeConfiguration cfg) {
        List<BlockPos> positionsToDecorate = new ArrayList<>();
        BiConsumer<BlockPos, BlockState> setter = (BlockPos target, BlockState state) -> {
            blockSetter.accept(target, state);
            positionsToDecorate.add(target.immutable());
        };
        List<FoliagePlacer.FoliageAttachment> result = super.placeTrunk(level, setter, random, height, pos, cfg);
        if (level instanceof WorldGenLevel wg) {
            for (BlockPos decoratePos : positionsToDecorate) {
                this.decorateLog(wg.getBlockState(decoratePos), (WorldGenLevel) level, decoratePos, random);
            }
        }
        return result;
    }

    protected abstract void decorateLog(BlockState state, WorldGenLevel world, BlockPos pos, Random random);
}
