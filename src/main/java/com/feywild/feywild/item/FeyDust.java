package com.feywild.feywild.item;

import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class FeyDust extends Item {

    public FeyDust(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        if (KeyboardHelper.isHoldingShift()) {

            tooltip.add(new TranslationTextComponent("message.feywild.fey_dust"));

        } else {

            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage"));
            //tooltip.add(new StringTextComponent("Hold "+ "\u00A7e" + "SHIFT" + "\u00A77" + " for more information."));
        }

        super.appendHoverText(stack, world, tooltip, flag);
    }

    //Test
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (playerIn.level.isClientSide()) return ActionResultType.SUCCESS;

        if (target instanceof SheepEntity) {
            target.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2));

        } else {
            target.addEffect(new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 2));
        }

        stack.shrink(1);

        return ActionResultType.SUCCESS;
    }
}
