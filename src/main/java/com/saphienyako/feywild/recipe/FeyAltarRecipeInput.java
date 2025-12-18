package com.saphienyako.feywild.recipe;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public class FeyAltarRecipeInput implements RecipeInput {

    private final SimpleContainer container;

    public FeyAltarRecipeInput(SimpleContainer container) {
        this.container = container;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return container.getItem(index);
    }

    @Override
    public int size() {
        return container.getContainerSize();
    }
}
