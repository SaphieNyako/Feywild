package com.saphienyako.feywild.datagen;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    private static final TagKey<Item> MUSIC_DISCS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "music_discs"));

    private static final TagKey<Item> SEEDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "seeds"));

    private static final TagKey<Item> MUSHROOMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "mushrooms"));
    protected final RecipeOutput recipeOutput;
    private final HolderLookup.Provider lookupProvider;

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.recipeOutput = output;
        this.lookupProvider = registries;
    }

    @Override
    protected void buildRecipes() {
        addBasicRecipes();

        addFeyAltarRecipes();

        addDefaultElvenQuartzRecipes();
        addSpringElvenQuartzRecipes();
        addSummerElvenQuartzRecipes();
        addAutumnElvenQuartzRecipes();
        addWinterElvenQuartzRecipes();

        addSpringTreeRecipes();
        addSummerTreeRecipes();
        addAutumnTreeRecipes();
        addWinterTreeRecipes();
    }


    private void addBasicRecipes() {
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
    }

    private void addFeyAltarRecipes() {
        addPixieWingTiaraRecipes();
        addTreeSaplingRecipes();
        addFlowerSeedRecipes();
        addSummoningScrollRecipes();
        addMiscFeyAltarRecipes();
    }

    private void addPixieWingTiaraRecipes() {
        feyAltarRecipe("light_wing_tiara")
                .ingredient(Items.ELYTRA)
                .ingredient(ModItems.PIXIE_WING_TIARA.get())
                .ingredient(Items.NETHER_STAR)
                .ingredient(Items.BLAZE_ROD)
                .ingredient(ModItems.FEY_DUST.get())
                .result(ModItems.LIGHT_WING_TIARA.get())
                .save();

        feyAltarRecipe("shadow_wing_tiara")
                .ingredient(Items.ELYTRA)
                .ingredient(ModItems.PIXIE_WING_TIARA.get())
                .ingredient(Items.NETHER_STAR)
                .ingredient(Items.ECHO_SHARD)
                .ingredient(ModItems.FEY_DUST.get())
                .result(ModItems.SHADOW_WING_TIARA.get())
                .save();

        feyAltarRecipe("spring_pixie_wing_tiara")
                .ingredient(Items.ELYTRA)
                .ingredient(ModItems.PIXIE_WING_TIARA.get())
                .ingredient(Items.NETHER_STAR)
                .ingredient(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get())
                .ingredient(ModItems.FEY_DUST.get())
                .result(ModItems.SPRING_PIXIE_WING_TIARA.get())
                .save();

        feyAltarRecipe("summer_pixie_wing_tiara")
                .ingredient(Items.ELYTRA)
                .ingredient(ModItems.PIXIE_WING_TIARA.get())
                .ingredient(Items.NETHER_STAR)
                .ingredient(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get())
                .ingredient(ModItems.FEY_DUST.get())
                .result(ModItems.SUMMER_PIXIE_WING_TIARA.get())
                .save();

        feyAltarRecipe("autumn_pixie_wing_tiara")
                .ingredient(Items.ELYTRA)
                .ingredient(ModItems.PIXIE_WING_TIARA.get())
                .ingredient(Items.NETHER_STAR)
                .ingredient(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get())
                .ingredient(ModItems.FEY_DUST.get())
                .result(ModItems.AUTUMN_PIXIE_WING_TIARA.get())
                .save();

        feyAltarRecipe("winter_pixie_wing_tiara")
                .ingredient(Items.ELYTRA)
                .ingredient(ModItems.PIXIE_WING_TIARA.get())
                .ingredient(Items.NETHER_STAR)
                .ingredient(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get())
                .ingredient(ModItems.FEY_DUST.get())
                .result(ModItems.WINTER_PIXIE_WING_TIARA.get())
                .save();
    }

    private void addTreeSaplingRecipes() {
        feyAltarRecipe("spring_tree_sapling")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(Items.OAK_SAPLING)
                .ingredient(Items.OAK_SAPLING)
                .ingredient(Items.OAK_SAPLING)
                .ingredient(Items.OAK_SAPLING)
                .result(ModBlocks.SPRING_TREE_SAPLING.get())
                .save();

        feyAltarRecipe("summer_tree_sapling")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(Items.ACACIA_SAPLING)
                .ingredient(Items.ACACIA_SAPLING)
                .ingredient(Items.ACACIA_SAPLING)
                .ingredient(Items.ACACIA_SAPLING)
                .result(ModBlocks.SUMMER_TREE_SAPLING.get())
                .save();

        feyAltarRecipe("autumn_tree_sapling")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(Items.DARK_OAK_SAPLING)
                .ingredient(Items.DARK_OAK_SAPLING)
                .ingredient(Items.DARK_OAK_SAPLING)
                .ingredient(Items.DARK_OAK_SAPLING)
                .result(ModBlocks.AUTUMN_TREE_SAPLING.get())
                .save();

        feyAltarRecipe("winter_tree_sapling")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(Items.SPRUCE_SAPLING)
                .ingredient(Items.SPRUCE_SAPLING)
                .ingredient(Items.SPRUCE_SAPLING)
                .ingredient(Items.SPRUCE_SAPLING)
                .result(ModBlocks.WINTER_TREE_SAPLING.get())
                .save();
    }

    private void addFlowerSeedRecipes() {
        feyAltarRecipe("giant_crocus_flower_seed")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(SEEDS)
                .ingredient(Items.CORNFLOWER)
                .ingredient(SEEDS)
                .ingredient(Items.CORNFLOWER)
                .result(ModItems.GIANT_CROCUS_FLOWER_SEED.get())
                .save();

        feyAltarRecipe("giant_dandelion_flower_seed")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(SEEDS)
                .ingredient(Items.DANDELION)
                .ingredient(SEEDS)
                .ingredient(Items.DANDELION)
                .result(ModItems.GIANT_DANDELION_FLOWER_SEED.get())
                .save();

        feyAltarRecipe("giant_sun_flower_seed")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(SEEDS)
                .ingredient(Items.SUNFLOWER)
                .ingredient(SEEDS)
                .ingredient(Items.SUNFLOWER)
                .result(ModItems.GIANT_SUN_FLOWER_SEED.get())
                .save();
    }

    private void addSummoningScrollRecipes() {

        feyAltarRecipe("summoning_scroll_spring_pixie")
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ItemTags.FLOWERS)
                .ingredient(Items.CAKE)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ItemTags.WOOL)
                .result(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get())
                .save();

        feyAltarRecipe("summoning_scroll_summer_pixie")
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(Items.HONEYCOMB)
                .ingredient(Items.GOLDEN_SWORD)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(Items.MELON)
                .result(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get())
                .save();

        feyAltarRecipe("summoning_scroll_autumn_pixie")
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(MUSHROOMS)
                .ingredient(ItemTags.HOES)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(Items.PUMPKIN)
                .result(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get())
                .save();

        feyAltarRecipe("summoning_scroll_winter_pixie")
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(Items.SNOWBALL)
                .ingredient(Items.ROTTEN_FLESH)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(Items.BONE)
                .result(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get())
                .save();

        feyAltarRecipe("summoning_scroll_mandragora")
                .ingredient(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(Items.FLOWERING_AZALEA)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModItems.MANDRAKE.get())
                .result(ModItems.SUMMONING_SCROLL_MANDRAGORA.get())
                .save();

        feyAltarRecipe("summoning_scroll_bee_knight")
                .ingredient(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(Items.SADDLE)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(Items.BEE_NEST)
                .result(ModItems.SUMMONING_SCROLL_BEE_KNIGHT.get())
                .save();

        feyAltarRecipe("summoning_scroll_shroomling")
                .ingredient(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(Items.RED_MUSHROOM_BLOCK)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(Items.SHROOMLIGHT)
                .result(ModItems.SUMMONING_SCROLL_SHROOMLING.get())
                .save();

        feyAltarRecipe("summoning_scroll_bellsnickel")
                .ingredient(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(Items.SOUL_LANTERN)
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(Items.ENCHANTED_BOOK)
                .result(ModItems.SUMMONING_SCROLL_BELLSNICKEL.get())
                .save();


        feyAltarRecipe("summoning_scroll_spring_tree_ent")
                .ingredient(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_MANDRAGORA.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModBlocks.SPRING_TREE_SAPLING.get())
                .result(ModItems.SUMMONING_SCROLL_SPRING_TREE_ENT.get())
                .save();

        feyAltarRecipe("summoning_scroll_summer_tree_ent")
                .ingredient(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_BEE_KNIGHT.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModBlocks.SUMMER_TREE_SAPLING.get())
                .result(ModItems.SUMMONING_SCROLL_SUMMER_TREE_ENT.get())
                .save();

        feyAltarRecipe("summoning_scroll_autumn_tree_ent")
                .ingredient(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_SHROOMLING.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModBlocks.AUTUMN_TREE_SAPLING.get())
                .result(ModItems.SUMMONING_SCROLL_AUTUMN_TREE_ENT.get())
                .save();

        feyAltarRecipe("summoning_scroll_winter_tree_ent")
                .ingredient(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_BELLSNICKEL.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModBlocks.WINTER_TREE_SAPLING.get())
                .result(ModItems.SUMMONING_SCROLL_WINTER_TREE_ENT.get())
                .save();

        //COURT RULERS

        feyAltarRecipe("summoning_scroll_oberon")
                .ingredient(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_MANDRAGORA.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModItems.SUMMONING_SCROLL_SPRING_TREE_ENT.get())
                .result(ModItems.SUMMONING_SCROLL_OBERON.get())
                .save();

        feyAltarRecipe("summoning_scroll_queen_titania")
                .ingredient(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_BEE_KNIGHT.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModItems.SUMMONING_SCROLL_SUMMER_TREE_ENT.get())
                .result(ModItems.SUMMONING_SCROLL_QUEEN_TITANIA.get())
                .save();

        feyAltarRecipe("summoning_scroll_ashen_lord")
                .ingredient(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_SHROOMLING.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModItems.SUMMONING_SCROLL_AUTUMN_TREE_ENT.get())
                .result(ModItems.SUMMONING_SCROLL_ASHEN_LORD.get())
                .save();

        feyAltarRecipe("summoning_scroll_queen_mab")
                .ingredient(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.SUMMONING_SCROLL_BELLSNICKEL.get())
                .ingredient(ModItems.EMPTY_SUMMONING_SCROLL.get())
                .ingredient(ModItems.SUMMONING_SCROLL_WINTER_TREE_ENT.get())
                .result(ModItems.SUMMONING_SCROLL_QUEEN_MAB.get())
                .save();
    }

    private void addMiscFeyAltarRecipes() {
        feyAltarRecipe("pixie_orb")
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(ModItems.FEY_GEM.get())
                .ingredient(Items.ENDER_PEARL)
                .ingredient(Items.ENDER_PEARL)
                .result(ModItems.PIXIE_ORB.get())
                .save();

        feyAltarRecipe("feywild_music_disc")
                .ingredient(MUSIC_DISCS)
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(ModItems.FEY_DUST.get())
                .ingredient(ModItems.FEY_DUST.get())
                .result(ModItems.FEYWILD_MUSIC_DISC.get())
                .save();
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

    protected FeyAltarRecipeBuilder feyAltarRecipe(String name) {
        return new FeyAltarRecipeBuilder(name, recipeOutput, lookupProvider);
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