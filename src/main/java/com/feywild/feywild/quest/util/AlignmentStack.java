package com.feywild.feywild.quest.util;

import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.item.ItemStack;

public record AlignmentStack(Alignment alignment, ItemStack stack) {
    
}
