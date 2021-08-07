package com.feywild.feywild.quest.util;

import com.feywild.feywild.quest.Alignment;
import net.minecraft.item.crafting.Ingredient;

public class FeyGift {

    public final Alignment alignment;
    public final Ingredient ingredient;

    public FeyGift(Alignment alignment, Ingredient ingredient) {
        this.alignment = alignment;
        this.ingredient = ingredient;
    }
}