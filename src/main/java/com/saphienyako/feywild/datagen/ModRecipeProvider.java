package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    private static final List<ItemLike> FEY_GEM_SMELTABLES = List.of(ModItems.FEY_GEM.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        oreSmelting(pWriter, FEY_GEM_SMELTABLES, RecipeCategory.MISC, ModItems.FEY_DUST.get(), 0.1f, 100, "fey_dust");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FEY_GEM.get())
                .pattern("## ")
                .pattern("## ")
                .pattern("   ")
                .define('#', ModItems.FEY_DUST.get())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FEY_ALTAR.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" _ ")
                .define('#', Items.SMOOTH_STONE.asItem())
                .define('S', ModItems.FEY_GEM.get())
                .define('_', Items.SMOOTH_STONE_SLAB.asItem())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(pWriter);

        /* ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FEY_DUST.get(), 4)
                .requires(ModItems.FEY_GEM.get())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(pWriter); */


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FEY_INK_BOTTLE.get(), 1)
                .requires(ModItems.MANDRAKE.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.INK_SAC)
                .unlockedBy(getHasName(ModItems.MANDRAKE.get()), has(ModItems.MANDRAKE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.EMPTY_SUMMONING_SCROLL.get(), 1)
                .requires(ModItems.FEY_INK_BOTTLE.get())
                .requires(Items.FEATHER)
                .requires(Items.PAPER)
                .unlockedBy(getHasName(ModItems.FEY_INK_BOTTLE.get()), has(ModItems.FEY_INK_BOTTLE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FEYWILD_LEXICON.get(), 1)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.BOOK)
                .unlockedBy(getHasName(ModItems.FEY_DUST.get()), has(ModItems.FEY_DUST.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MANDRAKE_ROOT.get(), 1)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.POISONOUS_POTATO)
                .unlockedBy(getHasName(ModItems.FEY_DUST.get()), has(ModItems.FEY_DUST.get()))
                .save(pWriter);

        //TODO Fey Altar Recipes
        //TODO add tag unlocked in Fey Altar Recipes
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  Feywild.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
