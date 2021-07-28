package com.feywild.feywild.world.feature;

import com.feywild.feywild.block.Crocus;
import com.feywild.feywild.block.CrocusStem;
import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class CrocusFeature extends Feature<NoFeatureConfig> {

    public CrocusFeature() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public boolean place(@Nonnull ISeedReader world, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        int check = 0;

        for (int i = 0; i < 32; ++i) {
            BlockPos blockpos = pos.offset(rand.nextInt(6) - rand.nextInt(6), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(6) - rand.nextInt(6));

            if (world.getBlockState(blockpos).isAir(world, blockpos)
                    && (world.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK)) {
                if (rand.nextInt(4) == 0)
                    spawnFlower(world, blockpos);
            }

            ++check;
        }

        return check > 0;
    }

    public void spawnFlower(ISeedReader world, BlockPos pos) {
        world.setBlock(pos, ModBlocks.CROCUS_STEM.get().defaultBlockState().setValue(CrocusStem.HAS_MODEL, true), 2, 1);
        world.setBlock(pos.above(1), ModBlocks.CROCUS_STEM.get().defaultBlockState(), 2, 1);
        world.setBlock(pos.above(2), ModBlocks.CROCUS.get().defaultBlockState().setValue(Crocus.VARIANT, 2), 2, 1);
    }

}
