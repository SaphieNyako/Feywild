package com.feywild.feywild.block.trees;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;
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

    @Nonnull
    @Override
    public List<FoliagePlacer.Foliage> placeTrunk(@Nonnull IWorldGenerationReader world, @Nonnull Random random, int height, @Nonnull BlockPos pos, @Nonnull Set<BlockPos> blocks, @Nonnull MutableBoundingBox box, @Nonnull BaseTreeFeatureConfig config) {
        List<FoliagePlacer.Foliage> list = Lists.newArrayList();
        list.addAll(super.placeTrunk(world, random, height, pos, blocks, box, config));
        List<Pair<BlockPos, BlockState>> decoratables = new ArrayList<>();
        for(int y = (height - 2 - random.nextInt(4)); y > (height / 2); y -= (2 + random.nextInt(4))) {
            float f = random.nextFloat() * (float) (Math.PI * 2);
            int x = 0;
            int z = 0;
            for (int i = 0; i < 5; ++i) {
                x = (int) (1.5f + MathHelper.cos(f) * i);
                z = (int) (1.5f + MathHelper.sin(f) * i);
                BlockPos logPos = pos.offset(x, y - 3 + i / 2, z);
                if (placeLog(world, random, logPos, blocks, box, config)) {
                    if (world instanceof ISeedReader) {
                        // World interfaces are annoying
                        // This SHOULD always be a seed reader
                        decoratables.add(Pair.of(logPos, ((ISeedReader) world).getBlockState(logPos)));
                    }
                }
            }
            list.add(new FoliagePlacer.Foliage(pos.offset(x, y, z), -2, false));
        }
        if (world instanceof ISeedReader) {
            // We need to decorate all logs after placing all logs as the decoration
            // code will query block states and expects the whole trunk to be paced
            for (Pair<BlockPos, BlockState> entry : decoratables) {
                if (((ISeedReader) world).getBlockState(entry.getLeft()).equals(entry.getRight())) {
                    decorateLog(entry.getRight(), (ISeedReader) world, entry.getLeft(), random);
                }
            }
        }
        return list;
    }

    protected abstract void decorateLog(BlockState state, ISeedReader world, BlockPos pos, Random random);
}
