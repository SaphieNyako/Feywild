package com.feywild.feywild.trade.item;

import net.minecraft.item.ItemStack;

import java.util.Random;

public class EmptyStackFactory implements StackFactory {

    public static final EmptyStackFactory INSTANCE = new EmptyStackFactory();
    
    private EmptyStackFactory() {
        
    }
    
    @Override
    public ItemStack createStack(Random random) {
        return ItemStack.EMPTY;
    }
}
