package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class SpringTree extends BaseTree {

    @Override
    protected Block getLogBlock() {
        return ModBlocks.SPRING_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.SPRING_TREE_LEAVES.get();
    }


}
