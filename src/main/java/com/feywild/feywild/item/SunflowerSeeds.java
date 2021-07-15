package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.SunflowerStem;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class SunflowerSeeds extends Item {

    public SunflowerSeeds() {
        super(new Item.Properties().tab(FeywildMod.FEYWILD_TAB));
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockPos pos = context.getClickedPos().above();
        if (!context.getLevel().isClientSide && context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.GRASS_BLOCK)) {
            context.getLevel().setBlock(pos, ModBlocks.SUNFLOWER_STEM.get().defaultBlockState(), 2, 1);
            context.getLevel().setBlock(pos.above(1), ModBlocks.SUNFLOWER_STEM.get().defaultBlockState().setValue(SunflowerStem.HAS_MODEL, true), 2, 1);
            context.getLevel().setBlock(pos.above(2), ModBlocks.SUNFLOWER_STEM.get().defaultBlockState(), 2, 1);
            context.getLevel().setBlock(pos.above(3), ModBlocks.SUNFLOWER.get().defaultBlockState(), 2, 1);
            context.getItemInHand().shrink(1);
        }
        return ActionResultType.SUCCESS;
    }
}
