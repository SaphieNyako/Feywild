package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    protected final RecipeOutput recipeOutput;

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.recipeOutput = output;
    }

    @Override
    protected void buildRecipes() {


        List<ItemLike> FEY_GEM_SMELTABLES = List.of(ModItems.FEY_GEM.get());

        oreSmelting(FEY_GEM_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.FEY_DUST.get(), 0.1f, 100, "fey_dust");

        shaped(RecipeCategory.MISC, ModItems.FEY_GEM.get())
                .pattern("## ")
                .pattern("## ")
                .pattern("   ")
                .define('#', ModItems.FEY_DUST.get())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(recipeOutput);

        shaped(RecipeCategory.MISC, ModBlocks.FEY_ALTAR_QUEEN_TITANIA.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" _ ")
                .define('#', Items.SMOOTH_STONE.asItem())
                .define('S', ModItems.FEY_GEM.get())
                .define('_', Items.SMOOTH_STONE_SLAB.asItem())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(recipeOutput);

        shaped(RecipeCategory.MISC, ModBlocks.FEY_ALTAR_QUEEN_MAB.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" _ ")
                .define('#', Items.SMOOTH_STONE.asItem())
                .define('S', ModItems.FEY_GEM.get())
                .define('_', Items.SMOOTH_STONE_SLAB.asItem())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(recipeOutput);

        shaped(RecipeCategory.MISC, ModBlocks.FEY_ALTAR_ASHEN_LORD.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" _ ")
                .define('#', Items.SMOOTH_STONE.asItem())
                .define('S', ModItems.FEY_GEM.get())
                .define('_', Items.SMOOTH_STONE_SLAB.asItem())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(recipeOutput);

        shaped(RecipeCategory.MISC, ModBlocks.FEY_ALTAR_OBERON.get())
                .pattern(" # ")
                .pattern("#S#")
                .pattern(" _ ")
                .define('#', Items.SMOOTH_STONE.asItem())
                .define('S', ModItems.FEY_GEM.get())
                .define('_', Items.SMOOTH_STONE_SLAB.asItem())
                .unlockedBy(getHasName(ModItems.FEY_GEM.get()), has(ModItems.FEY_GEM.get()))
                .save(recipeOutput);

        shapeless(RecipeCategory.MISC, ModItems.FEY_INK_BOTTLE.get(), 1)
                .requires(ModItems.MANDRAKE.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.INK_SAC)
                .unlockedBy(getHasName(ModItems.MANDRAKE.get()), has(ModItems.MANDRAKE.get()))
                .save(recipeOutput);

        shapeless(RecipeCategory.MISC, ModItems.EMPTY_SUMMONING_SCROLL.get(), 1)
                .requires(ModItems.FEY_INK_BOTTLE.get())
                .requires(Items.FEATHER)
                .requires(Items.PAPER)
                .unlockedBy(getHasName(ModItems.FEY_INK_BOTTLE.get()), has(ModItems.FEY_INK_BOTTLE.get()))
                .save(recipeOutput);

        shapeless(RecipeCategory.MISC, ModItems.FEYWILD_LEXICON.get(), 1)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.BOOK)
                .unlockedBy(getHasName(ModItems.FEY_DUST.get()), has(ModItems.FEY_DUST.get()))
                .save(recipeOutput);

        shapeless(RecipeCategory.MISC, ModItems.MANDRAKE_ROOT.get(), 1)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.POISONOUS_POTATO)
                .unlockedBy(getHasName(ModItems.FEY_DUST.get()), has(ModItems.FEY_DUST.get()))
                .save(recipeOutput);

        shapeless(RecipeCategory.MISC, Items.MUSHROOM_STEW, 1)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.BOWL)
                .unlockedBy(getHasName(ModItems.SUMMONING_SCROLL_SHROOMLING.get()), has(ModItems.SUMMONING_SCROLL_SHROOMLING.get()))
                .save(recipeOutput);

        addDefaultElvenQuartzRecipes();
        addSpringElvenQuartzRecipes();
        addSummerElvenQuartzRecipes();
        addWinterElvenQuartzRecipes();
        addAutumnElvenQuartzRecipes();

        addAutumnTreeRecipes();
        addSpringTreeRecipes();
        addSummerTreeRecipes();
        addWinterTreeRecipes();
    }

    protected void addDefaultElvenQuartzRecipes() {

        // ELVEN QUARTZ BLOCK
        shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_BLOCK.get(), 4)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.QUARTZ)
                .requires(ModItems.FEY_DUST.get())
                .requires(Items.QUARTZ)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stairBuilder(ModBlocks.ELVEN_QUARTZ_STAIRS.get(),
                Ingredient.of(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get(), 2);

        // BRICK
        shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.ELVEN_QUARTZ_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK.get(), 2);

        // MOSSY
        shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BRICK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BRICK.get()))
                .save(output);

        // CRACKED
        smeltingResultFromBase(
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_CRACKED_BRICK.get(), 2);

        // PILLAR
        shapeless(RecipeCategory.MISC, ModBlocks.ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        // POLISHED
        smeltingResultFromBase(
                ModBlocks.ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                Ingredient.of(ModBlocks.ELVEN_QUARTZ_POLISHED.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.ELVEN_QUARTZ_POLISHED.get(), 2);
    }

    protected void addSpringElvenQuartzRecipes() {

        // SPRING ELVEN QUARTZ BLOCK
        shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(Items.GREEN_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get(),
                Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get(), 2);

        // BRICK
        shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get(), 2);

        // MOSSY
        shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()),
                        has(ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get()))
                .save(output);

        // CRACKED
        smeltingResultFromBase(
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_CRACKED_BRICK.get(), 2);

        // PILLAR
        shapeless(RecipeCategory.MISC, ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        // POLISHED
        smeltingResultFromBase(
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                Ingredient.of(ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SPRING_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.SPRING_ELVEN_QUARTZ_POLISHED.get(), 2);
    }

    protected void addSummerElvenQuartzRecipes() {

        // SUMMER ELVEN QUARTZ BLOCK
        shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(Items.YELLOW_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get(),
                Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get(), 2);

        // BRICK
        shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get(), 2);

        // MOSSY
        shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()),
                        has(ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get()))
                .save(output);

        // CRACKED
        smeltingResultFromBase(
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_CRACKED_BRICK.get(), 2);

        // PILLAR
        shapeless(RecipeCategory.MISC, ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        // POLISHED
        smeltingResultFromBase(
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                Ingredient.of(ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.SUMMER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.SUMMER_ELVEN_QUARTZ_POLISHED.get(), 2);
    }

    protected void addWinterElvenQuartzRecipes() {

        // WINTER ELVEN QUARTZ BLOCK
        shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(Items.BLUE_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get(),
                Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get(), 2);

        // BRICK
        shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get(), 2);

        // MOSSY
        shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()),
                        has(ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get()))
                .save(output);

        // CRACKED
        smeltingResultFromBase(
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_CRACKED_BRICK.get(), 2);

        // PILLAR
        shapeless(RecipeCategory.MISC, ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        // POLISHED
        smeltingResultFromBase(
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                Ingredient.of(ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.WINTER_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.WINTER_ELVEN_QUARTZ_POLISHED.get(), 2);
    }

    protected void addAutumnElvenQuartzRecipes() {

        // AUTUMN ELVEN QUARTZ BLOCK
        shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get(), 8)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(Items.RED_DYE)
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get(),
                Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get(), 2);

        // BRICK
        shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(), 4)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get(), 2);

        // MOSSY
        shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get(), 1)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()),
                        has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get()))
                .save(output);

        // CRACKED
        smeltingResultFromBase(
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_CRACKED_BRICK.get(), 2);

        // PILLAR
        shapeless(RecipeCategory.MISC, ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(), 2)
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .requires(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()),
                        has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_PILLAR.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        // POLISHED
        smeltingResultFromBase(
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get());

        stairBuilder(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                Ingredient.of(ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get()))
                .group("elven_quartz")
                .unlockedBy("has_elven_quartz_block",
                        has(ModBlocks.AUTUMN_ELVEN_QUARTZ_BLOCK.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_STAIRS.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED_SLAB.get(),
                ModBlocks.AUTUMN_ELVEN_QUARTZ_POLISHED.get(), 2);
    }

    protected void addAutumnTreeRecipes() {

        // WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_TREE_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.AUTUMN_TREE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.AUTUMN_TREE_LOG.get()),
                        has(ModBlocks.AUTUMN_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_TREE_WOOD_SLAB.get(),
                ModBlocks.AUTUMN_TREE_WOOD.get());

        stairBuilder(ModBlocks.AUTUMN_TREE_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_WOOD.get()))
                .unlockedBy("has_autumn_wood",
                        has(ModBlocks.AUTUMN_TREE_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_TREE_WOOD_WALL.get(),
                ModBlocks.AUTUMN_TREE_WOOD.get());

        // STRIPPED WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()),
                        has(ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_SLAB.get(),
                ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get());

        stairBuilder(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get()))
                .unlockedBy("has_autumn_stripped_wood",
                        has(ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_TREE_STRIPPED_WOOD_WALL.get(),
                ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get());

        // PLANKS
        shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AUTUMN_TREE_PLANKS.get(), 4)
                .requires(ModTags.Items.AUTUMN_LOGS)
                .unlockedBy(getHasName(ModBlocks.AUTUMN_TREE_LOG.get()),
                        has(ModBlocks.AUTUMN_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AUTUMN_TREE_PLANKS_SLAB.get(),
                ModBlocks.AUTUMN_TREE_PLANKS.get());

        stairBuilder(ModBlocks.AUTUMN_TREE_PLANKS_STAIRS.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .unlockedBy("has_autumn_planks",
                        has(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .save(output);

        fenceBuilder(ModBlocks.AUTUMN_TREE_PLANKS_FENCE.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .unlockedBy("has_autumn_planks",
                        has(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .save(output);

        fenceGateBuilder(ModBlocks.AUTUMN_TREE_PLANKS_FENCE_GATE.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .unlockedBy("has_autumn_planks",
                        has(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .save(output);

        doorBuilder(ModBlocks.AUTUMN_TREE_PLANKS_DOOR.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .unlockedBy("has_autumn_planks",
                        has(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .save(output);

        trapdoorBuilder(ModBlocks.AUTUMN_TREE_PLANKS_TRAPDOOR.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .unlockedBy("has_autumn_planks",
                        has(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .save(output);

        buttonBuilder(ModBlocks.AUTUMN_TREE_PLANKS_BUTTON.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .unlockedBy("has_autumn_planks",
                        has(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .save(output);

        pressurePlateBuilder(RecipeCategory.REDSTONE,
                ModBlocks.AUTUMN_TREE_PLANKS_PRESSURE_PLATE.get(),
                Ingredient.of(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .unlockedBy("has_autumn_planks",
                        has(ModBlocks.AUTUMN_TREE_PLANKS.get()))
                .save(output);
    }


    protected void addSummerTreeRecipes() {

        // WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_TREE_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SUMMER_TREE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.SUMMER_TREE_LOG.get()),
                        has(ModBlocks.SUMMER_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_TREE_WOOD_SLAB.get(),
                ModBlocks.SUMMER_TREE_WOOD.get());

        stairBuilder(ModBlocks.SUMMER_TREE_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_WOOD.get()))
                .unlockedBy("has_summer_wood",
                        has(ModBlocks.SUMMER_TREE_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_TREE_WOOD_WALL.get(),
                ModBlocks.SUMMER_TREE_WOOD.get());

        // STRIPPED WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SUMMER_TREE_STRIPPED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()),
                        has(ModBlocks.SUMMER_TREE_STRIPPED_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_TREE_STRIPPED_WOOD_SLAB.get(),
                ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get());

        stairBuilder(ModBlocks.SUMMER_TREE_STRIPPED_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get()))
                .unlockedBy("has_summer_stripped_wood",
                        has(ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_TREE_STRIPPED_WOOD_WALL.get(),
                ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get());

        // PLANKS
        shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUMMER_TREE_PLANKS.get(), 4)
                .requires(ModTags.Items.SUMMER_LOGS)
                .unlockedBy(getHasName(ModBlocks.SUMMER_TREE_LOG.get()),
                        has(ModBlocks.SUMMER_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SUMMER_TREE_PLANKS_SLAB.get(),
                ModBlocks.SUMMER_TREE_PLANKS.get());

        stairBuilder(ModBlocks.SUMMER_TREE_PLANKS_STAIRS.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .unlockedBy("has_summer_planks",
                        has(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .save(output);

        fenceBuilder(ModBlocks.SUMMER_TREE_PLANKS_FENCE.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .unlockedBy("has_summer_planks",
                        has(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .save(output);

        fenceGateBuilder(ModBlocks.SUMMER_TREE_PLANKS_FENCE_GATE.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .unlockedBy("has_summer_planks",
                        has(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .save(output);

        doorBuilder(ModBlocks.SUMMER_TREE_PLANKS_DOOR.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .unlockedBy("has_summer_planks",
                        has(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .save(output);

        trapdoorBuilder(ModBlocks.SUMMER_TREE_PLANKS_TRAPDOOR.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .unlockedBy("has_summer_planks",
                        has(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .save(output);

        buttonBuilder(ModBlocks.SUMMER_TREE_PLANKS_BUTTON.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .unlockedBy("has_summer_planks",
                        has(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .save(output);

        pressurePlateBuilder(RecipeCategory.REDSTONE,
                ModBlocks.SUMMER_TREE_PLANKS_PRESSURE_PLATE.get(),
                Ingredient.of(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .unlockedBy("has_summer_planks",
                        has(ModBlocks.SUMMER_TREE_PLANKS.get()))
                .save(output);
    }

    protected void addSpringTreeRecipes() {

        // WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_TREE_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SPRING_TREE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.SPRING_TREE_LOG.get()),
                        has(ModBlocks.SPRING_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_TREE_WOOD_SLAB.get(),
                ModBlocks.SPRING_TREE_WOOD.get());

        stairBuilder(ModBlocks.SPRING_TREE_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_WOOD.get()))
                .unlockedBy("has_spring_wood",
                        has(ModBlocks.SPRING_TREE_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_TREE_WOOD_WALL.get(),
                ModBlocks.SPRING_TREE_WOOD.get());

        // STRIPPED WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_TREE_STRIPPED_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.SPRING_TREE_STRIPPED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()),
                        has(ModBlocks.SPRING_TREE_STRIPPED_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_TREE_STRIPPED_WOOD_SLAB.get(),
                ModBlocks.SPRING_TREE_STRIPPED_WOOD.get());

        stairBuilder(ModBlocks.SPRING_TREE_STRIPPED_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_STRIPPED_WOOD.get()))
                .unlockedBy("has_spring_stripped_wood",
                        has(ModBlocks.SPRING_TREE_STRIPPED_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_TREE_STRIPPED_WOOD_WALL.get(),
                ModBlocks.SPRING_TREE_STRIPPED_WOOD.get());

        // PLANKS
        shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRING_TREE_PLANKS.get(), 4)
                .requires(ModTags.Items.SPRING_LOGS)
                .unlockedBy(getHasName(ModBlocks.SPRING_TREE_LOG.get()),
                        has(ModBlocks.SPRING_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.SPRING_TREE_PLANKS_SLAB.get(),
                ModBlocks.SPRING_TREE_PLANKS.get());

        stairBuilder(ModBlocks.SPRING_TREE_PLANKS_STAIRS.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_PLANKS.get()))
                .unlockedBy("has_spring_planks",
                        has(ModBlocks.SPRING_TREE_PLANKS.get()))
                .save(output);

        fenceBuilder(ModBlocks.SPRING_TREE_PLANKS_FENCE.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_PLANKS.get()))
                .unlockedBy("has_spring_planks",
                        has(ModBlocks.SPRING_TREE_PLANKS.get()))
                .save(output);

        fenceGateBuilder(ModBlocks.SPRING_TREE_PLANKS_FENCE_GATE.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_PLANKS.get()))
                .unlockedBy("has_spring_planks",
                        has(ModBlocks.SPRING_TREE_PLANKS.get()))
                .save(output);

        doorBuilder(ModBlocks.SPRING_TREE_PLANKS_DOOR.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_PLANKS.get()))
                .unlockedBy("has_spring_planks",
                        has(ModBlocks.SPRING_TREE_PLANKS.get()))
                .save(output);

        trapdoorBuilder(ModBlocks.SPRING_TREE_PLANKS_TRAPDOOR.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_PLANKS.get()))
                .unlockedBy("has_spring_planks",
                        has(ModBlocks.SPRING_TREE_PLANKS.get()))
                .save(output);

        buttonBuilder(ModBlocks.SPRING_TREE_PLANKS_BUTTON.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_PLANKS.get()))
                .unlockedBy("has_spring_planks",
                        has(ModBlocks.SPRING_TREE_PLANKS.get()))
                .save(output);

        pressurePlateBuilder(RecipeCategory.REDSTONE,
                ModBlocks.SPRING_TREE_PLANKS_PRESSURE_PLATE.get(),
                Ingredient.of(ModBlocks.SPRING_TREE_PLANKS.get()))
                .unlockedBy("has_spring_planks",
                        has(ModBlocks.SPRING_TREE_PLANKS.get()))
                .save(output);
    }
    protected void addWinterTreeRecipes() {

        // WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_TREE_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.WINTER_TREE_LOG.get())
                .unlockedBy(getHasName(ModBlocks.WINTER_TREE_LOG.get()),
                        has(ModBlocks.WINTER_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_TREE_WOOD_SLAB.get(),
                ModBlocks.WINTER_TREE_WOOD.get());

        stairBuilder(ModBlocks.WINTER_TREE_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_WOOD.get()))
                .unlockedBy("has_winter_wood",
                        has(ModBlocks.WINTER_TREE_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_TREE_WOOD_WALL.get(),
                ModBlocks.WINTER_TREE_WOOD.get());

        // STRIPPED WOOD
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_TREE_STRIPPED_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.WINTER_TREE_STRIPPED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()),
                        has(ModBlocks.WINTER_TREE_STRIPPED_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_TREE_STRIPPED_WOOD_SLAB.get(),
                ModBlocks.WINTER_TREE_STRIPPED_WOOD.get());

        stairBuilder(ModBlocks.WINTER_TREE_STRIPPED_WOOD_STAIRS.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_STRIPPED_WOOD.get()))
                .unlockedBy("has_winter_stripped_wood",
                        has(ModBlocks.WINTER_TREE_STRIPPED_WOOD.get()))
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_TREE_STRIPPED_WOOD_WALL.get(),
                ModBlocks.WINTER_TREE_STRIPPED_WOOD.get());

        // PLANKS
        shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINTER_TREE_PLANKS.get(), 4)
                .requires(ModTags.Items.WINTER_LOGS)
                .unlockedBy(getHasName(ModBlocks.WINTER_TREE_LOG.get()),
                        has(ModBlocks.WINTER_TREE_LOG.get()))
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.WINTER_TREE_PLANKS_SLAB.get(),
                ModBlocks.WINTER_TREE_PLANKS.get());

        stairBuilder(ModBlocks.WINTER_TREE_PLANKS_STAIRS.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_PLANKS.get()))
                .unlockedBy("has_winter_planks",
                        has(ModBlocks.WINTER_TREE_PLANKS.get()))
                .save(output);

        fenceBuilder(ModBlocks.WINTER_TREE_PLANKS_FENCE.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_PLANKS.get()))
                .unlockedBy("has_winter_planks",
                        has(ModBlocks.WINTER_TREE_PLANKS.get()))
                .save(output);

        fenceGateBuilder(ModBlocks.WINTER_TREE_PLANKS_FENCE_GATE.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_PLANKS.get()))
                .unlockedBy("has_winter_planks",
                        has(ModBlocks.WINTER_TREE_PLANKS.get()))
                .save(output);

        doorBuilder(ModBlocks.WINTER_TREE_PLANKS_DOOR.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_PLANKS.get()))
                .unlockedBy("has_winter_planks",
                        has(ModBlocks.WINTER_TREE_PLANKS.get()))
                .save(output);

        trapdoorBuilder(ModBlocks.WINTER_TREE_PLANKS_TRAPDOOR.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_PLANKS.get()))
                .unlockedBy("has_winter_planks",
                        has(ModBlocks.WINTER_TREE_PLANKS.get()))
                .save(output);

        buttonBuilder(ModBlocks.WINTER_TREE_PLANKS_BUTTON.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_PLANKS.get()))
                .unlockedBy("has_winter_planks",
                        has(ModBlocks.WINTER_TREE_PLANKS.get()))
                .save(output);

        pressurePlateBuilder(RecipeCategory.REDSTONE,
                ModBlocks.WINTER_TREE_PLANKS_PRESSURE_PLATE.get(),
                Ingredient.of(ModBlocks.WINTER_TREE_PLANKS.get()))
                .unlockedBy("has_winter_planks",
                        has(ModBlocks.WINTER_TREE_PLANKS.get()))
                .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries, @NotNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NotNull String getName() {
            return "Feywild Recipes";
        }
    }
}