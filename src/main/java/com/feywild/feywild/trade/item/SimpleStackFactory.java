package com.feywild.feywild.trade.item;

import net.minecraft.item.ItemStack;

import java.util.Random;

// Factory for an ItemStack with optional random count
public class SimpleStackFactory implements StackFactory {

    private final ItemStack stack;
    private final int minAmount;
    private final int maxAmount;

    public SimpleStackFactory(ItemStack stack, int minAmount, int maxAmount) {
        this.stack = stack.copy();
        this.minAmount = Math.min(minAmount, maxAmount);
        this.maxAmount = Math.min(minAmount, maxAmount);
        if (minAmount <= 0) throw new IllegalStateException("Can't create stack factory with empty count");
    }

    @Override
    public ItemStack createStack(Random random) {
        ItemStack stack = this.stack.copy();
        stack.setCount(minAmount + random.nextInt(1 + (maxAmount - minAmount)));
        return stack;
    }
}
