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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DwarvenAnvilRecipe implements IDwarvenAnvilRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final List<Ingredient> inputs;
    private final int manaUsage;

    public DwarvenAnvilRecipe(ResourceLocation id, ItemStack output, List<Ingredient> inputs, int manaUsage) {
        this.id = id;
        this.output = output;
        this.manaUsage = manaUsage;
        this.inputs = new LinkedList<>();
        for (int i = 0; i < inputs.size(); i++)
            this.inputs.add(inputs.get(i));
        System.out.println(this.inputs.size());
        System.out.println(inputs.size());

    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return FeywildRecipes.matches(inputs, inv);
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public int getManaUsage() {
        return manaUsage;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.DWARVEN_ANVIL_SERIALIZER.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.DWARVEN_ANVIL.get());
    }

    public static class DwarvenAnvilRecipeType implements IRecipeType<DwarvenAnvilRecipe> {

        @Override
        public String toString() {
            return DwarvenAnvilRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DwarvenAnvilRecipe> {

        @Override
        public DwarvenAnvilRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            int manaUsage = JSONUtils.getAsInt(json, "mana_usage"); //added

            ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));
            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();

            for (JsonElement jsonElement : ingredients) {
                inputs.add(Ingredient.fromJson(jsonElement));
            }

            return new DwarvenAnvilRecipe(recipeId, output, inputs, manaUsage);
        }

        @Nullable
        @Override
        public DwarvenAnvilRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient[] inputs = new Ingredient[buffer.readInt()];

            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(buffer);
            }

            ItemStack output = buffer.readItem();
            int manaUsage = buffer.readInt(); //added

            return new DwarvenAnvilRecipe(recipeId, output, Arrays.asList(inputs), manaUsage);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, DwarvenAnvilRecipe recipe) {

            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(), false);

        }
    }
}
