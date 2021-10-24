package com.feywild.feywild.block.trees;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.Set;

public abstract class DecoratingBlobFoliagePlacer extends BlobFoliagePlacer {

    public DecoratingBlobFoliagePlacer(UniformInt radiusSpread, UniformInt heightSpread, int height) {
        super(radiusSpread, heightSpread, height);
    }

    @Override
    protected void placeLeavesRow(@Nonnull LevelSimulatedRW level, @Nonnull Random random, @Nonnull TreeConfiguration config, @Nonnull BlockPos pos, int radius, @Nonnull Set<BlockPos> positions, int height, boolean flag, @Nonnull BoundingBox box) {
        int off = flag ? 1 : 0;
        BlockPos.MutableBlockPos current = new BlockPos.MutableBlockPos();
        for(int xd = -radius; xd <= radius + off; ++xd) {
            for(int zd = -radius; zd <= radius + off; ++zd) {
                if (!this.shouldSkipLocationSigned(random, xd, height, zd, radius, flag)) {
                    current.setWithOffset(pos, xd, height, zd);
                    if (TreeFeature.validTreePos(level, current)) {
                        level.setBlock(current, config.leavesProvider.getState(random, current), 19);
                        if (level instanceof WorldGenLevel) {
                            this.decorateLeaves(((WorldGenLevel) level).getBlockState(current), (WorldGenLevel) level, current, random);
                        }
                        box.expand(new BoundingBox(current, current));
                        positions.add(current.immutable());
                    }
                }
            }
        }

    }
    
    protected abstract void decorateLeaves(BlockState state, WorldGenLevel world, BlockPos pos, Random random);
}
