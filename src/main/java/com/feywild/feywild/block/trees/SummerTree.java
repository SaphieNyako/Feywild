package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;

import java.util.Random;

public class SummerTree extends BaseTree {

    @Override
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
        BaseTreeFeatureConfig featureConfig = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(getLogBlock().defaultBlockState()),
                new SimpleBlockStateProvider(getLeafBlock().defaultBlockState()),
                getFoliagePlacer(),
                getGiantTrunkPlacer(),
                getTwoLayerFeature()
        ).decorators(ImmutableList.of(Features.Placements.BEEHIVE_005))
                .build();
        
        return Feature.TREE.configured(featureConfig);
    }


    @Override
    protected Block getLogBlock() {
        return ModBlocks.SUMMER_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.SUMMER_TREE_LEAVES.get();
    }

}
