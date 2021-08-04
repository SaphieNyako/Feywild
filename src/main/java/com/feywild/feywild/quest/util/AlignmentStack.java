package com.feywild.feywild.quest.util;

import com.feywild.feywild.quest.Alignment;
import net.minecraft.item.ItemStack;

public class AlignmentStack {
    
    public final Alignment alignment;
    private final ItemStack stack;

    public AlignmentStack(Alignment alignment, ItemStack stack) {
        this.alignment = alignment;
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }
}
