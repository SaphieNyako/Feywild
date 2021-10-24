package com.feywild.feywild.block.trees;

import com.feywild.feywild.particles.ModParticles;
import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.data.worldgen.Features;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class SummerTree extends BaseTree {

    public SummerTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 20, ModParticles.summerLeafParticle));
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
    protected TreeConfiguration.TreeConfigurationBuilder getFeatureBuilder(@Nonnull Random random, boolean largeHive) {
        return super.getFeatureBuilder(random, largeHive).decorators(ImmutableList.of(
                Features.Decorators.BEEHIVE_005
        ));
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, Random random) {
        if (Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock())) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }
}
