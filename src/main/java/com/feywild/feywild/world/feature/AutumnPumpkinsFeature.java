package com.feywild.feywild.world.feature;

import com.feywild.feywild.block.ModBlocks;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class AutumnPumpkinsFeature extends Feature<NoFeatureConfig> {


    public AutumnPumpkinsFeature() {


        super(NoFeatureConfig.field_236558_a_);

    }


    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int check = 0;

        for(int i = 0; i < 32; ++i)
        {
          BlockPos blockpos = pos.add(rand.nextInt(8)- rand.nextInt(8), rand.nextInt(4)-rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (world.getBlockState(blockpos).isAir(world, blockpos)
                    && (world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK || world.getBlockState(blockpos.down()).getBlock() == Blocks.PODZOL)) {
                if (rand.nextInt(3) == 0) {

                    world.setBlockState(blockpos, getBlocks(rand), 2);

                    }
                }

                ++check;
            }

        return check > 0;
    }

    public BlockState getBlocks(Random random) {

        switch (random.nextInt(10)) {
            case 0:
                return Blocks.JACK_O_LANTERN.getDefaultState();
            case 1:
                return Blocks.CARVED_PUMPKIN.getDefaultState();


            default:
                return Blocks.PUMPKIN.getDefaultState();

        }
    }

}
