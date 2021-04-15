package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.VineBlock;
import net.minecraft.block.trees.BigTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.*;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;

import javax.annotation.Nullable;
import java.util.Random;

public class SpringTree extends Tree {

    private static final int BASE_HEIGHT = 6;
    private static final int FIRST_RANDOM_HEIGHT = 7;
    private static final int SECOND_RANDOM_HEIGHT = 8;

    private static final int LEAVES_RADIUS = 5;
    private static final int LEAVES_OFFSET =5;
    private static final int LEAVES_HEIGHT =5;

   public static final BaseTreeFeatureConfig SPRING_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
           new SimpleBlockStateProvider(ModBlocks.FEY_TREE_LOG.get().getDefaultState()),
           new SimpleBlockStateProvider(ModBlocks.SPRING_TREE_LEAVES.get().getDefaultState()),
           new BlobFoliagePlacer(FeatureSpread.func_242252_a(LEAVES_RADIUS),
                   FeatureSpread.func_242252_a(LEAVES_OFFSET), LEAVES_HEIGHT),
           new MegaJungleTrunkPlacer(BASE_HEIGHT, FIRST_RANDOM_HEIGHT, SECOND_RANDOM_HEIGHT),
           new TwoLayerFeature(1,0,1)
   )).build();


    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return Feature.TREE.withConfiguration(SPRING_TREE_CONFIG);
    }
}
