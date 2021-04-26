package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

import java.util.Random;

public class AutumnTree extends BaseTree {

    protected static final BlockState PODZOL_STATE = Blocks.PODZOL.getDefaultState();
    //protected static final BlockState GRASS_BLOCK_STATE = Blocks.GRASS_BLOCK.getDefaultState();

    @Override  //protected
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        BaseTreeFeatureConfig featureConfig = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(getLogBlock().getDefaultState()),
                new SimpleBlockStateProvider(getLeafBlock().getDefaultState()),
                getFoliagePlacer(),
                getGiantTrunkPlacer(),
                getTwoLayerFeature()
        ).setDecorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(PODZOL_STATE)))).build();

        return Feature.TREE.withConfiguration(featureConfig);
    }


    @Override
    protected Block getLogBlock() {
        return ModBlocks.AUTUMN_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.AUTUMN_TREE_LEAVES.get();
    }

}
