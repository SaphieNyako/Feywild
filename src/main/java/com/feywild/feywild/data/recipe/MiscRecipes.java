package com.feywild.feywild.data.recipe;

import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Consumer;


public class MiscRecipes extends AnyRecipeProvider {

    public MiscRecipes(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Nonnull
    @Override
    public String getName() {
        return this.mod.modid + " miscellaneous recipes";
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        stonecutting(Ingredient.of(ModItems.greaterFeyGem), ModItems.lesserFeyGem, 2)
                .unlocks("has_item", has((ModItems.greaterFeyGem)))
                .save(consumer, this.loc(ModItems.lesserFeyGem, "cutting"));
        
        stonecutting(Ingredient.of(ModItems.shinyFeyGem), ModItems.greaterFeyGem, 2)
                .unlocks("has_item", has((ModItems.shinyFeyGem)))
                .save(consumer, this.loc(ModItems.greaterFeyGem, "cutting"));
        
        stonecutting(Ingredient.of(ModItems.brilliantFeyGem), ModItems.shinyFeyGem, 2)
                .unlocks("has_item", has((ModItems.brilliantFeyGem)))
                .save(consumer, this.loc(ModItems.shinyFeyGem, "cutting"));
    }
}
