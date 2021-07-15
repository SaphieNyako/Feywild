package com.feywild.feywild.entity.util.trades;

import net.minecraft.item.ItemStack;

public class TradeData {

    private final int maxUses;

    private final int givenXP;

    private final float priceMultiplier;

    private final ItemStack stackIn;

    public TradeData(int maxUses, int givenXP, float priceMultiplier, ItemStack stackIn){
        this.maxUses = maxUses;
        this.givenXP = givenXP;
        this.priceMultiplier = priceMultiplier;
        this.stackIn = stackIn;

    }

    public int getMaxUses() {
        return maxUses;
    }

    public int getGivenXP() {
        return givenXP;
    }

    public float getPriceMultiplier() {
        return priceMultiplier;
    }

    public ItemStack getStack() {
        return stackIn;
    }
}
