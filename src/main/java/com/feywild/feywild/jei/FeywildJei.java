package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.recipes.AltarRecipe;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class FeywildJei implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(FeywildMod.getInstance().modid, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new AltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DwarvenAnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.feyAltar), AltarRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.dwarvenAnvil), DwarvenAnvilRecipeCategory.UID);
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {

        List<AltarRecipe> altarRecipeList = new ArrayList<>();
        List<DwarvenAnvilRecipe> anvilRecipeList = new ArrayList<>();

        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        for (IRecipe<?> recipe : manager.getRecipes()) {
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
