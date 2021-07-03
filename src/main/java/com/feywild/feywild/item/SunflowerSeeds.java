package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.SunflowerStem;
import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SunflowerSeeds extends Item {

    public SunflowerSeeds() {
        super(new Item.Properties().tab(FeywildMod.FEYWILD_TAB));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockPos pos = context.getClickedPos().above();
        if(!context.getLevel().isClientSide && context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.GRASS_BLOCK)) {
            context.getLevel().setBlock(pos, ModBlocks.SUNFLOWER_STEM.get().defaultBlockState(), 2, 1);
            context.getLevel().setBlock(pos.above(1), ModBlocks.SUNFLOWER_STEM.get().defaultBlockState().setValue(SunflowerStem.HAS_MODEL, true), 2, 1);
            context.getLevel().setBlock(pos.above(2), ModBlocks.SUNFLOWER_STEM.get().defaultBlockState(), 2, 1);
            context.getLevel().setBlock(pos.above(3), ModBlocks.SUNFLOWER.get().defaultBlockState(), 2, 1);
            context.getItemInHand().shrink(1);
        }
        return ActionResultType.SUCCESS;
    }
}
