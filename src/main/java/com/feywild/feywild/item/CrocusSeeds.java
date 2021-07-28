package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.Crocus;
import com.feywild.feywild.block.CrocusStem;
import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class CrocusSeeds extends Item {

    public CrocusSeeds() {
        super(new Properties().tab(FeywildMod.FEYWILD_TAB));
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockPos pos = context.getClickedPos().above();

        boolean canSpawn = true;

        for (int i = 0; i < 4; i++) {
            if (!context.getLevel().getBlockState(pos.above(i)).isAir()) {
                canSpawn = false;
                break;
            }
        }

        if (!context.getLevel().isClientSide && context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.GRASS_BLOCK) && canSpawn) {
            context.getLevel().setBlock(pos, ModBlocks.CROCUS_STEM.get().defaultBlockState().setValue(CrocusStem.HAS_MODEL, true), 2, 1);
            context.getLevel().setBlock(pos.above(1), ModBlocks.CROCUS_STEM.get().defaultBlockState(), 2, 1);
            context.getLevel().setBlock(pos.above(2), ModBlocks.CROCUS.get().defaultBlockState().setValue(Crocus.VARIANT, random.nextInt(2) + 1), 2, 1);
            context.getItemInHand().shrink(1);
        }
        return ActionResultType.SUCCESS;
    }

}
