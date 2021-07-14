package com.feywild.feywild.item;

import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public abstract class TooltipItem extends Item {

    public TooltipItem(Properties properties) {
        super(properties);
    }

    public abstract List<ITextComponent> getTooltip(ItemStack stack, World world);

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.addAll(getTooltip(stack, world));
        } else {
            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage", new KeybindTextComponent("key.sneak")));
        }
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
