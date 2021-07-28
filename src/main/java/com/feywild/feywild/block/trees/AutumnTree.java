package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnTree extends BaseTree {

    protected static final BlockState PODZOL_STATE = Blocks.PODZOL.defaultBlockState();

    @Override  //protected
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(@Nonnull Random randomIn, boolean largeHive) {
        BaseTreeFeatureConfig featureConfig = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(getLogBlock().defaultBlockState()),
                new SimpleBlockStateProvider(getLeafBlock().defaultBlockState()),
                getFoliagePlacer(),
                getGiantTrunkPlacer(),
                getTwoLayerFeature()
        )
                .decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(PODZOL_STATE)))).build();

        return Feature.TREE.configured(featureConfig);
    }

    @Override
    protected Block getLogBlock() {
        return ModBlocks.AUTUMN_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.AUTUMN_TREE_LEAVES.get();
    }

    @Override
    protected AbstractTrunkPlacer getGiantTrunkPlacer() {
        return new TrunkPlacer(getBaseHeight(), getFirstRandomHeight(), getSecondRandomHeight());
    }
    
    private static class TrunkPlacer extends DecoratingGiantTrunkPlacer {

        public TrunkPlacer(int p_i232058_1_, int p_i232058_2_, int p_i232058_3_) {
            super(p_i232058_1_, p_i232058_2_, p_i232058_3_);
        }

        @Override
        protected void decorateLog(BlockState state, ISeedReader world, BlockPos pos, Random random) {
            if (random.nextDouble() < 0.05) {
                if (world.isEmptyBlock(pos.north())) {
                    world.setBlock(pos.north(), ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), 19);
                } else if (world.isEmptyBlock(pos.east())) {
                    Rotation rotation = Rotation.CLOCKWISE_90;
                    world.setBlock(pos.east(), ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState().rotate(world, pos, rotation), 19);
                } else if (world.isEmptyBlock(pos.south())) {
                    Rotation rotation = Rotation.CLOCKWISE_180;
                    world.setBlock(pos.south(), ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState().rotate(world, pos, rotation), 19);
                } else if (world.isEmptyBlock(pos.west())) {
                    Rotation rotation = Rotation.COUNTERCLOCKWISE_90;
                    world.setBlock(pos.west(), ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState().rotate(world, pos, rotation), 19);
                }
            }
        }
    }
}
