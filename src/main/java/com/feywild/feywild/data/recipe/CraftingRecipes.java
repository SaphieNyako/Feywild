package com.feywild.feywild.data.recipe;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.FeyWoodBlock;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class CraftingRecipes extends RecipeProviderBase {

    public CraftingRecipes(ModX mod, DataGenerator generator) {
        super(mod, generator);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        //noinspection ConstantConditions
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(b -> this.mod.modid.equals(b.getRegistryName().getNamespace()))
                .filter(b -> b instanceof FeyWoodBlock)
                .forEach(b -> this.makeWoodRecipe((FeyWoodBlock) b, consumer));

        ShapedRecipeBuilder.shaped(ModBlocks.dwarvenAnvil)
                .pattern("fff")
                .pattern(" i ")
                .pattern("iii")
                .define('f', ModItems.lesserFeyGem)
                .define('i', Blocks.IRON_BLOCK)
                .unlockedBy("has_item", has(ModItems.lesserFeyGem))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.feyInkBottle)
                .requires(ModItems.feyDust)
                .requires(Items.INK_SAC)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.mandrake)
                .unlockedBy("has_item0", has(ModItems.feyDust))
                .unlockedBy("has_item1", has(ModItems.mandrake))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.feyMushroom)
                .requires(ModItems.feyDust)
                .requires(Items.RED_MUSHROOM)
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.mandrakePotion)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.mandrake)
                .requires(Items.GHAST_TEAR)
                .requires(ModItems.brilliantFeyGem)
                .unlockedBy("has_item0", has(Items.GHAST_TEAR))
                .unlockedBy("has_item1", has(ModItems.mandrake))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.summoningScroll)
                .requires(Items.PAPER)
                .requires(ModItems.feyInkBottle)
                .requires(Items.FEATHER)
                .unlockedBy("has_item", has(ModItems.feyInkBottle))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.magicalHoneyCookie, 4)
                .pattern(" a ")
                .pattern("#b#")
                .pattern(" a ")
                .define('a', ModItems.honeycomb)
                .define('b', Items.COCOA_BEANS)
                .define('#', Items.WHEAT)
                .unlockedBy("has_item", has(ModItems.honeycomb))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.feywildLexicon)
                .requires(Items.BOOK)
                .requires(ModItems.feyDust)
                .unlockedBy("has_item", has(ModItems.feyDust))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.feyAltar)
                .pattern("fpf")
                .pattern("pdp")
                .pattern("ggg")
                .define('f', ModItems.feyDust)
                .define('p', Items.IRON_INGOT)
                .define('d', ModItems.brilliantFeyGem)
                .define('g', Items.GOLD_NUGGET)
                .unlockedBy("has_item", has(ModItems.feyDust))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.magicalBrazier)
                .pattern("lbl")
                .pattern("xsx")
                .pattern("gxg")
                .define('l', ModItems.lesserFeyGem)
                .define('b', Items.BOOK)
                .define('x', Items.IRON_INGOT)
                .define('s', ModItems.shinyFeyGem)
                .define('g', Items.GOLD_NUGGET)
                .unlockedBy("has_item", has(ModItems.lesserFeyGem))
                .save(consumer);
    }

    private void makeWoodRecipe(FeyWoodBlock block, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(block, 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', block.getLogBlock())
                .group("bark")
                .unlockedBy("has_item", has(block.getLogBlock()))
                .save(consumer, this.loc(block));
    }
}
