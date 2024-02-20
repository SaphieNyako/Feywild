package com.feywild.feywild.recipes;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface IAltarRecipe extends Recipe<Container> {

    @Nonnull
    @Override
    default RecipeType<?> getType() {
        return ModRecipeTypes.altar;
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Nonnull
    @Override
    default ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.summerFeyAltar);
    }

    // We don't use vanilla matching against a vanilla inventory
    // Modded inventories normally are IItemHandlers.
    @Override
    @Deprecated
    default boolean matches(@Nonnull Container container, @Nonnull Level level) {
        return false;
    }

    Optional<ItemStack> getResult(RegistryAccess registries, List<ItemStack> inputs);
}
