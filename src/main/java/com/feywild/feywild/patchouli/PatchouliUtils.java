package com.feywild.feywild.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class PatchouliUtils {

    @Nullable
    public static <C extends Container, T extends Recipe<C>> T getRecipe(RecipeType<T> type, @Nullable String id) {
        return getRecipe(type, id == null ? null : ResourceLocation.tryParse(id));
    }
    
    @Nullable
    public static <C extends Container, T extends Recipe<C>, X extends T> X getRecipe(Class<X> cls, RecipeType<T> type, @Nullable String id) {
        return getRecipe(cls, type, id == null ? null : ResourceLocation.tryParse(id));
    }
    
    @Nullable
    public static <C extends Container, T extends Recipe<C>> T getRecipe(RecipeType<T> type, @Nullable ResourceLocation id) {
        if (id == null) return null;
        RecipeManager rm = Minecraft.getInstance().getConnection() == null ? null : Minecraft.getInstance().getConnection().getRecipeManager();
        //noinspection unchecked
        return rm == null ? null : (T) rm.byType(type).getOrDefault(id, null);
    }

    @Nullable
    public static <C extends Container, T extends Recipe<C>, X extends T> X getRecipe(Class<X> cls, RecipeType<T> type, @Nullable ResourceLocation id) {
        T recipe = getRecipe(type, id);
        if (recipe == null || cls.isAssignableFrom(recipe.getClass())) {
            //noinspection unchecked
            return (X) recipe;
        } else {
            return null;
        }
    }
}

