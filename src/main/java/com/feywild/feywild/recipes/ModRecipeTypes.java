package com.feywild.feywild.recipes;

import io.github.noeppi_noeppi.libx.annotation.registration.NoReg;
import io.github.noeppi_noeppi.libx.annotation.registration.RegName;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;

@RegisterClass
public class ModRecipeTypes {

    @NoReg public static final RecipeType<IAltarRecipe> ALTAR = RecipeType.register(IAltarRecipe.TYPE_ID.toString());
    @NoReg public static final RecipeType<IDwarvenAnvilRecipe> DWARVEN_ANVIL = RecipeType.register(IDwarvenAnvilRecipe.TYPE_ID.toString());

    @RegName("fey_altar") public static final AltarRecipe.Serializer ALTAR_SERIALIZER = new AltarRecipe.Serializer();
    @RegName("dwarven_anvil") public static final DwarvenAnvilRecipe.Serializer DWARVEN_ANVIL_SERIALIZER = new DwarvenAnvilRecipe.Serializer();

    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_TYPE, AltarRecipe.TYPE_ID, ALTAR);
        Registry.register(Registry.RECIPE_TYPE, DwarvenAnvilRecipe.TYPE_ID, DWARVEN_ANVIL);
    }
}
