package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public class FeyAltarRecipeBuilder {

    private final String name;
    private final RecipeOutput recipeOutput;
    private final HolderLookup.Provider lookupProvider;

    private final List<Ingredient> ingredients = new ArrayList<>();
    private ItemStackTemplate result;

    public FeyAltarRecipeBuilder(String name, RecipeOutput recipeOutput, HolderLookup.Provider lookupProvider) {
        this.name = name;
        this.recipeOutput = recipeOutput;
        this.lookupProvider = lookupProvider;
    }

    public FeyAltarRecipeBuilder ingredient(ItemLike item) {
        ingredients.add(Ingredient.of(item));
        return this;
    }

    public FeyAltarRecipeBuilder ingredient(TagKey<Item> tag) {
        HolderSet<Item> holders = lookupProvider
                .lookupOrThrow(Registries.ITEM)
                .getOrThrow(tag);

        ingredients.add(Ingredient.of(holders));
        return this;
    }

    public FeyAltarRecipeBuilder result(ItemLike item) {
        this.result = new ItemStackTemplate(item.asItem());
        return this;
    }

    public void save() {
        if (ingredients.size() != 5) {
            throw new IllegalStateException("Fey Altar recipes require exactly 5 ingredients: " + name);
        }

        if (result == null) {
            throw new IllegalStateException("Fey Altar recipe has no result: " + name);
        }

        FeyAltarRecipe recipe = new FeyAltarRecipe(ingredients, result);
        ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Feywild.MOD_ID, name));

        recipeOutput.accept(recipeKey, recipe, null);
    }

}
