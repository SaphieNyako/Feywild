package com.feywild.feywild.world.feature.trees;

import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import org.moddingx.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class SpringTreeGrower extends BaseTree {

    public SpringTreeGrower(ModX mod) {
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
    protected String getName() {
        return "spring_tree";
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.3) {
            if (Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(level.getBlockState(pos.below()).getBlock()).get()).is(BlockTags.DIRT)) {
                level.setBlockAndUpdate(pos, getDecorationBlock(random));
            }
        }
    }
}
