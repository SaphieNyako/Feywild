package com.feywild.feywild.data.recipe;

import com.feywild.feywild.data.DataUtils;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeExtension;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface AltarExtension extends RecipeExtension {

    default AltarRecipeBuilder altar(ItemLike result) {
        return this.altar(new ItemStack(result));
    }

    default AltarRecipeBuilder altar(ItemLike result, int amount) {
        return this.altar(new ItemStack(result, amount));
    }

    default AltarRecipeBuilder altar(ItemStack result) {
        return new AltarRecipeBuilder(this, result);
    }

    class AltarRecipeBuilder {

        private final RecipeExtension ext;
        private final ItemStack result;
        private final List<Ingredient> inputs;

        public AltarRecipeBuilder(RecipeExtension ext, ItemStack result) {
            this.ext = ext;
            this.result = result;
            this.inputs = new ArrayList<>();
        }

        public AltarRecipeBuilder requires(ItemLike item) {
            return this.requires(Ingredient.of(item));
        }

        public AltarRecipeBuilder requires(Tag<Item> item) {
            return this.requires(Ingredient.of(item));
        }

        public AltarRecipeBuilder requires(Ingredient item) {
            this.inputs.add(item);
            return this;
        }

        public void build() {
            this.build(ext.provider().loc(this.result.getItem(), "fey_altar"));
        }

        public void build(ResourceLocation id) {
            if (this.inputs.isEmpty())
                throw new IllegalStateException("Can't build fey altar recipe without inputs: " + id);
            if (this.inputs.size() > 5)
                throw new IllegalStateException("Can't build fey altar recipe with more than 5 inputs: " + id);
            ext.consumer().accept(new FinishedRecipe() {

                @Override
                public void serializeRecipeData(@Nonnull JsonObject json) {
                    json.add("output", DataUtils.serializeWithNbt(AltarRecipeBuilder.this.result));
                    JsonArray inputList = new JsonArray();
                    AltarRecipeBuilder.this.inputs.forEach(i -> inputList.add(i.toJson()));
                    json.add("ingredients", inputList);
                }

                @Nonnull
                @Override
                public ResourceLocation getId() {
                    return id;
                }

                @Nonnull
                @Override
                public RecipeSerializer<?> getType() {
                    return ModRecipeTypes.ALTAR_SERIALIZER;
                }

                @Nullable
                @Override
                public JsonObject serializeAdvancement() {
                    return null;
                }

                @Nullable
                @Override
                public ResourceLocation getAdvancementId() {
                    return null;
                }
            });
        }
    }
}
