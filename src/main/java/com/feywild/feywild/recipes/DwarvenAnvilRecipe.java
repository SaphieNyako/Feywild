package com.feywild.feywild.recipes;

import com.feywild.feywild.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DwarvenAnvilRecipe implements IDwarvenAnvilRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    @Nullable // Null = no schematics required
    private final Ingredient schematics;
    private final List<Ingredient> inputs;
    private final NonNullList<Ingredient> inputList;
    private final int mana;

    public DwarvenAnvilRecipe(ResourceLocation id, ItemStack output, @Nullable Ingredient schematics, List<Ingredient> inputs, int mana) {
        this.id = id;
        this.output = output;
        this.schematics = schematics;
        this.inputs = ImmutableList.copyOf(inputs);
        this.inputList = NonNullList.withSize(this.inputs.size(), Ingredient.EMPTY);
        for (int i = 0; i < this.inputs.size(); i++) this.inputList.set(i, this.inputs.get(i));
        this.mana = mana;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.dwarvenAnvil.serializer();
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull Container container, @Nonnull RegistryAccess registries) {
        return this.getResultItem(registries);
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull RegistryAccess registries) {
        return this.output.copy();
    }

    @Nullable
    public Ingredient getSchematics() {
        return this.schematics;
    }

    public List<Ingredient> getInputs() {
        return this.inputs;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputList;
    }

    @Override
    public Optional<ItemStack> getResult(RegistryAccess registries, ItemStack schematics, List<ItemStack> inputs) {
        if (this.schematics != null && (schematics.isEmpty() || !this.schematics.test(schematics))) {
            return Optional.empty();
        } else if (!Util.simpleMatch(this.inputs, inputs)) {
            return Optional.empty();
        } else {
            return Optional.of(this.getResultItem(registries));
        }
    }

    public static class Serializer implements RecipeSerializer<DwarvenAnvilRecipe> {

        @Nonnull
        @Override
        public DwarvenAnvilRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            int mana = GsonHelper.getAsInt(json, "mana");
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
            Ingredient schematics = json.has("schematics") ? Ingredient.fromJson(json.get("schematics")) : null;
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }
            return new DwarvenAnvilRecipe(recipeId, output, schematics, inputs, mana);
        }

        @Nullable
        @Override
        public DwarvenAnvilRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int mana = buffer.readVarInt();
            Ingredient schematics = buffer.readBoolean() ? Ingredient.fromNetwork(buffer) : null;
            int inputSize = buffer.readVarInt();
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            return new DwarvenAnvilRecipe(recipeId, output, schematics, inputs.build(), mana);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DwarvenAnvilRecipe recipe) {
            buffer.writeVarInt(recipe.mana);
            buffer.writeBoolean(recipe.schematics != null);
            if (recipe.schematics != null) recipe.schematics.toNetwork(buffer);
            buffer.writeVarInt(recipe.inputs.size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
