package com.feywild.feywild.trade.item;

import net.minecraft.world.item.ItemStack;

import java.util.Random;

// Factory for the empty stack
public class EmptyStackFactory implements StackFactory {

    public static final EmptyStackFactory INSTANCE = new EmptyStackFactory();

    private EmptyStackFactory() {

    }

    @Override
    public ItemStack createStack(Random random) {
        return ItemStack.EMPTY;
    }
}
