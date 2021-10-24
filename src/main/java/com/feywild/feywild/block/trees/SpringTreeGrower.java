package com.feywild.feywild.block.trees;

import com.feywild.feywild.particles.ModParticles;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class SpringTreeGrower extends BaseTreeGrower {

    public SpringTreeGrower(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 14, ModParticles.springLeafParticle));
    }

    private static BlockState getDecorationBlock(Random random) {
        switch (random.nextInt(10)) {
            case 0:
                return Blocks.RED_TULIP.defaultBlockState();
            case 1:
                return Blocks.DANDELION.defaultBlockState();
            case 2:
                return Blocks.ORANGE_TULIP.defaultBlockState();
            case 3:
                return Blocks.BLUE_ORCHID.defaultBlockState();
            case 4:
                return Blocks.ALLIUM.defaultBlockState();
            case 5:
                return Blocks.AZURE_BLUET.defaultBlockState();
            case 6:
                return Blocks.WHITE_TULIP.defaultBlockState();
            case 7:
                return Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            default:
                return Blocks.GRASS.defaultBlockState();
        }
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
