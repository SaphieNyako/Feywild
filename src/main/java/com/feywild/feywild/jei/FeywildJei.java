package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.jei.util.FeywildJeiReloader;
import com.feywild.feywild.recipes.AltarRecipe;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@JeiPlugin
public class FeywildJei implements IModPlugin {

    private static IJeiRuntime runtime;
    
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
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.feyAltar), AltarRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.dwarvenAnvil), DwarvenAnvilRecipeCategory.UID);
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(rm.getAllRecipesFor(ModRecipeTypes.ALTAR).stream().filter(r -> r instanceof AltarRecipe).collect(Collectors.toList()), AltarRecipeCategory.UID);
        registration.addRecipes(rm.getAllRecipesFor(ModRecipeTypes.DWARVEN_ANVIL).stream().filter(r -> r instanceof DwarvenAnvilRecipe).collect(Collectors.toList()), DwarvenAnvilRecipeCategory.UID);
        registration.addRecipes(FeywildJeiReloader.clientTrades, TradeRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime runtime) {
        FeywildJei.runtime = runtime;
    }
    
    public static void runtime(Consumer<IJeiRuntime> action) {
        if (runtime != null) {
            action.accept(runtime);
        }
    }
}
