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

public class SummerTreeGrower extends BaseTree {

    public SummerTreeGrower(ModX mod) {
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
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, Random random) {
        if (Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(level.getBlockState(pos.below()).getBlock()).get()).is(BlockTags.DIRT)) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    @Override
    protected String getName() {
        return "summer_tree";
    }
}
