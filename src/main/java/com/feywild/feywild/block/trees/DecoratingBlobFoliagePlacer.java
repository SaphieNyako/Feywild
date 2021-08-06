package com.feywild.feywild.block.trees;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.Set;

public abstract class DecoratingBlobFoliagePlacer extends BlobFoliagePlacer {

    public DecoratingBlobFoliagePlacer(FeatureSpread radiusSpread, FeatureSpread heightSpread, int height) {
        super(radiusSpread, heightSpread, height);
    }

    @Override
    protected void placeLeavesRow(@Nonnull IWorldGenerationReader world, @Nonnull Random random, @Nonnull BaseTreeFeatureConfig config, @Nonnull BlockPos pos, int radius, @Nonnull Set<BlockPos> positions, int height, boolean flag, @Nonnull MutableBoundingBox box) {
        int off = flag ? 1 : 0;
        BlockPos.Mutable current = new BlockPos.Mutable();
        for(int xd = -radius; xd <= radius + off; ++xd) {
            for(int zd = -radius; zd <= radius + off; ++zd) {
                if (!this.shouldSkipLocationSigned(random, xd, height, zd, radius, flag)) {
                    current.setWithOffset(pos, xd, height, zd);
                    if (TreeFeature.validTreePos(world, current)) {
                        world.setBlock(current, config.leavesProvider.getState(random, current), 19);
                        if (world instanceof ISeedReader) {
                            this.decorateLeaves(((ISeedReader) world).getBlockState(current), (ISeedReader) world, current, random);
                        }
                        box.expand(new MutableBoundingBox(current, current));
                        positions.add(current.immutable());
                    }
                }
            }
        }

    }
    
    protected abstract void decorateLeaves(BlockState state, ISeedReader world, BlockPos pos, Random random);
}
