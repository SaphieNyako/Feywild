package com.feywild.feywild.recipes;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.Registration;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;

import java.util.LinkedList;
import java.util.List;

public class ModRecipeTypes {

    public static final RegistryObject<AltarRecipe.Serializer> ALTAR_SERIALIZER =
            Registration.RECIPE_SERIALIZER.register("fey_altar", AltarRecipe.Serializer::new);

    public static final IRecipeType<IAltarRecipe> ALTAR_RECIPE = IRecipeType.register(IAltarRecipe.TYPE_ID.toString());


    public static final RegistryObject<DwarvenAnvilRecipe.Serializer> DWARVEN_ANVIL_SERIALIZER =
            Registration.RECIPE_SERIALIZER.register("dwarven_anvil", DwarvenAnvilRecipe.Serializer::new);

    public static final IRecipeType<IDwarvenAnvilRecipe> DWARVEN_ANVIL_RECIPE = IRecipeType.register(IDwarvenAnvilRecipe.TYPE_ID.toString());
            
    public static void registerRecipes() {
        System.out.println("HELLO FROM RECIPES!");

        Registry.register(Registry.RECIPE_TYPE, AltarRecipe.TYPE_ID, ALTAR_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, DwarvenAnvilRecipe.TYPE_ID, DWARVEN_ANVIL_RECIPE);
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
