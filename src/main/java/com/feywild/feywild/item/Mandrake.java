package com.feywild.feywild.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Mandrake extends TooltipItem {

    public Mandrake(Item.Properties properties) {
        super(properties);
    }

    @Override
    public List<ITextComponent> getTooltip(ItemStack stack, World world) {
        return ImmutableList.of(new TranslationTextComponent("message.feywild.mandrake"));
    }
}


