package com.saphienyako.feywild.recipe;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.saphienyako.feywild.Feywild;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FeyAltarRecipe implements IRecipe<IInventory> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final List<Ingredient> inputs;
    private final NonNullList<Ingredient> inputList;

    public FeyAltarRecipe(ResourceLocation id, ItemStack output, List<Ingredient> inputs) {
        this.id = id;
        this.output = output;
        this.inputs = ImmutableList.copyOf(inputs);
        this.inputList = NonNullList.withSize(this.inputs.size(), Ingredient.EMPTY);
        for (int i = 0; i < this.inputs.size(); i++) this.inputList.set(i, this.inputs.get(i));
    }

    @Override
    public boolean matches(@Nonnull IInventory inventory,@Nonnull World level) {
        NonNullList<ItemStack> stacks = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            stacks.set(i, inventory.getItem(i));
        }
        return matchesLists(inputs, stacks);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull IInventory inventory) {
        return this.getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }
    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return this.output.copy();
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }
    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputList;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static boolean matchesLists(List<Ingredient> ingredients, List<ItemStack> stacks) {
        if (ingredients.size() != stacks.size()) return false;
        List<ItemStack> left = new ArrayList<>(stacks);
        ingredients:
        for (Ingredient ingredient : ingredients) {
           Iterator<ItemStack> itr = left.iterator();
            while (itr.hasNext()) {
                if (ingredient.test(itr.next())) {
                    itr.remove();
                    continue ingredients;
                }
            }
            return false;
        }
        return true;
    }

    public static class Type implements IRecipeType<FeyAltarRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "fey_altar";

        @Override
        public String toString() {
            return ID;
        }
    }

    public static class Serializer implements IRecipeSerializer<FeyAltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Feywild.MOD_ID, "fey_altar");

        @Nonnull
        @Override
        public FeyAltarRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
            return new FeyAltarRecipe(recipeId, output, inputs.build());
        }

        @Nullable
        @Override
        public FeyAltarRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            int inputSize = buffer.readVarInt();
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            return new FeyAltarRecipe(recipeId, output, inputs.build());
        }

        @Override
        public void toNetwork(PacketBuffer buffer, FeyAltarRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.output, false);
        }

        @Override
        public IRecipeSerializer<?> setRegistryName(ResourceLocation resourceLocation) {
            return this;
        }
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<IRecipeSerializer<?>> getRegistryType() {
            @SuppressWarnings("unchecked")
            Class<IRecipeSerializer<?>> serializerClass = (Class<IRecipeSerializer<?>>) (Class<?>) IRecipeSerializer.class;
            return serializerClass;
        }
    }
}
