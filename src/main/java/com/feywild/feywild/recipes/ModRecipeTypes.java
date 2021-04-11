package com.feywild.feywild.recipes;

import com.feywild.feywild.FeywildMod;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;

public class ModRecipeTypes {

    public static final IRecipeType<IAltarRecipe> ALTAR_RECIPE = new RecipeType<>();
    public static final IRecipeSerializer<AltarRecipe> ALTAR_SERIALIZER = new AltarRecipe.Serializer();

    //Place to register recipe types
    public void register(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
        System.out.println("Hellow ow");
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(FeywildMod.MOD_ID, "fey_altar"), ALTAR_RECIPE);
        evt.getRegistry().register(ALTAR_SERIALIZER.setRegistryName(new ResourceLocation(FeywildMod.MOD_ID, "fey_altar")));
    }


    //Recipe init class
    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}
