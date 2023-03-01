package com.feywild.feywild.data.recipe;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.datagen.provider.recipe.DefaultExtension;
import org.moddingx.libx.datagen.provider.recipe.RecipeProviderBase;
import org.moddingx.libx.datagen.provider.recipe.SmeltingExtension;
import org.moddingx.libx.datagen.provider.recipe.StoneCuttingExtension;
import org.moddingx.libx.datagen.provider.recipe.crafting.CraftingExtension;
import org.moddingx.libx.mod.ModX;

@Datagen
public class RecipeProvider extends RecipeProviderBase implements CraftingExtension, SmeltingExtension,
        DefaultExtension, AltarExtension, AnvilExtension, StoneCuttingExtension, VariantExtension {

    public RecipeProvider(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void setup() {
        shaped(ModTrees.springTree.getWoodBlock(), 3, "aa", "aa", 'a', ModTrees.springTree.getLogBlock());
        shaped(ModTrees.summerTree.getWoodBlock(), 3, "aa", "aa", 'a', ModTrees.summerTree.getLogBlock());
        shaped(ModTrees.autumnTree.getWoodBlock(), 3, "aa", "aa", 'a', ModTrees.autumnTree.getLogBlock());
        shaped(ModTrees.winterTree.getWoodBlock(), 3, "aa", "aa", 'a', ModTrees.winterTree.getLogBlock());
        shaped(ModTrees.blossomTree.getWoodBlock(), 3, "aa", "aa", 'a', ModTrees.blossomTree.getLogBlock());
        shaped(ModTrees.hexenTree.getWoodBlock(), 3, "aa", "aa", 'a', ModTrees.hexenTree.getLogBlock());

        shapeless(ModTrees.autumnTree.getPlankBlock(), 4, ModItemTags.AUTUMN_LOGS);
        shapeless(ModTrees.springTree.getPlankBlock(), 4, ModItemTags.SPRING_LOGS);
        shapeless(ModTrees.summerTree.getPlankBlock(), 4, ModItemTags.SUMMER_LOGS);
        shapeless(ModTrees.winterTree.getPlankBlock(), 4, ModItemTags.WINTER_LOGS);
        shapeless(ModTrees.blossomTree.getPlankBlock(), 4, ModItemTags.BLOSSOM_LOGS);
        shapeless(ModTrees.hexenTree.getPlankBlock(), 4, ModItemTags.HEXEN_LOGS);

        shaped(ModBlocks.dwarvenAnvil, "fff", " i ", "iii", 'f', ModItems.lesserFeyGem, 'i', Tags.Items.STORAGE_BLOCKS_IRON);
        shapeless(ModItems.feyInkBottle, ModItems.feyDust, Items.INK_SAC, Items.GLASS_BOTTLE, ModItems.mandrake);
        shapeless(ModItems.mandrakePotion, Items.GLASS_BOTTLE, ModItems.mandrake, Items.GHAST_TEAR, ModItems.brilliantFeyGem);
        shapeless(ModItems.summoningScroll, Items.PAPER, ModItems.feyInkBottle, Items.FEATHER);
        shapeless(ModBlocks.feyMushroom, Tags.Items.MUSHROOMS, ModItems.feyDust);

        shapeless(ModBlocks.elvenQuartz, 4, ModItems.rawElvenQuartz, ModItems.rawElvenQuartz, ModItems.rawElvenQuartz, ModItems.rawElvenQuartz);
        shapeless(ModBlocks.elvenSpringQuartz, 4, ModItems.rawSpringElvenQuartz, ModItems.rawSpringElvenQuartz, ModItems.rawSpringElvenQuartz, ModItems.rawSpringElvenQuartz);
        shapeless(ModBlocks.elvenSummerQuartz, 4, ModItems.rawSummerElvenQuartz, ModItems.rawSummerElvenQuartz, ModItems.rawSummerElvenQuartz, ModItems.rawSummerElvenQuartz);
        shapeless(ModBlocks.elvenWinterQuartz, 4, ModItems.rawWinterElvenQuartz, ModItems.rawWinterElvenQuartz, ModItems.rawWinterElvenQuartz, ModItems.rawWinterElvenQuartz);
        shapeless(ModBlocks.elvenAutumnQuartz, 4, ModItems.rawAutumnElvenQuartz, ModItems.rawAutumnElvenQuartz, ModItems.rawAutumnElvenQuartz, ModItems.rawAutumnElvenQuartz);

        shaped(ModItems.magicalHoneyCookie, " a ", "wbw", " a ", 'a', ModItems.honeycomb, 'b', Items.COCOA_BEANS, 'w', Items.WHEAT);
        shapeless(ModItems.feywildLexicon, Items.BOOK, ModItems.feyDust);
        shaped(ModBlocks.feyAltar, "fpf", "pdp", "ggg", 'f', ModItems.feyDust, 'p', Tags.Items.STONE, 'd', ModItems.brilliantFeyGem, 'g', Tags.Items.INGOTS_IRON);
        shaped(ModBlocks.magicalBrazier, "lbl", "xsx", "gxg", 'l', ModItems.lesserFeyGem, 'b', Items.BOOK, 'x', Items.IRON_INGOT, 's', ModItems.shinyFeyGem, 'g', Items.GOLD_NUGGET);

        smelting(ModItems.lesserFeyGem, ModItems.feyDust, 0.1f, 100);

        stoneCutting(ModItems.greaterFeyGem, ModItems.lesserFeyGem, 2);
        stoneCutting(ModItems.shinyFeyGem, ModItems.greaterFeyGem, 2);
        stoneCutting(ModItems.brilliantFeyGem, ModItems.shinyFeyGem, 2);

        this.altar(ModTrees.springTree.getSapling().asItem())
                .requires(Items.OAK_SAPLING)
                .requires(Items.OAK_SAPLING)
                .requires(Items.OAK_SAPLING)
                .requires(Items.OAK_SAPLING)
                .requires(ModItems.feyDust)
                .build();

        this.altar(ModTrees.summerTree.getSapling().asItem())
                .requires(Items.BIRCH_SAPLING)
                .requires(Items.BIRCH_SAPLING)
                .requires(Items.BIRCH_SAPLING)
                .requires(Items.BIRCH_SAPLING)
                .requires(ModItems.feyDust)
                .build();

        this.altar(ModTrees.winterTree.getSapling().asItem())
                .requires(Items.SPRUCE_SAPLING)
                .requires(Items.SPRUCE_SAPLING)
                .requires(Items.SPRUCE_SAPLING)
                .requires(Items.SPRUCE_SAPLING)
                .requires(ModItems.feyDust)
                .build();

        this.altar(ModTrees.autumnTree.getSapling().asItem())
                .requires(Items.DARK_OAK_SAPLING)
                .requires(Items.DARK_OAK_SAPLING)
                .requires(Items.DARK_OAK_SAPLING)
                .requires(Items.DARK_OAK_SAPLING)
                .requires(ModItems.feyDust)
                .build();

        this.altar(ModItems.feywildMusicDisc)
                .requires(ItemTags.MUSIC_DISCS)
                .requires(ModTrees.springTree.getSapling())
                .requires(ModTrees.summerTree.getSapling())
                .requires(ModTrees.autumnTree.getSapling())
                .requires(ModTrees.winterTree.getSapling())
                .build();

        this.altar(ModItems.summoningScrollSpringPixie)
                .requires(ModTrees.springTree.getSapling())
                .requires(Blocks.OXEYE_DAISY)
                .requires(Items.WHEAT_SEEDS)
                .requires(Items.EGG)
                .requires(ModItems.summoningScroll)
                .build();

        this.altar(ModItems.summoningScrollSummerPixie)
                .requires(ModTrees.summerTree.getSapling())
                .requires(Blocks.SUNFLOWER)
                .requires(Items.HONEYCOMB)
                .requires(Items.GOLDEN_SWORD)
                .requires(ModItems.summoningScroll)
                .build();

        this.altar(ModItems.summoningScrollAutumnPixie)
                .requires(ModTrees.autumnTree.getSapling())
                .requires(Blocks.CARVED_PUMPKIN)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.IRON_HOE)
                .requires(ModItems.summoningScroll)
                .build();

        this.altar(ModItems.summoningScrollWinterPixie)
                .requires(ModTrees.winterTree.getSapling())
                .requires(Items.SNOWBALL)
                .requires(Blocks.ICE)
                .requires(Items.ROTTEN_FLESH)
                .requires(ModItems.summoningScroll)
                .build();

        this.altar(ModItems.summoningScrollBeeKnight)
                .requires(ModItems.summoningScrollSummerPixie)
                .requires(ModItems.honeycomb)
                .requires(Blocks.BEE_NEST)
                .requires(Items.LEAD)
                .requires(ModItems.summoningScroll)
                .build();

        this.altar(ModItems.summoningScrollMandragora)
                .requires(ModItems.magicalHoneyCookie)
                .requires(ModItems.mandrake)
                .requires(Blocks.BIG_DRIPLEAF)
                .requires(Blocks.NOTE_BLOCK)
                .requires(ModItems.summoningScrollSpringPixie)
                .build();

        this.altar(ModItems.summoningScrollShroomling)
                .requires(Blocks.RED_MUSHROOM_BLOCK)
                .requires(Blocks.MUSHROOM_STEM)
                .requires(ModItems.greaterFeyGem)
                .requires(Blocks.COMPOSTER)
                .requires(ModItems.summoningScrollAutumnPixie)
                .build();

        this.altar(ModItems.reaperScythe)
                .requires(ModTrees.winterTree.getWoodBlock())
                .requires(ModItems.feyDust)
                .requires(Items.DIAMOND_HOE)
                .requires(Items.PACKED_ICE)
                .requires(Items.SPORE_BLOSSOM)
                .build();

        this.gemTransmutation(ModItems.lesserFeyGem, ModItems.greaterFeyGem, 50);
        this.gemTransmutation(ModItems.greaterFeyGem, ModItems.shinyFeyGem, 100);
        this.gemTransmutation(ModItems.shinyFeyGem, ModItems.brilliantFeyGem, 150);

        this.quartzRecipes(ModBlocks.elvenQuartz);
        this.quartzRecipes(ModBlocks.elvenSpringQuartz);
        this.quartzRecipes(ModBlocks.elvenSummerQuartz);
        this.quartzRecipes(ModBlocks.elvenAutumnQuartz);
        this.quartzRecipes(ModBlocks.elvenWinterQuartz);
    }
}
