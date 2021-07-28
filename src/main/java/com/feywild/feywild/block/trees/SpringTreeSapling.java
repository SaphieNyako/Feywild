package com.feywild.feywild.block.trees;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class SpringTreeSapling extends BaseSapling {

    public SpringTreeSapling() {
        super(SpringTree::new, AbstractBlock.Properties.copy(Blocks.OAK_SAPLING));
    }

    @Override
    public void performBonemeal(@Nonnull ServerWorld worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, BlockState state) {

        super.performBonemeal(worldIn, rand, pos, state);

        if (state.getValue(STAGE) == 1) {

            if (!worldIn.isClientSide()) {
                //x
                int sizeFlowerPatch = 4;

                //-4 to 4
                for (int x = -sizeFlowerPatch; x <= sizeFlowerPatch; x++) {
                    for (int z = -sizeFlowerPatch; z <= sizeFlowerPatch; z++) {
                        if (rand.nextDouble() < 0.3) {
                            //Only if its a Dirt Block
                            if (worldIn.getBlockState(new BlockPos(pos.getX() - x, pos.getY() - 1, pos.getZ() - z)).is(Blocks.GRASS_BLOCK)) {

                                worldIn.setBlockAndUpdate(new BlockPos(pos.getX() - x, pos.getY(), pos.getZ() - z), getBlocks(rand));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public BlockState getBlocks(Random random) {

        switch (random.nextInt(30)) {
            case 0:
                return Blocks.RED_TULIP.defaultBlockState();
            case 1:
                return Blocks.DANDELION.defaultBlockState();
            case 2:
                return Blocks.ORANGE_TULIP.defaultBlockState();
            case 3:
                return Blocks.BLUE_ORCHID.defaultBlockState();
            case 4:
                return Blocks.ALLIUM.defaultBlockState();
            case 5:
                return Blocks.AZURE_BLUET.defaultBlockState();
            case 6:
                return Blocks.WHITE_TULIP.defaultBlockState();
            case 7:
                return Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            default:
                return Blocks.GRASS.defaultBlockState();
        }
    }

}
