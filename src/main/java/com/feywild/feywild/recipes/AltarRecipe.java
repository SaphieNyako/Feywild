package com.feywild.feywild.recipes;

import com.feywild.feywild.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

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
        return id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.ALTAR_SERIALIZER;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputList;
    }

    @Override
    public Optional<ItemStack> getResult(List<ItemStack> inputs) {
        return Util.simpleMatch(this.inputs, inputs) ? Optional.of(this.getResultItem()) : Optional.empty();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AltarRecipe> {

        @Nonnull
        @Override
        public AltarRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
            return new AltarRecipe(recipeId, output, inputs.build());
        }

        @Nullable
        @Override
        public AltarRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            int inputSize = buffer.readVarInt();
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            return new AltarRecipe(recipeId, output, inputs.build());
        }

        @Override
        public void toNetwork(PacketBuffer buffer, AltarRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
