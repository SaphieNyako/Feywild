package com.feywild.feywild.events;

import com.feywild.feywild.FeywildMod;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ModRecipes{
    //Store altar recipes
    private static HashMap<List<ItemStack>,ItemStack> altar_recipes = new HashMap<>();


    public static void register() {
        addAltarRecipe(new ItemStack(Items.DIAMOND), Arrays.asList(new ItemStack(Items.STONE), ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY));
    }


    public static void addAltarRecipe(ItemStack result, List<ItemStack> items){
        if(items.size() <= 5) {
            altar_recipes.put(items, result);
        }else {
            System.out.println("Array too large! (>=5)");
        }
    }

    public static void removeAltarRecipe(ItemStack result){
        altar_recipes.remove(result);
    }

    public static HashMap<List<ItemStack>, ItemStack> getAltarRecipes() {
        return (HashMap<List<ItemStack>, ItemStack>) altar_recipes.clone();
    }
}
