package com.feywild.feywild.block.trees;

import com.feywild.feywild.particles.ModParticles;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class SpringTree extends BaseTree {

    public SpringTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 14, ModParticles.springLeafParticle));
    }

    private static BlockState getDecorationBlock(Random random) {
        return switch (random.nextInt(10)) {
            case 0 -> Blocks.RED_TULIP.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.ORANGE_TULIP.defaultBlockState();
            case 3 -> Blocks.BLUE_ORCHID.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.AZURE_BLUET.defaultBlockState();
            case 6 -> Blocks.WHITE_TULIP.defaultBlockState();
            case 7 -> Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            default -> Blocks.GRASS.defaultBlockState();
        };
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.3) {
            if (Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock())) {
                level.setBlockAndUpdate(pos, getDecorationBlock(random));
            }
        }
    }
}
