package com.feywild.feywild.data.recipe;

import com.feywild.feywild.item.ModItems;
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

public interface AnvilExtension extends RecipeExtension {

    default void gemTransmutation(ItemLike input, ItemLike result, int mana) {
        this.anvil(result, 2)
                .requires(input)
                .requires(input)
                .requires(input)
                .requires(input)
                .requires(input)
                .schematics(ModItems.schematicsGemTransmutation)
                .mana(mana)
                .build();
    }
    
    default AnvilRecipeBuilder anvil(ItemLike result) {
        return this.anvil(new ItemStack(result));
    }

    default AnvilRecipeBuilder anvil(ItemLike result, int amount) {
        return this.anvil(new ItemStack(result, amount));
    }

    default AnvilRecipeBuilder anvil(ItemStack result) {
        return new AnvilRecipeBuilder(this, result);
    }

    class AnvilRecipeBuilder {

        private final RecipeExtension ext;
        private final ItemStack result;
        private final List<Ingredient> inputs;
        @Nullable
        private Ingredient schematics;
        private int mana = -1;

        private AnvilRecipeBuilder(RecipeExtension ext, ItemStack result) {
            this.ext = ext;
            this.result = result;
            this.inputs = new ArrayList<>();
        }

        public AnvilRecipeBuilder requires(ItemLike item) {
            return this.requires(Ingredient.of(item));
        }

        public AnvilRecipeBuilder requires(Tag<Item> item) {
            return this.requires(Ingredient.of(item));
        }

        public AnvilRecipeBuilder requires(Ingredient item) {
            this.inputs.add(item);
            return this;
        }

        public AnvilRecipeBuilder schematics(ItemLike item) {
            return this.schematics(Ingredient.of(item));
        }

        public AnvilRecipeBuilder schematics(Tag<Item> item) {
            return this.schematics(Ingredient.of(item));
        }

        public AnvilRecipeBuilder schematics(Ingredient item) {
            if (this.schematics != null)
                throw new IllegalStateException("Can't build dwarven anvil recipe with multiple schematics");
            this.schematics = item;
            return this;
        }

        public AnvilRecipeBuilder mana(int mana) {
            if (this.mana >= 0) throw new IllegalStateException("Mana can only set once per dwarven anvil recipe.");
            this.mana = mana;
            return this;
        }

        public void build() {
            this.build(ext.provider().loc(this.result.getItem(), "dwarven_anvil"));
        }

        public void build(ResourceLocation id) {
            if (this.inputs.isEmpty())
                throw new IllegalStateException("Can't build dwarven anvil recipe without inputs: " + id);
            if (this.inputs.size() > 5)
                throw new IllegalStateException("Can't build dwarven anvil recipe with more than 5 inputs: " + id);
            if (this.mana < 0) throw new IllegalStateException("mana not set for dwarven anvil recipe: " + id);
            ext.consumer().accept(new FinishedRecipe() {

                @Override
                public void serializeRecipeData(@Nonnull JsonObject json) {
                    json.addProperty("mana", AnvilRecipeBuilder.this.mana);
                    json.add("output", DataUtils.serializeWithNbt(AnvilRecipeBuilder.this.result));
                    if (AnvilRecipeBuilder.this.schematics != null) {
                        json.add("schematics", AnvilRecipeBuilder.this.schematics.toJson());
                    }
                    JsonArray inputList = new JsonArray();
                    AnvilRecipeBuilder.this.inputs.forEach(i -> inputList.add(i.toJson()));
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
                    return ModRecipeTypes.DWARVEN_ANVIL_SERIALIZER;
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
