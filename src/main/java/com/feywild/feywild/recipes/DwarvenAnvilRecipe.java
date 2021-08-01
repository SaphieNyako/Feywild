package com.feywild.feywild.recipes;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.crafting.recipe.RecipeHelper;
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
        return id;
    }
    
    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.DWARVEN_ANVIL_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputList;
    }

    @Override
    public Optional<ItemStack> getResult(ItemStack schematics, List<ItemStack> inputs) {
        if (this.schematics != null && (schematics.isEmpty() || !this.schematics.test(schematics))) {
            return Optional.empty();
        } else if (!RecipeHelper.matches(this, inputs, true)) {
            return Optional.empty();
        } else {
            return Optional.of(this.getResultItem());
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DwarvenAnvilRecipe> {

        @Nonnull
        @Override
        public DwarvenAnvilRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            int mana = JSONUtils.getAsInt(json, "mana");
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
            Ingredient schematics = json.has("schematics") ? Ingredient.fromJson(json.get("schematics")) : null;
            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }
            return new DwarvenAnvilRecipe(recipeId, output, schematics, inputs, mana);
        }

        @Nullable
        @Override
        public DwarvenAnvilRecipe fromNetwork(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
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
        public void toNetwork(PacketBuffer buffer, DwarvenAnvilRecipe recipe) {
            buffer.writeVarInt(recipe.mana);
            buffer.writeBoolean(recipe.schematics != null);
            if (recipe.schematics != null) recipe.schematics.toNetwork(buffer);
            buffer.writeVarInt(recipe.inputs.size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.getResultItem(), false);
            buffer.writeInt(recipe.mana);
        }
    }
}
