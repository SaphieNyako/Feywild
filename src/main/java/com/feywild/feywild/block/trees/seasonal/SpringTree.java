package com.feywild.feywild.block.trees.seasonal;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.util.data.TagAccess;

import java.util.Random;

public class SpringTree extends BaseTree {

    public SpringTree(ModX mod) {
        super(mod);
    }

    private static BlockState getDecorationBlock(RandomSource random) {
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
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.3) {
            if (TagAccess.ROOT.has(BlockTags.DIRT, level.getBlockState(pos.below()).getBlock())) {
                level.setBlockAndUpdate(pos, getDecorationBlock(random));
            }
        }
    }

    @Override
    public FeyLeavesBlock getLeafBlock() {
        Random random = new Random();
        return switch (random.nextInt(2)) {
            case 0 -> ModBlocks.springCyanLeaves;
            case 1 -> ModBlocks.springGreenLeaves;
            default -> ModBlocks.springLimeLeaves;
        };
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.springLeafParticle;
    }
}
