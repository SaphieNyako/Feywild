package com.feywild.feywild.block.trees.feature;

import com.feywild.feywild.block.trees.DecoratingBlobFoliagePlacer;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

import java.util.Random;

public class WinterTreeGrower extends BaseTreeGrower {

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

    private static class LeavesPlacer extends DecoratingBlobFoliagePlacer {

        public LeavesPlacer(UniformInt radiusSpread, UniformInt heightSpread, int height) {
            super(radiusSpread, heightSpread, height);
        }

        @Override
        protected void decorateLeaves(BlockState state, WorldGenLevel level, BlockPos pos, Random random) {
            BlockPos.MutableBlockPos mpos = pos.mutable();
            for (int i = 0; i < 30; i++) {
                if (level.getBlockState(mpos).isAir() && Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(level.getBlockState(pos.below()).getBlock()).get()).is(BlockTags.DIRT)) {
                    level.setBlock(mpos, Blocks.SNOW.defaultBlockState(), 19);
                    if (level.getBlockState(mpos.below()).hasProperty(SnowyDirtBlock.SNOWY))
                        level.setBlock(mpos.below(), level.getBlockState(mpos.below()).setValue(SnowyDirtBlock.SNOWY, true), 19);
                }
                mpos.move(Direction.DOWN);
            }
        }
    }
}
