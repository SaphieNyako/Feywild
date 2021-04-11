package com.feywild.feywild.recipes;

import com.feywild.feywild.block.ModBlocks;
import com.google.common.base.Preconditions;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AltarRecipe implements IAltarRecipe{
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;

    public AltarRecipe(ResourceLocation id, ItemStack output, Ingredient... inputs){
        Preconditions.checkArgument(inputs.length <= 5);
        this.id = id;
        this.output = output;
        this.inputs = NonNullList.from(Ingredient.EMPTY, inputs);
    }
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return FeywildRecipes.matches(inputs, inv);
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return output;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return getRecipeOutput().copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.ALTAR_SERIALIZER;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.FEY_ALTAR.get());
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return ModRecipeTypes.ALTAR_RECIPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AltarRecipe>{

        @Override
        public AltarRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();

            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.deserialize(jsonElement));
            }

            return new AltarRecipe(recipeId, output, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public AltarRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readVarInt()];

            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.read(buffer);
            }

            ItemStack output = buffer.readItemStack();

            return new AltarRecipe(recipeId, output, inputs);
        }

        @Override
        public void write(PacketBuffer buffer, AltarRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            for(Ingredient ing : recipe.getIngredients()){
                ing.write(buffer);
            }
            buffer.writeItemStack(recipe.getRecipeOutput(),false);
        }
    }

}
