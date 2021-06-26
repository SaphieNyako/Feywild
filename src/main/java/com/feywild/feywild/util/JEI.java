package com.feywild.feywild.util;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.recipes.AltarRecipe;
import com.feywild.feywild.recipes.AltarRecipeCategory;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import com.feywild.feywild.recipes.DwarvenAnvilRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEI implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(FeywildMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new AltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DwarvenAnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        List<AltarRecipe> altarRecipeList = new ArrayList<>();
        List<DwarvenAnvilRecipe> anvilRecipeList = new ArrayList<>();

        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        for (IRecipe recipe : manager.getRecipes()) {
            if (recipe instanceof AltarRecipe) {
                altarRecipeList.add((AltarRecipe) recipe);
            }
            if (recipe instanceof DwarvenAnvilRecipe) {
                anvilRecipeList.add((DwarvenAnvilRecipe) recipe);
            }
        }

        registration.addRecipes(altarRecipeList, AltarRecipeCategory.UID);
        registration.addRecipes(anvilRecipeList, DwarvenAnvilRecipeCategory.UID);
    }
}
