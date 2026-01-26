package com.saphienyako.feywild.entity.base.intereface;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

public interface ITradeable {

    public SoundEvent getTradeSound();

    public ItemStack getTradeItem();

    public boolean isTradeItem(ItemStack stack);

    public ItemStack getTradeResult();
}
