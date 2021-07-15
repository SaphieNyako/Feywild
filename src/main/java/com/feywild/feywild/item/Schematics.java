package com.feywild.feywild.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Schematics extends TooltipItem {

    TranslationTextComponent text;

    public Schematics(Properties properties, TranslationTextComponent text) {
        super(properties);
        this.text = text;
    }

    @Override
    public List<ITextComponent> getTooltip(ItemStack stack, World world) {
        return ImmutableList.of(text);
    }

}
