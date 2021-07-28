package com.feywild.feywild.block.trees;

import net.minecraft.block.Block;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public abstract class BaseTree extends Tree {

    // Default values

    private static final int BASE_HEIGHT = 6;
    private static final int FIRST_RANDOM_HEIGHT = 7;
    private static final int SECOND_RANDOM_HEIGHT = 8;

    private static final int LEAVES_RADIUS = 5;
    private static final int LEAVES_OFFSET = 4;
    private static final int LEAVES_HEIGHT = 5;

    @Nullable
    @Override  //protected
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(@Nonnull Random randomIn, boolean largeHive) {
        BaseTreeFeatureConfig featureConfig = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(getLogBlock().defaultBlockState()),
                new SimpleBlockStateProvider(getLeafBlock().defaultBlockState()),
                getFoliagePlacer(),
                getGiantTrunkPlacer(),
                getTwoLayerFeature()
        ).build();

        return Feature.TREE.configured(featureConfig);
    }

    protected FoliagePlacer getFoliagePlacer() {
        return new BlobFoliagePlacer(
                FeatureSpread.fixed(getLeavesRadius()),
                FeatureSpread.fixed(getLeavesOffset()), getLeavesHeight());
    }

    protected AbstractTrunkPlacer getGiantTrunkPlacer() {
        return new MegaJungleTrunkPlacer(getBaseHeight(), getFirstRandomHeight(), getSecondRandomHeight());
    }

    //Branch placer
    protected TwoLayerFeature getTwoLayerFeature() {
        return new TwoLayerFeature(1, 0, 1);
    }

    protected abstract Block getLogBlock();

    protected abstract Block getLeafBlock();

    protected int getLeavesRadius() {
        return LEAVES_RADIUS;
    }

    protected int getLeavesOffset() {
        return LEAVES_OFFSET;
    }

    protected int getLeavesHeight() {
        return LEAVES_HEIGHT;
    }

    protected int getBaseHeight() {
        return BASE_HEIGHT;
    }

    protected int getFirstRandomHeight() {
        return FIRST_RANDOM_HEIGHT;
    }

    protected int getSecondRandomHeight() {
        return SECOND_RANDOM_HEIGHT;
    }
}
