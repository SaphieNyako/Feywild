package com.feywild.feywild.recipes;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface IDwarvenAnvilRecipe extends IRecipe<IInventory> {

    ResourceLocation TYPE_ID = new ResourceLocation(FeywildMod.getInstance().modid, "dwarven_anvil");

    @Nonnull
    @Override
    default IRecipeType<?> getType() {
        return Objects.requireNonNull(Registry.RECIPE_TYPE.get(TYPE_ID));
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
        return new ItemStack(ModBlocks.dwarvenAnvil);
    }

    // We don't use vanilla matching against a vanilla inventory
    // Modded inventories normally are IItemHandlers.
    @Override
    @Deprecated
    default boolean matches(@Nonnull IInventory inventory, @Nonnull World world) {
        return false;
    }

    // Again we don't use vanilla inventories
    @Nonnull
    @Override
    @Deprecated
    default ItemStack assemble(@Nonnull IInventory inventory) {
        return this.getResultItem();
    }

    // Single method for matching and the result.
    // Has the advantage that we can write much shorter expressions to find
    // a recipe
    Optional<ItemStack> getResult(ItemStack schematics, List<ItemStack> inputs);

    int getMana();
}
