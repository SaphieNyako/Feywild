package com.feywild.feywild.block.trees;

import com.feywild.feywild.particles.ModParticles;
import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class SummerTree extends BaseTree {

    public SummerTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 20, ModParticles.summerLeafParticle));
    }

    private static BlockState getDecorationBlock(Random random) {
        return switch (random.nextInt(30)) {
            case 0 -> Blocks.OXEYE_DAISY.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.POPPY.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.CORNFLOWER.defaultBlockState();
            default -> Blocks.GRASS.defaultBlockState();
        };
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
