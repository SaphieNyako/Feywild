package com.feywild.feywild.world.feature.trees;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.DecoratingGiantTrunkPlacer;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import org.moddingx.libx.mod.ModX;

import java.util.List;

public class AutumnTree extends BaseTree {

    public AutumnTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 10, ModParticles.leafParticle));
    }

    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.PUMPKIN.defaultBlockState();
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2 -> Blocks.RED_MUSHROOM.defaultBlockState();
            case 3 -> Blocks.BROWN_MUSHROOM.defaultBlockState();
            default -> Blocks.FERN.defaultBlockState();
        };
    }

    @Override
    public TreeConfiguration.TreeConfigurationBuilder getFeatureBuilder() {
        return super.getFeatureBuilder().decorators(List.of(
                new AlterGroundDecorator(SimpleStateProvider.simple(Blocks.PODZOL.defaultBlockState()))
        ));
    }

    @Override
    protected TrunkPlacer getGiantTrunkPlacer() {
        return new TrunkPlacer(this.getBaseHeight(), this.getFirstRandomHeight(), this.getSecondRandomHeight());
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.2) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    private static class TrunkPlacer extends DecoratingGiantTrunkPlacer {

        public TrunkPlacer(int baseHeight, int heightA, int heightB) {
            super(baseHeight, heightA, heightB);
        }

        @Override
        protected void decorateLog(BlockState state, WorldGenLevel level, BlockPos pos, RandomSource random) {
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