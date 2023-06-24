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
import java.util.List;
import java.util.Optional;

public class AltarRecipe implements IAltarRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final List<Ingredient> inputs;
    private final NonNullList<Ingredient> inputList;

    public AltarRecipe(ResourceLocation id, ItemStack output, List<Ingredient> inputs) {
        this.id = id;
        this.output = output;
        this.inputs = ImmutableList.copyOf(inputs);
        this.inputList = NonNullList.withSize(this.inputs.size(), Ingredient.EMPTY);
        for (int i = 0; i < this.inputs.size(); i++) this.inputList.set(i, this.inputs.get(i));
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.altar.serializer();
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

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputList;
    }

    @Override
    public Optional<ItemStack> getResult(RegistryAccess registries, List<ItemStack> inputs) {
        return Util.simpleMatch(this.inputs, inputs) ? Optional.of(this.getResultItem(registries)) : Optional.empty();
    }

    public static class Serializer implements RecipeSerializer<AltarRecipe> {

        @Nonnull
        @Override
        public AltarRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
            return new AltarRecipe(recipeId, output, inputs.build());
        }

        @Nullable
        @Override
        public AltarRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            int inputSize = buffer.readVarInt();
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            return new AltarRecipe(recipeId, output, inputs.build());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AltarRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
