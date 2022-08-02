package com.feywild.feywild.recipes;

import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "RECIPE_TYPE_REGISTRY")
public class ModRecipeTypes {

    public static final FeywildRecipeType<IAltarRecipe, AltarRecipe> altar = new FeywildRecipeType<>(new AltarRecipe.Serializer());
    public static final FeywildRecipeType<IDwarvenAnvilRecipe, DwarvenAnvilRecipe> dwarvenAnvil = new FeywildRecipeType<>(new DwarvenAnvilRecipe.Serializer());
}
