package com.saphienyako.feywild.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record FeyAltarRecipe(List<Ingredient> inputItems, ItemStack output) implements Recipe<FeyAltarRecipeInput>  {
    /*
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
    } */


    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(inputItems);
        return list;
    }

    @Override
    public boolean matches(FeyAltarRecipeInput inventory, @NotNull Level level) {
        List<ItemStack> stacks = new ArrayList<>();
        for(int i = 0; i < inventory.size()-1; i++) {
            stacks.add(i, inventory.getItem(i));
        }
        //Match list with all existing ingredient list  of recipes of FairyAltarRecipes.
        return matchesLists(this.inputItems, stacks);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull FeyAltarRecipeInput inventory, HolderLookup.@NotNull Provider registries) {
        return this.getResultItem(registries);
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        //return this.output.copy();
        return this.output;
    }

    /*
    public @NotNull ResourceLocation getId() {
        return this.id;
    } */


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
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.FEY_ALTAR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.FEY_ALTAR_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<FeyAltarRecipe> {
        public static final MapCodec<FeyAltarRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(FeyAltarRecipe::inputItems),
                ItemStack.CODEC.fieldOf("output").forGetter(FeyAltarRecipe::output)
        ).apply(inst, FeyAltarRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FeyAltarRecipe> STREAM_CODEC = StreamCodec.of(
                FeyAltarRecipe.Serializer::toNetwork, FeyAltarRecipe.Serializer::fromNetwork
        );


        public static void toNetwork(RegistryFriendlyByteBuf buffer, FeyAltarRecipe recipe) {
            //Write ingredients size
            buffer.writeVarInt(recipe.getIngredients().size());

            //Write List of Ingredients
                    //List of recipe.inputItems
                    //For each item on list
            for(Ingredient i : recipe.inputItems){
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, i);
            }
            //Write Output
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }

        /*
        @Override
        public void toNetwork(FriendlyByteBuf buffer, FeyAltarRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.output, false);
        } */



        public static FeyAltarRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int inputSize = buffer.readVarInt();
            List<Ingredient> inputs = new ArrayList<>();
            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }
            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            return new FeyAltarRecipe(inputs, output);
        }

        /*
        @Override
        public FeyAltarRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int inputSize = buffer.readVarInt();
            ImmutableList.Builder<Ingredient> inputs = ImmutableList.builder();
            for (int i = 0; i < inputSize; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ItemStack output = buffer.readItem();
            return new FeyAltarRecipe(recipeId, output, inputs.build());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, FeyAltarRecipe recipe) {
            buffer.writeVarInt(recipe.getIngredients().size());
            recipe.inputs.forEach(i -> i.toNetwork(buffer));
            buffer.writeItemStack(recipe.output, false);
        } */

        @Override
        public @NotNull MapCodec<FeyAltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, FeyAltarRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
