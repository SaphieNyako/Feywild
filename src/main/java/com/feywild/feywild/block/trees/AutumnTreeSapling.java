package com.feywild.feywild.block.trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class AutumnTreeSapling extends BaseSapling {

    public AutumnTreeSapling() {
        super(AutumnTree::new, Properties.from(Blocks.OAK_SAPLING));
    }


    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {

        super.grow(worldIn, rand, pos, state);


        if (state.get(STAGE) == 1) {

            if (!worldIn.isRemote()) {
                //x
                int sizePodzol = 4;

                //-4 to 4
                for (int x = -sizePodzol; x <= sizePodzol; x++) {
                    for (int z = -sizePodzol; z <= sizePodzol; z++) {
                        if (rand.nextDouble() < 0.7 ) {
                            //Only if its a Dirt Block
                           if(worldIn.getBlockState(new BlockPos(pos.getX() -x, pos.getY()-1, pos.getZ()-z)).isIn(Blocks.GRASS_BLOCK)){

                                worldIn.setBlockState(new BlockPos(pos.getX() - x, pos.getY() - 1, pos.getZ() - z), Blocks.PODZOL.getDefaultState());

                                if(rand.nextDouble() < 0.2) {
                                    worldIn.setBlockState(new BlockPos(pos.getX() - x, pos.getY() , pos.getZ() - z), getBlocks(rand));
                                }
                            }
                       }
                    }
                }
            }
        }
    }

    public BlockState getBlocks(Random random) {

        switch (random.nextInt(20)) {
            case 0: return Blocks.PUMPKIN.getDefaultState();
            case 1: return Blocks.CARVED_PUMPKIN.getDefaultState();
            case 2: return Blocks.RED_MUSHROOM.getDefaultState();
            case 4: return Blocks.BROWN_MUSHROOM.getDefaultState();

            default: return Blocks.FERN.getDefaultState();
        }
    }
}


