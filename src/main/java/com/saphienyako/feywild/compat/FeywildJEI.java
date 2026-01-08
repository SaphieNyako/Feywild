package com.saphienyako.feywild.compat;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

@JeiPlugin
public class FeywildJEI implements IModPlugin {

    @Override
    public @Nonnull ResourceLocation getPluginUid() {
        return new ResourceLocation(Feywild.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FeyAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<FeyAltarRecipe> feyAltarRecipes = recipeManager.getAllRecipesFor(FeyAltarRecipe.Type.INSTANCE);
        registration.addRecipes(feyAltarRecipes, FeyAltarRecipeCategory.UID);
    }

    @Override
    public void registerGuiHandlers(@Nonnull IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }
}
