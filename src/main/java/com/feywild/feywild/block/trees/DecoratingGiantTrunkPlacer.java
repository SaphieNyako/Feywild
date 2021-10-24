package com.feywild.feywild.block.trees;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class DecoratingGiantTrunkPlacer extends MegaJungleTrunkPlacer {

    public DecoratingGiantTrunkPlacer(int p_i232058_1_, int p_i232058_2_, int p_i232058_3_) {
        super(p_i232058_1_, p_i232058_2_, p_i232058_3_);
    }

    private void tryPlaceLog(LevelSimulatedRW level, Random random, BlockPos.MutableBlockPos pos, Set<BlockPos> blocks, BoundingBox box, TreeConfiguration config, BlockPos target, int offsetX, int offsetY, int offsetZ, List<Pair<BlockPos, BlockState>> decoratables) {
        pos.setWithOffset(target, offsetX, offsetY, offsetZ);
        if (TreeFeature.isFree(level, pos)) {
            if (placeLog(level, random, pos, blocks, box, config)) {
                if (level instanceof WorldGenLevel) {
                    decoratables.add(Pair.of(pos.immutable(), ((WorldGenLevel) level).getBlockState(pos)));
                }
            }
        }
    }

    private List<FoliagePlacer.FoliageAttachment> placeBaseTrunk(@Nonnull LevelSimulatedRW level, @Nonnull Random random, int height, @Nonnull BlockPos pos, @Nonnull Set<BlockPos> blocks, @Nonnull BoundingBox box, @Nonnull TreeConfiguration config, List<Pair<BlockPos, BlockState>> decoratables) {
        BlockPos blockpos = pos.below();
        setDirtAt(level, blockpos);
        setDirtAt(level, blockpos.east());
        setDirtAt(level, blockpos.south());
        setDirtAt(level, blockpos.south().east());
        BlockPos.MutableBlockPos current = new BlockPos.MutableBlockPos();

        for (int i = 0; i < height; ++i) {
            this.tryPlaceLog(level, random, current, blocks, box, config, pos, 0, i, 0, decoratables);
            if (i < height - 1) {
                this.tryPlaceLog(level, random, current, blocks, box, config, pos, 1, i, 0, decoratables);
                this.tryPlaceLog(level, random, current, blocks, box, config, pos, 1, i, 1, decoratables);
                this.tryPlaceLog(level, random, current, blocks, box, config, pos, 0, i, 1, decoratables);
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(height), 0, true));
    }

    @Nonnull
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(@Nonnull LevelSimulatedRW level, @Nonnull Random random, int height, @Nonnull BlockPos pos, @Nonnull Set<BlockPos> blocks, @Nonnull BoundingBox box, @Nonnull TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        List<Pair<BlockPos, BlockState>> decoratables = new ArrayList<>();
        list.addAll(this.placeBaseTrunk(level, random, height, pos, blocks, box, config, decoratables));

        //Top to Bottom
        for (int y = (height - 2 - random.nextInt(4)); y > (height / 2); y -= (2 + random.nextInt(4))) {
            float f = random.nextFloat() * (float) (Math.PI * 2);
            int x = 0;
            int z = 0;
            for (int i = 0; i < 5; ++i) {
                x = (int) (1.5f + Mth.cos(f) * i);
                z = (int) (1.5f + Mth.sin(f) * i);
                BlockPos logPos = pos.offset(x, y - 3 + i / 2, z);
                if (placeLog(level, random, logPos, blocks, box, config)) {
                    if (level instanceof WorldGenLevel) {
                        // World interfaces are annoying
                        // This SHOULD always be a seed reader
                        decoratables.add(Pair.of(logPos.immutable(), ((WorldGenLevel) level).getBlockState(logPos)));
                    }
                }
            }
            list.add(new FoliagePlacer.FoliageAttachment(pos.offset(x, y, z), -2, false));
        }
        if (level instanceof WorldGenLevel) {
            // We need to decorate all logs after placing all logs as the decoration
            // code will query block states and expects the whole trunk to be paced
            for (Pair<BlockPos, BlockState> entry : decoratables) {
                if (((WorldGenLevel) level).getBlockState(entry.getLeft()).equals(entry.getRight())) {
                    this.decorateLog(entry.getRight(), (WorldGenLevel) level, entry.getLeft(), random);
                }
            }
        }
        return list;
    }

    protected abstract void decorateLog(BlockState state, WorldGenLevel world, BlockPos pos, Random random);

}
