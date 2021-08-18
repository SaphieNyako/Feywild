package com.feywild.feywild.block.trees;

import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class SummerTree extends BaseTree {

    public SummerTree(ModX mod) {
        super(mod, SummerLeavesBlock::new);
    }

    private static BlockState getDecorationBlock(Random random) {
        switch (random.nextInt(30)) {
            case 0:
                return Blocks.OXEYE_DAISY.defaultBlockState();
            case 1:
                return Blocks.DANDELION.defaultBlockState();
            case 2:
                return Blocks.POPPY.defaultBlockState();
            case 4:
                return Blocks.ALLIUM.defaultBlockState();
            case 5:
                return Blocks.CORNFLOWER.defaultBlockState();
            default:
                return Blocks.GRASS.defaultBlockState();
        }
    }

    @Override
    protected BaseTreeFeatureConfig.Builder getFeatureBuilder(@Nonnull Random random, boolean largeHive) {
        return super.getFeatureBuilder(random, largeHive).decorators(ImmutableList.of(
                Features.Placements.BEEHIVE_005
        ));
    }

    @Override
    public void decorateSaplingGrowth(ServerWorld world, BlockPos pos, Random random) {
        if (Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock())) {
            world.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }
}
