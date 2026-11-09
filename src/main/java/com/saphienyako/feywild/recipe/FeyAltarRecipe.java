package com.saphienyako.feywild.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record FeyAltarRecipe(List<Ingredient> inputItems, ItemStack output) implements Recipe<@NotNull FeyAltarRecipeInput> {

    @Override
    public boolean matches(FeyAltarRecipeInput inventory, @NotNull Level level) {
        List<ItemStack> stacks = new ArrayList<>();

        for (int i = 0; i < inventory.size() - 1; i++) {
            stacks.add(inventory.getItem(i));
        }

        return matchesLists(this.inputItems, stacks);
    }

    @Override
    public @NotNull ItemStack assemble(FeyAltarRecipeInput inventory) {
        return this.output;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NotNull String group() {
        return "";
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }


    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(this.inputItems);
        return list;
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

    @Override
    public @NotNull RecipeSerializer<@NotNull FeyAltarRecipe> getSerializer() {
        return ModRecipes.FEY_ALTAR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<@NotNull FeyAltarRecipe> getType() {
        return ModRecipes.FEY_ALTAR_TYPE.get();
    }

    public static class Serializer {

        public static final MapCodec<FeyAltarRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Ingredient.CODEC.listOf()
                                .fieldOf("ingredients")
                                .forGetter(FeyAltarRecipe::inputItems),

                        ItemStack.CODEC
                                .fieldOf("output")
                                .forGetter(FeyAltarRecipe::output)
                ).apply(inst, FeyAltarRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FeyAltarRecipe> STREAM_CODEC =
                StreamCodec.of(
                        Serializer::toNetwork,
                        Serializer::fromNetwork
                );

        public static final RecipeSerializer<@NotNull FeyAltarRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

        public static void toNetwork(RegistryFriendlyByteBuf buffer, FeyAltarRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }

        public static FeyAltarRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int inputSize = buffer.readVarInt();
            List<Ingredient> inputs = new ArrayList<>();

            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }

            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);

            return new FeyAltarRecipe(inputs, output);
        }
    }
}