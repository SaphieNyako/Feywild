package com.feywild.feywild.block.trees;

import com.feywild.feywild.particles.ModParticles;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class WinterTree extends BaseTree {

    public WinterTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 15, ModParticles.winterLeafParticle));
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, Random random) {
        if (Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock())) {
            level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, 1 + random.nextInt(2)));
        }
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
                if (level.getBlockState(mpos).isAir() && Tags.Blocks.DIRT.contains(level.getBlockState(mpos.below()).getBlock())) {
                    level.setBlock(mpos, Blocks.SNOW.defaultBlockState(), 19);
                    if (level.getBlockState(mpos.below()).hasProperty(SnowyDirtBlock.SNOWY))
                        level.setBlock(mpos.below(), level.getBlockState(mpos.below()).setValue(SnowyDirtBlock.SNOWY, true), 19);
                }
                mpos.move(Direction.DOWN);
            }
        }
    }
}
