package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.particles.ModParticles;
import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnTree extends BaseTree {

    public AutumnTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 10, ModParticles.leafParticle));
    }

    @Override
    protected BaseTreeFeatureConfig.Builder getFeatureBuilder(@Nonnull Random random, boolean largeHive) {
        return super.getFeatureBuilder(random, largeHive).decorators(ImmutableList.of(
                new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.defaultBlockState()))
        ));
    }

    @Override
    protected AbstractTrunkPlacer getGiantTrunkPlacer() {
        return new TrunkPlacer(this.getBaseHeight(), this.getFirstRandomHeight(), this.getSecondRandomHeight());
    }

    @Override
    public void decorateSaplingGrowth(ServerWorld world, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.2) {
            world.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    private static BlockState getDecorationBlock(Random random) {
        switch (random.nextInt(20)) {
            case 0: return Blocks.PUMPKIN.defaultBlockState();
            case 1: return Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2: return Blocks.RED_MUSHROOM.defaultBlockState();
            case 3: return Blocks.BROWN_MUSHROOM.defaultBlockState();
            default: return Blocks.FERN.defaultBlockState();
        }
    }

    private static class TrunkPlacer extends DecoratingGiantTrunkPlacer {

        public TrunkPlacer(int p_i232058_1_, int p_i232058_2_, int p_i232058_3_) {
            super(p_i232058_1_, p_i232058_2_, p_i232058_3_);
        }

        @Override
        protected void decorateLog(BlockState state, ISeedReader world, BlockPos pos, Random random) {
            if (random.nextDouble() < 0.02) {
                if (world.isEmptyBlock(pos.north())) {
                    world.setBlock(pos.north(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH), 19);
                }
                if (world.isEmptyBlock(pos.east())) {
                    world.setBlock(pos.east(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST), 19);
                }
                if (world.isEmptyBlock(pos.south())) {
                    world.setBlock(pos.south(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH), 19);
                }
                if (world.isEmptyBlock(pos.west())) {
                    world.setBlock(pos.west(), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST), 19);
                }
            }
        }
    }
}
