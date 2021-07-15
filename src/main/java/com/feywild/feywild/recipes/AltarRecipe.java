package com.feywild.feywild.recipes;

import com.feywild.feywild.block.ModBlocks;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AltarRecipe implements IAltarRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final List<Ingredient> inputs;

    public AltarRecipe(ResourceLocation id, ItemStack output, List<Ingredient> inputs) {
        this.id = id;
        this.output = output;
        this.inputs = new LinkedList<>();
        this.inputs.addAll(inputs);
        System.out.println(this.inputs.size());
        System.out.println(inputs.size());

    }

    @Override
    public boolean matches(@Nonnull IInventory inv, @Nonnull World worldIn) {
        return FeywildRecipes.matchesAltar(inputs, inv);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull IInventory inv) {
        return output;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    public List<Ingredient> getInputs() {
        return inputs;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.ALTAR_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.FEY_ALTAR.get());
    }

    public static class AltarRecipeType implements IRecipeType<AltarRecipe> {

        @Override
        public String toString() {
            return AltarRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AltarRecipe> {

        @Nonnull
        @Override
        public AltarRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));
            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();

            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }

            return new AltarRecipe(recipeId, output, inputs);
        }

        @Nullable
        @Override
        public AltarRecipe fromNetwork(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readInt()];

            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(buffer);
            }

            ItemStack output = buffer.readItem();

            return new AltarRecipe(recipeId, output, Arrays.asList(inputs));
        }

        @Override
        public void toNetwork(PacketBuffer buffer, AltarRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }

}
