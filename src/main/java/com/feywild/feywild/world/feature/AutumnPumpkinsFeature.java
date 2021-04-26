package com.feywild.feywild.world.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
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


    public AutumnPumpkinsFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }


    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int i = 0;

        for(int j = 0; j < 64; ++j)
        {
            BlockPos blockpos = new BlockPos(rand.nextInt(8)- rand.nextInt(8), rand.nextInt(4)-rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (world.getBlockState(blockpos).canBeReplacedByLeaves(world, blockpos) && world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK)
            {

                if (rand.nextInt(3) == 0)
                {
                    int num = rand.nextInt(50);

                    if (num > 10)
                    {
                        world.setBlockState(blockpos, Blocks.PUMPKIN.getDefaultState(),2);

                    }
                    else if (num > 1)
                    {
                        world.setBlockState(blockpos, Blocks.CARVED_PUMPKIN.getDefaultState(), 2);
                        //.setValue(CarvedPumpkinBlock.FACING, Direction.from3DDataValue(2 + rand.nextInt(4))), 2);
                    }
                    else
                    {
                        world.setBlockState(blockpos, Blocks.JACK_O_LANTERN.getDefaultState(),2); //4?
                        //.setValue(CarvedPumpkinBlock.FACING, Direction.from3DDataValue(2 + rand.nextInt(4))), 2);
                    }
                }
                else
                {
                    world.setBlockState(blockpos, Blocks.OAK_LEAVES.getDefaultState(), 2);

                }

                ++i;
            }
        }

        return i > 0;
    }
}
