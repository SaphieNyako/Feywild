package com.feywild.feywild.world.gen.feature;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;

public class TreeMushroomFeature extends Feature<NoneFeatureConfiguration> {


    public TreeMushroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.level().getBlockState(context.origin()).getBlock() == ModTrees.autumnTree.getLogBlock()) {
            return context.level().setBlock(context.origin().north(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), 19);
        } else {
            return false;
        }
    }
}
