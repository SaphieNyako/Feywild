package com.feywild.feywild.block.trees;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class WinterTree extends BaseTree {

    public WinterTree(ModX mod) {
        super(mod, WinterLeavesBlock::new);
    }

    @Override
    public void decorateSaplingGrowth(ServerWorld world, BlockPos pos, Random random) {
        if (Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock())) {
            world.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, 1 + random.nextInt(2)));
        }
    }

    @Override
    protected FoliagePlacer getFoliagePlacer() {
        return new LeavesPlacer(
                FeatureSpread.fixed(this.getLeavesRadius()),
                FeatureSpread.fixed(this.getLeavesOffset()),
                this.getLeavesHeight()
        );
    }

    private static class LeavesPlacer extends DecoratingBlobFoliagePlacer {
        
        public LeavesPlacer(FeatureSpread radiusSpread, FeatureSpread heightSpread, int height) {
            super(radiusSpread, heightSpread, height);
        }

        @Override
        protected void decorateLeaves(BlockState state, ISeedReader world, BlockPos pos, Random random) {
            BlockPos.Mutable mpos = pos.mutable();
            for (int i = 0; i < 30; i++) {
                //noinspection deprecation
                if (world.getBlockState(mpos).isAir() && Tags.Blocks.DIRT.contains(world.getBlockState(mpos.below()).getBlock())) {
                    world.setBlock(mpos, Blocks.SNOW.defaultBlockState(), 19);
                    if (world.getBlockState(mpos.below()).hasProperty(SnowyDirtBlock.SNOWY))
                        world.setBlock(mpos.below(), world.getBlockState(mpos.below()).setValue(SnowyDirtBlock.SNOWY, true), 19);
                }
                mpos.move(Direction.DOWN);
            }
        }
    }
}
