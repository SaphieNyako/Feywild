package com.feywild.feywild.recipes;

import com.feywild.feywild.util.Registration;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;

import java.util.LinkedList;
import java.util.List;

public class ModRecipeTypes {

    public static final RegistryObject<AltarRecipe.Serializer> ALTAR_SERIALIZER = Registration.RECIPE_SERIALIZER.register("fey_altar", AltarRecipe.Serializer::new);
    public static final IRecipeType<AltarRecipe> ALTAR_RECIPE = new AltarRecipe.AltarRecipeType();

    public static void registerRecipes() {
        System.out.println("HELLO FROM RECIPES!");

        Registry.register(Registry.RECIPE_TYPE, AltarRecipe.TYPE_ID, ALTAR_RECIPE);
    }


    public static List<IRecipe<?>> getRecipes(IRecipeType<?> recipeType, RecipeManager manager) {
        List<IRecipe<?>> recipeList = new LinkedList<>();
        for (IRecipe<?> recipe : manager.getRecipes()) {

            if (recipe.getType().equals(recipeType)) {
                recipeList.add(recipe);
            }

        }
        return recipeList;
    }


}
