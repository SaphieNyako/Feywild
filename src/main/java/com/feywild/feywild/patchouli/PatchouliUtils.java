package com.feywild.feywild.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatchouliUtils {

    public static <T extends Recipe<C>, C extends Container> T getRecipe(RecipeType<T> type, ResourceLocation id) {
        Map<ResourceLocation, T> map = new HashMap<>();

        List<T> recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(type);
        recipes.forEach(T -> map.putIfAbsent(T.getId(), T));

        return map.get(id);
    }
}

