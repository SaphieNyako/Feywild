package com.feywild.feywild.data.recipe;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.crafting.CraftingHelper2;
import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AnvilRecipes extends RecipeProviderBase {

    public AnvilRecipes(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Nonnull
    @Override
    public String getName() {
        return this.mod.modid + " fey anvil recipes";
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        this.gemTransmutation(consumer, ModItems.lesserFeyGem, ModItems.greaterFeyGem, 50);
        this.gemTransmutation(consumer, ModItems.greaterFeyGem, ModItems.shinyFeyGem, 100);
        this.gemTransmutation(consumer, ModItems.shinyFeyGem, ModItems.brilliantFeyGem, 150);

        /* EXAMPLE
        this.anvil(ModBlocks.feyAltar)
                .requires(Tags.Items.INGOTS_GOLD)
                .requires(Tags.Items.INGOTS_GOLD)
                .requires(ModItems.lesserFeyGem)
                .requires(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .requires(ModItems.brilliantFeyGem)
                .schematics(ModItems.schematicsFeyAltar)
                .mana(1000)
                .build(consumer); */
    }

    private void gemTransmutation(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike result, int mana) {
        this.anvil(result, 2)
                .requires(input)
                .requires(input)
                .requires(input)
                .requires(input)
                .requires(input)
                .schematics(ModItems.schematicsGemTransmutation)
                .mana(mana)
                .build(consumer);
    }

    private AnvilRecipeBuilder anvil(ItemLike result) {
        return this.anvil(new ItemStack(result));
    }

    private AnvilRecipeBuilder anvil(ItemLike result, int amount) {
        return this.anvil(new ItemStack(result, amount));
    }

    private AnvilRecipeBuilder anvil(ItemStack result) {
        return new AnvilRecipeBuilder(result);
    }

    private class AnvilRecipeBuilder {

        private final ItemStack result;
        private final List<Ingredient> inputs;
        @Nullable
        private Ingredient schematics;
        private int mana = -1;

        public AnvilRecipeBuilder(ItemStack result) {
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

        public void build(Consumer<FinishedRecipe> consumer) {
            this.build(consumer, AnvilRecipes.this.loc(this.result.getItem(), "dwarven_anvil"));
        }

        public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            if (this.inputs.isEmpty())
                throw new IllegalStateException("Can't build dwarven anvil recipe without inputs: " + id);
            if (this.inputs.size() > 5)
                throw new IllegalStateException("Can't build dwarven anvil recipe with more than 5 inputs: " + id);
            if (this.mana < 0) throw new IllegalStateException("mana not set for dwarven anvil recipe: " + id);
            consumer.accept(new FinishedRecipe() {

                @Override
                public void serializeRecipeData(@Nonnull JsonObject json) {
                    json.addProperty("mana", AnvilRecipeBuilder.this.mana);
                    json.add("output", CraftingHelper2.serializeItemStack(AnvilRecipeBuilder.this.result, true));
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
