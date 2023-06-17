package com.feywild.feywild.block.trees.seasonal;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.world.gen.feature.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class HexenTree extends BaseTree {
    public HexenTree(ModX mod) {
        super(mod);
    }

    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.PUMPKIN.defaultBlockState();
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2 -> ModBlocks.feyMushroom.defaultBlockState();
            default -> Blocks.FERN.defaultBlockState();
        };
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.2) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    @Override
    public FeyLeavesBlock getLeafBlock() {
        Random random = new Random();
        return switch (random.nextInt(2)) {
            case 0 -> ModBlocks.hexBlackLeaves;
            default -> ModBlocks.hexPurpleLeaves;
        };
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.winterSparkleParticle;
    }

    @Nullable
    @Override
    public Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource random, boolean largeHive) {
        return ModConfiguredFeatures.HEXEN_TREE;
    }
}
