package com.feywild.feywild.recipes;

import io.github.noeppi_noeppi.libx.annotation.NoReg;
import io.github.noeppi_noeppi.libx.annotation.RegName;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;

@RegisterClass
public class ModRecipeTypes {


    @NoReg public static final IRecipeType<IAltarRecipe> ALTAR = IRecipeType.register(IAltarRecipe.TYPE_ID.toString());
    @NoReg public static final IRecipeType<IDwarvenAnvilRecipe> DWARVEN_ANVIL = IRecipeType.register(IDwarvenAnvilRecipe.TYPE_ID.toString());

    @RegName("fey_altar") public static final AltarRecipe.Serializer ALTAR_SERIALIZER = new AltarRecipe.Serializer();
    @RegName("dwarven_anvil") public static final DwarvenAnvilRecipe.Serializer DWARVEN_ANVIL_SERIALIZER = new DwarvenAnvilRecipe.Serializer();


    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_TYPE, AltarRecipe.TYPE_ID, ALTAR);
        Registry.register(Registry.RECIPE_TYPE, DwarvenAnvilRecipe.TYPE_ID, DWARVEN_ANVIL);
    }
}
