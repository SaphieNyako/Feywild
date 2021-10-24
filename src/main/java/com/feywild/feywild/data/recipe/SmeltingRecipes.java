package com.feywild.feywild.data.recipe;

import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.recipe.crafting.SmeltingExtension;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class SmeltingRecipes extends SmeltingExtension {

    public SmeltingRecipes(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        this.smelting(consumer, ModItems.lesserFeyGem, ModItems.feyDust, 0.1f, 100);
    }
}
