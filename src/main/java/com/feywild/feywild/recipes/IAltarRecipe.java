package com.feywild.feywild.recipes;

import com.feywild.feywild.block.ModBlocks;
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

    @Override
    default boolean isSpecial() {
        return false;
    }

    @Nonnull
    @Override
    default ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.feyAltar);
    }

    // We don't use vanilla matching against a vanilla inventory
    // Modded inventories normally are IItemHandlers.
    @Override
    @Deprecated
    default boolean matches(@Nonnull Container container, @Nonnull Level level) {
        return false;
    }

    // Again we don't use vanilla inventories
    @Nonnull
    @Override
    @Deprecated
    default ItemStack assemble(@Nonnull Container container) {
        return this.getResultItem();
    }

    Optional<ItemStack> getResult(List<ItemStack> inputs);
}
