package com.feywild.feywild.block.trees;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class SpringTree extends BaseTree {

    public SpringTree(ModX mod) {
        super(mod);
    }

    @Override
    public void decorateSaplingGrowth(ServerWorld world, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.3) {
            if (Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock())) {
                world.setBlockAndUpdate(pos, getDecorationBlock(random));
            }
        }
    }

    private static BlockState getDecorationBlock(Random random) {
        switch (random.nextInt(30)) {
            case 0: return Blocks.RED_TULIP.defaultBlockState();
            case 1: return Blocks.DANDELION.defaultBlockState();
            case 2: return Blocks.ORANGE_TULIP.defaultBlockState();
            case 3: return Blocks.BLUE_ORCHID.defaultBlockState();
            case 4: return Blocks.ALLIUM.defaultBlockState();
            case 5: return Blocks.AZURE_BLUET.defaultBlockState();
            case 6: return Blocks.WHITE_TULIP.defaultBlockState();
            case 7: return Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            default: return Blocks.GRASS.defaultBlockState();
        }
    }
}
