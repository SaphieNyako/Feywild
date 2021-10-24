package com.feywild.feywild.data.recipe;

import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeExtension;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;

public interface StonecuttingExtension extends RecipeExtension {

    default void stonecutting(ItemLike input, ItemLike output) {
        stonecutting(Ingredient.of(input), output);
    }

    default void stonecutting(ItemLike input, ItemLike output, int amount) {
        stonecutting(Ingredient.of(input), output, amount);
    }
    default void stonecutting(Ingredient input, ItemLike output) {
        stonecutting(input, output, 1);
    }
    
    default void stonecutting(Ingredient input, ItemLike output, int amount) {
        SingleItemRecipeBuilder builder = SingleItemRecipeBuilder.stonecutting(input, output, amount);
        List<CriterionTriggerInstance> criteria = this.criteria(input);
        for (int i = 0; i < criteria.size(); i++) {
            builder.unlockedBy("has_item" + i, criteria.get(i));
        }
        builder.save(this.consumer(), this.provider().loc(output, "stonecutting"));
    }
}
