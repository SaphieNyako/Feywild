package com.feywild.feywild.world.feature.trees;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.DecoratingBlobFoliagePlacer;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import com.google.common.collect.ImmutableList;
import org.moddingx.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WinterTreeGrower extends BaseTree {

    public WinterTreeGrower(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 15, ModParticles.winterLeafParticle));
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, Random random) {
        if (Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(level.getBlockState(pos.below()).getBlock()).get()).is(BlockTags.DIRT)) {
            level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, 1 + random.nextInt(2)));
        }
    }

    @Override
    protected String getName() {
        return "winter_tree";
    }

    @Override
    protected FoliagePlacer getFoliagePlacer() {
        return new LeavesPlacer(
                UniformInt.of(this.getLeavesRadius(), this.getLeavesRadius()),
                UniformInt.of(this.getLeavesOffset(), this.getLeavesOffset()),
                this.getLeavesHeight()
        );
    }

    @Override
    public TreeConfiguration.TreeConfigurationBuilder getFeatureBuilder(@NotNull Random random, boolean largeHive) {
        return super.getFeatureBuilder(random, largeHive).decorators(ImmutableList.of(
                new AlterGroundDecorator(SimpleStateProvider.simple(ModBlocks.snowyGrassBlock)
                )));
    }

    private static class LeavesPlacer extends DecoratingBlobFoliagePlacer {

        public LeavesPlacer(UniformInt radiusSpread, UniformInt heightSpread, int height) {
            super(radiusSpread, heightSpread, height);
        }

        @Override
        protected void decorateLeaves(BlockState state, WorldGenLevel level, BlockPos pos, Random random) {
            BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
            for (int i = 0; i < 30; i++) {
                if (level.getBlockState(mutableBlockPos).isAir() && level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK)) {
                    level.setBlock(mutableBlockPos, Blocks.SNOW.defaultBlockState(), 19);
                    if (level.getBlockState(mutableBlockPos.below()).hasProperty(GrassBlock.SNOWY))

                        level.setBlock(mutableBlockPos.below(), level.getBlockState(mutableBlockPos.below()).setValue(GrassBlock.SNOWY, true), 19);
                }
                mutableBlockPos.move(Direction.DOWN);
            }
        }
    }
}
