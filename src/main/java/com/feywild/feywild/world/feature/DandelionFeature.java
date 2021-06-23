package com.feywild.feywild.world.feature;

import com.feywild.feywild.block.Dandelion;
import com.feywild.feywild.block.DandelionStem;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.SunflowerStem;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class DandelionFeature extends Feature<NoFeatureConfig> {

    public DandelionFeature() {

        super(NoFeatureConfig.CODEC);

    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int check = 0;

        for (int i = 0; i < 32; ++i) {
            BlockPos blockpos = pos.offset(rand.nextInt(6) - rand.nextInt(6), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(6) - rand.nextInt(6));

            if (world.getBlockState(blockpos).isAir(world, blockpos)
                    && (world.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK )) {
                if(rand.nextInt(4) == 0)
                    spawnFlower(world,blockpos, rand);
            }

            ++check;
        }

        return check > 0;
    }

    public void spawnFlower(ISeedReader world , BlockPos pos,Random random) {
        world.setBlock(pos, ModBlocks.DANDELION_STEM.get().defaultBlockState(), 2, 1);
        world.setBlock(pos.above(1), ModBlocks.DANDELION_STEM.get().defaultBlockState().setValue(DandelionStem.HAS_MODEL, true), 2, 1);
        world.setBlock(pos.above(2), ModBlocks.DANDELION_STEM.get().defaultBlockState(), 2, 1);
        world.setBlock(pos.above(3), ModBlocks.DANDELION.get().defaultBlockState().setValue(Dandelion.VARIANT,random.nextInt(3)), 2, 1);
    }

}
