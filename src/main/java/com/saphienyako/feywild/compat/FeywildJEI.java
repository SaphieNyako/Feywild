package com.saphienyako.feywild.compat;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import com.saphienyako.feywild.recipe.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@SuppressWarnings("unused")
@JeiPlugin
public class FeywildJEI implements IModPlugin {

    //TODO Feywild Trades

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        registration.addRecipeCategories(
                new FeyAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {

        if (Minecraft.getInstance().level != null) {
            RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();


            List<FeyAltarRecipe> feyAltarRecipes = recipeManager.getAllRecipesFor(ModRecipes.FEY_ALTAR_TYPE.get())
                    .stream()
                    .map(RecipeHolder::value) // extract FeyAltarRecipe?
                    .toList();
            registration.addRecipes(FeyAltarRecipeCategory.FEY_ALTAR_TYPE, feyAltarRecipes);
        }
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }
}
