package com.feywild.feywild.block.trees;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class WinterTreeSapling extends BaseSapling {

    public WinterTreeSapling() {
        super(WinterTree::new, AbstractBlock.Properties.copy(Blocks.OAK_SAPLING));
    }

    @Override
    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {

        super.performBonemeal(worldIn, rand, pos, state);


        if (state.getValue(STAGE) == 1) {

            if (!worldIn.isClientSide()) {
                //x
                int sizePodzol = 4;

                //-4 to 4
                for (int x = -sizePodzol; x <= sizePodzol; x++) {
                    for (int z = -sizePodzol; z <= sizePodzol; z++) {
                        if (rand.nextDouble() < 0.7 ) {
                            //Only if its a Dirt Block
                            if(worldIn.getBlockState(new BlockPos(pos.getX() -x, pos.getY()-1, pos.getZ()-z)).is(Blocks.GRASS_BLOCK)){

                                worldIn.setBlockAndUpdate(new BlockPos(pos.getX() - x, pos.getY() , pos.getZ() - z), Blocks.SNOW.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }


}
