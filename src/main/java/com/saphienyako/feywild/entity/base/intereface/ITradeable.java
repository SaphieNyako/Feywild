package com.saphienyako.feywild.entity.base.intereface;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

public interface ITradeable {

    SoundEvent getTradeSound();

    /** @noinspection unused*/
    ItemStack getTradeItem();

    boolean isTradeItem(ItemStack stack);

    ItemStack getTradeResult();
}
