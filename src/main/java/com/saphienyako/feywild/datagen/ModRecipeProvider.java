package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {

        List<ItemLike> FEY_GEM_SMELTABLES = List.of(ModItems.FEY_GEM.get());

        oreSmelting(recipeOutput, FEY_GEM_SMELTABLES, RecipeCategory.MISC, ModItems.FEY_DUST.get(), 0.1f, 100, "fey_dust");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FEY_GEM.get())
                .pattern("## ")
                .pattern("## ")
                .pattern("   ")
                .define('#', ModItems.FEY_DUST.get())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FEY_ALTAR.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" _ ")
                .define('#', Items.SMOOTH_STONE.asItem())
                .define('S', ModItems.FEY_GEM.get())
                .define('_', Items.SMOOTH_STONE_SLAB.asItem())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FEY_INK_BOTTLE.get(), 1)
                .requires(ModItems.MANDRAKE.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.INK_SAC)
                .unlockedBy(getHasName(ModItems.MANDRAKE.get()), has(ModItems.MANDRAKE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.EMPTY_SUMMONING_SCROLL.get(), 1)
                .requires(ModItems.FEY_INK_BOTTLE.get())
                .requires(Items.FEATHER)
                .requires(Items.PAPER)
                .unlockedBy(getHasName(ModItems.FEY_INK_BOTTLE.get()), has(ModItems.FEY_INK_BOTTLE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FEYWILD_LEXICON.get(), 1)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.BOOK)
                .unlockedBy(getHasName(ModItems.FEY_DUST.get()), has(ModItems.FEY_DUST.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MANDRAKE_ROOT.get(), 1)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.POISONOUS_POTATO)
                .unlockedBy(getHasName(ModItems.FEY_DUST.get()), has(ModItems.FEY_DUST.get()))
                .save(recipeOutput);
    }

    protected static void oreSmelting(@NotNull RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult,
                                      float pExperience, int pCookingTIme, @NotNull String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult,
                                      float pExperience, int pCookingTime, @NotNull String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(@NotNull RecipeOutput pRecipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.@NotNull Factory<T> factory,
                                                                       List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, Feywild.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}