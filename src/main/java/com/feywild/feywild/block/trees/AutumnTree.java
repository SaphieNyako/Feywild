package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.TreeMushroomBlock;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.lwjgl.system.CallbackI;

import java.util.Random;

public class AutumnTree extends BaseTree {

    protected static final BlockState PODZOL_STATE = Blocks.PODZOL.defaultBlockState();


    @Override  //protected
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
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
}
