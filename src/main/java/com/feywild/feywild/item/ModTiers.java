package com.feywild.feywild.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {

    //Iron level, higher durability, same speed and attack damage, higher enchant value equal to gold.
    public static final ForgeTier FEY_LESSER_ORE = new ForgeTier(2, 900, 6.0f,
            2.0f, 22, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(ModItems.lesserFeyGem));

    public static final ForgeTier FEY_GREATER_ORE = new ForgeTier(3, 1800, 8.0f,
            3.0f, 22, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.greaterFeyGem));

    public static final ForgeTier FEY_SHINY_ORE = new ForgeTier(4, 2700, 9.0f,
            4.0f, 22, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.greaterFeyGem));

    public static final ForgeTier FEY_BRILLIANT_ORE = new ForgeTier(5, 3600, 10.0f,
            5.0f, 22, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.greaterFeyGem));

}
