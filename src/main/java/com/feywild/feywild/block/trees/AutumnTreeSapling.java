package com.feywild.feywild.block.trees;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class AutumnTreeSapling extends BaseSapling {

    public AutumnTreeSapling() {
        super(AutumnTree::new, Properties.copy(Blocks.OAK_SAPLING));
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

                                if(rand.nextDouble() < 0.2) {
                                    worldIn.setBlockAndUpdate(new BlockPos(pos.getX() - x, pos.getY() , pos.getZ() - z), getBlocks(rand));
                                }
                    }
                }
            }
        }
    }

    public BlockState getBlocks(Random random) {

        switch (random.nextInt(20)) {
            case 0: return Blocks.PUMPKIN.defaultBlockState();
            case 1: return Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2: return Blocks.RED_MUSHROOM.defaultBlockState();
            case 4: return Blocks.BROWN_MUSHROOM.defaultBlockState();

            default: return Blocks.FERN.defaultBlockState();
        }
    }
}


