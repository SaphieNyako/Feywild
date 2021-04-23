package com.feywild.feywild.block.trees;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SpringTreeSapling extends BaseSapling {

    public SpringTreeSapling() {
        super(SpringTree::new, AbstractBlock.Properties.from(Blocks.OAK_SAPLING));
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {

        super.grow(worldIn, rand, pos, state);


        if (state.get(STAGE) == 1) {

            if (!worldIn.isRemote()) {
                //x
                int sizeFlowerPatch = 4;

                //-4 to 4
                for (int x = -sizeFlowerPatch; x <= sizeFlowerPatch; x++) {
                    for (int z = -sizeFlowerPatch; z <= sizeFlowerPatch; z++) {
                        if (rand.nextDouble() < 0.3) {
                            //Only if its a Dirt Block
                            if (worldIn.getBlockState(new BlockPos(pos.getX() - x, pos.getY() - 1, pos.getZ() - z)).isIn(Blocks.GRASS_BLOCK)) {

                                worldIn.setBlockState(new BlockPos(pos.getX() - x, pos.getY(), pos.getZ() - z), getBlocks(rand));
                            }
                        }
                    }
                }
            }
        }
    }

    public BlockState getBlocks(Random random) {

        switch (random.nextInt(30)) {
            case 0: return Blocks.RED_TULIP.getDefaultState();
            case 1: return Blocks.DANDELION.getDefaultState();
            case 2: return Blocks.ORANGE_TULIP.getDefaultState();
            case 3: return Blocks.BLUE_ORCHID.getDefaultState();
            case 4: return Blocks.ALLIUM.getDefaultState();
            case 5: return Blocks.AZURE_BLUET.getDefaultState();
            case 6: return Blocks.WHITE_TULIP.getDefaultState();
            case 7: return Blocks.LILY_OF_THE_VALLEY.getDefaultState();
            default: return Blocks.GRASS.getDefaultState();
        }


    }

}
