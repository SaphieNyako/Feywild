package com.feywild.feywild.data.recipe;

import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.data.CraftingHelper2;
import io.github.noeppi_noeppi.libx.data.provider.recipe.AnyRecipeProvider;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AltarRecipes extends AnyRecipeProvider {

    public AltarRecipes(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Nonnull
    @Override
    public String getName() {
        return this.mod.modid + " fey anvil recipes";
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        this.altar(ModItems.feywildMusicDisc)
                .requires(ItemTags.MUSIC_DISCS)
                .requires(ModTrees.springTree.getSapling())
                .requires(ModTrees.summerTree.getSapling())
                .requires(ModTrees.autumnTree.getSapling())
                .requires(ModTrees.winterTree.getSapling())
                .build(consumer);

        this.altar(ModItems.summoningScrollSpringPixie)
                .requires(ModTrees.springTree.getSapling())
                .requires(Blocks.OXEYE_DAISY)
                .requires(Items.WHEAT_SEEDS)
                .requires(Items.EGG)
                .requires(ModItems.summoningScroll)
                .build(consumer);

        this.altar(ModItems.summoningScrollSummerPixie)
                .requires(ModTrees.summerTree.getSapling())
                .requires(Blocks.SUNFLOWER)
                .requires(Items.HONEYCOMB)
                .requires(Items.GOLDEN_SWORD)
                .requires(ModItems.summoningScroll)
                .build(consumer);

        this.altar(ModItems.summoningScrollAutumnPixie)
                .requires(ModTrees.autumnTree.getSapling())
                .requires(Blocks.CARVED_PUMPKIN)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.IRON_HOE)
                .requires(ModItems.summoningScroll)
                .build(consumer);

        this.altar(ModItems.summoningScrollWinterPixie)
                .requires(ModTrees.winterTree.getSapling())
                .requires(Items.SNOWBALL)
                .requires(Blocks.ICE)
                .requires(Items.ROTTEN_FLESH)
                .requires(ModItems.summoningScroll)
                .build(consumer);

        this.altar(ModItems.summoningScrollBeeKnight)
                .requires(ModItems.summoningScrollSummerPixie)
                .requires(ModItems.honeycomb)
                .requires(Blocks.BEE_NEST)
                .requires(Items.LEAD)
                .requires(ModItems.summoningScroll)
                .build(consumer);

        this.altar(ModItems.summoningScrollMandragora)
                .requires(ModItems.magicalHoneyCookie)
                .requires(ModItems.mandrake)
                .requires(ModTrees.autumnTree.getSapling())
                .requires(Blocks.NOTE_BLOCK)
                .requires(ModItems.summoningScroll)
                .build(consumer);

        this.altar(ModItems.summoningScrollShroomling)
                .requires(Blocks.RED_MUSHROOM_BLOCK)
                .requires(Blocks.MUSHROOM_STEM)
                .requires(ModItems.greaterFeyGem)
                .requires(Blocks.COMPOSTER)
                .requires(ModItems.summoningScroll)
                .build(consumer);

    }

    private AltarRecipeBuilder altar(IItemProvider result) {
        return this.altar(new ItemStack(result));
    }

    private AltarRecipeBuilder altar(IItemProvider result, int amount) {
        return this.altar(new ItemStack(result, amount));
    }

    private AltarRecipeBuilder altar(ItemStack result) {
        return new AltarRecipeBuilder(result);
    }

    private class AltarRecipeBuilder {

        private final ItemStack result;
        private final List<Ingredient> inputs;

        public AltarRecipeBuilder(ItemStack result) {
            this.result = result;
            this.inputs = new ArrayList<>();
        }

        public AltarRecipeBuilder requires(IItemProvider item) {
            return this.requires(Ingredient.of(item));
        }

        public AltarRecipeBuilder requires(ITag<Item> item) {
            return this.requires(Ingredient.of(item));
        }

        public AltarRecipeBuilder requires(Ingredient item) {
            this.inputs.add(item);
            return this;
        }

        public void build(Consumer<IFinishedRecipe> consumer) {
            this.build(consumer, AltarRecipes.this.loc(this.result.getItem(), "fey_altar"));
        }

        public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
            if (this.inputs.isEmpty())
                throw new IllegalStateException("Can't build fey altar recipe without inputs: " + id);
            if (this.inputs.size() > 5)
                throw new IllegalStateException("Can't build fey altar recipe with more than 5 inputs: " + id);
            consumer.accept(new IFinishedRecipe() {

                @Override
                public void serializeRecipeData(@Nonnull JsonObject json) {
                    json.add("output", CraftingHelper2.serializeItemStack(AltarRecipeBuilder.this.result, true));
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
                public IRecipeSerializer<?> getType() {
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
