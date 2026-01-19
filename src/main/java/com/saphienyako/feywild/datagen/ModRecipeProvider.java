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

        addDefaultElvenQuartzRecipes(recipeOutput);
        addSpringElvenQuartzRecipes(recipeOutput);
        addSummerElvenQuartzRecipes(recipeOutput);
        addWinterElvenQuartzRecipes(recipeOutput);
        addAutumnElvenQuartzRecipes(recipeOutput);

    }

    protected static void oreSmelting(@NotNull RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult,
                                      float pExperience, int pCookingTIme, @NotNull String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }
    @SuppressWarnings("unused")
    protected static void oreBlasting(@NotNull RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult,
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

    protected static void addDefaultElvenQuartzRecipes(@NotNull RecipeOutput recipeOutput){

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_BLOCK.get(), 4)
                .requires(ModItems.FEY_DUST)
                .requires(Items.QUARTZ)
                .requires(ModItems.FEY_DUST)
                .requires(Items.QUARTZ)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stairBuilder(ModBlocks.ELVEN_QUARTZ_STAIRS.get(), Ingredient.of(ModBlocks.ELVEN_QUARTZ_BLOCK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_SLAB.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_STAIRS.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_SLAB.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get(),2);


        //BRICK
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_BRICK.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.ELVEN_QUARTZ_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get(), ModBlocks.ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.ELVEN_QUARTZ_BRICK.get(),2);
        //MOSSY
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BRICK.get()), has(ModBlocks.ELVEN_QUARTZ_BRICK.get()))
                .save(recipeOutput);
        //CRACKED
        smeltingResultFromBase(recipeOutput, ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(),2);

        //PILLAR
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_PILLAR.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get());
        //POLISHED
        smeltingResultFromBase(recipeOutput, ModBlocks.ELVEN_QUARTZ_POLISHED.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_POLISHED.get(), ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get(), Ingredient.of(ModBlocks.ELVEN_QUARTZ_POLISHED.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get(), ModBlocks.ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.ELVEN_QUARTZ_POLISHED.get(),2);
    }

    protected static void addSpringElvenQuartzRecipes(@NotNull RecipeOutput recipeOutput){

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(Items.GREEN_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get(), Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get(),2);

        //BRICK
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(),2);
        //MOSSY
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()), has(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()))
                .save(recipeOutput);
        //CRACKED
        smeltingResultFromBase(recipeOutput, ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(),2);

        //PILLAR
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());
        //POLISHED
        smeltingResultFromBase(recipeOutput, ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get(), Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get(), ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(),2);
    }

    protected static void addSummerElvenQuartzRecipes(@NotNull RecipeOutput recipeOutput){

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(Items.YELLOW_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)

                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get(), Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get(),2);

        //BRICK
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(),2);
        //MOSSY
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()), has(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()))
                .save(recipeOutput);
        //CRACKED
        smeltingResultFromBase(recipeOutput, ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(),2);

        //PILLAR
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());
        //POLISHED
        smeltingResultFromBase(recipeOutput, ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get(), Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(),2);
    }

    protected static void addWinterElvenQuartzRecipes(@NotNull RecipeOutput recipeOutput){

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(Items.BLUE_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get(), Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get(),2);



        //BRICK
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(),2);
        //MOSSY
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()), has(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()))
                .save(recipeOutput);
        //CRACKED
        smeltingResultFromBase(recipeOutput, ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(),2);

        //PILLAR
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());
        //POLISHED
        smeltingResultFromBase(recipeOutput, ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get(), Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get(), ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(),2);
    }

    protected static void addAutumnElvenQuartzRecipes(@NotNull RecipeOutput recipeOutput){

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(Items.RED_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get(), Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get(),2);


        //BRICK
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK)
                .unlockedBy(getHasName(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(),2);
        //MOSSY
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()), has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()))
                .save(recipeOutput);
        //CRACKED
        smeltingResultFromBase(recipeOutput, ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(),2);

        //PILLAR
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()), has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());
        //POLISHED
        smeltingResultFromBase(recipeOutput, ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get(), Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get())).group("elven_quartz")
                .unlockedBy("has_elven_quartz_block", has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get(), ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(),2);
    }
}