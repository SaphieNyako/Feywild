package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.particles.ModParticles;
import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnTree extends BaseTree {

    public AutumnTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 10, ModParticles.leafParticle));
    }

    @Override
    protected TreeConfiguration.TreeConfigurationBuilder getFeatureBuilder(@Nonnull Random random, boolean largeHive) {
        return super.getFeatureBuilder(random, largeHive).decorators(ImmutableList.of(
                new AlterGroundDecorator(SimpleStateProvider.simple(Blocks.PODZOL.defaultBlockState()))
        ));
    }

    @Override
    protected TrunkPlacer getGiantTrunkPlacer() {
        return new TrunkPlacer(this.getBaseHeight(), this.getFirstRandomHeight(), this.getSecondRandomHeight());
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.2) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    private static BlockState getDecorationBlock(Random random) {
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.PUMPKIN.defaultBlockState();
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2 -> Blocks.RED_MUSHROOM.defaultBlockState();
            case 3 -> Blocks.BROWN_MUSHROOM.defaultBlockState();
            default -> Blocks.FERN.defaultBlockState();
        };
    }

    private static class TrunkPlacer extends DecoratingGiantTrunkPlacer {

        public TrunkPlacer(int p_i232058_1_, int p_i232058_2_, int p_i232058_3_) {
            super(p_i232058_1_, p_i232058_2_, p_i232058_3_);
        }

        @Override
        protected void decorateLog(BlockState state, WorldGenLevel level, BlockPos pos, Random random) {
            if (random.nextDouble() < 0.03) {
                if (level.isEmptyBlock(pos.north())) {
                    level.setBlock(pos.north(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), 19);
                }
                if (level.isEmptyBlock(pos.east())) {
                    level.setBlock(pos.east(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), 19);
                }
                if (level.isEmptyBlock(pos.south())) {
                    level.setBlock(pos.south(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), 19);
                }
                if (level.isEmptyBlock(pos.west())) {
                    level.setBlock(pos.west(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), 19);
                }
            }
        }
    }
}
