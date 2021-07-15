package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class WinterTree extends BaseTree {


    protected static final BlockState SNOW = Blocks.GOLD_BLOCK.defaultBlockState();
    @Override  //protected
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
        BaseTreeFeatureConfig featureConfig = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(getLogBlock().defaultBlockState()),
                new SimpleBlockStateProvider(getLeafBlock().defaultBlockState()),
                getFoliagePlacer(),
                getGiantTrunkPlacer(),
                getTwoLayerFeature()
        ).build();

        return Feature.TREE.configured(featureConfig);
    }

    @Override
    protected Block getLogBlock() {
        return ModBlocks.WINTER_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.WINTER_TREE_LEAVES.get();
    }

}
