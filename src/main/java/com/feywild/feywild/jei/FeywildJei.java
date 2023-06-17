package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.recipes.AltarRecipe;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.feywild.feywild.trade.recipe.TradeRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@JeiPlugin
public class FeywildJei implements IModPlugin {

    public static List<TradeRecipe> clientDwarfTrades = List.of();

    private static IJeiRuntime runtime;

    public static void runtime(Consumer<IJeiRuntime> action) {
        if (runtime != null) {
            action.accept(runtime);
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(FeywildMod.getInstance().modid, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new AltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new DwarvenAnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new TradeRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.summerFeyAltar), AltarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.dwarvenAnvil), DwarvenAnvilRecipeCategory.TYPE);
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(AltarRecipeCategory.TYPE, rm.getAllRecipesFor(ModRecipeTypes.altar).stream()
                .filter(r -> r instanceof AltarRecipe)
                .map(r -> (AltarRecipe) r)
                .toList()
        );
        registration.addRecipes(DwarvenAnvilRecipeCategory.TYPE, rm.getAllRecipesFor(ModRecipeTypes.dwarvenAnvil).stream()
                .filter(r -> r instanceof DwarvenAnvilRecipe)
                .map(r -> (DwarvenAnvilRecipe) r)
                .toList()
        );
        registration.addRecipes(TradeRecipeCategory.TYPE, clientDwarfTrades);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime runtime) {
        FeywildJei.runtime = runtime;
    }
}
