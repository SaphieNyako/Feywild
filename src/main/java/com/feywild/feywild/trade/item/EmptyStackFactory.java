package com.feywild.feywild.trade.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

// Factory for the empty stack
public final class EmptyStackFactory implements StackFactory {

    public static final EmptyStackFactory INSTANCE = new EmptyStackFactory();

    private EmptyStackFactory() {

    }

    @Override
    public ItemStack createStack(RandomSource random) {
        return ItemStack.EMPTY;
    }
}
