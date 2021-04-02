package com.feywild.feywild.events;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.ModItems;
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


    //register recipes
    public static void register() {
        //Scroll recipes
        addAltarRecipe(new ItemStack(ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get()), Arrays.asList(new ItemStack(ModItems.FEY_DUST.get()),new ItemStack(Items.RED_MUSHROOM), new ItemStack(Items.COAL), new ItemStack(Items.FEATHER), new ItemStack(Items.SWEET_BERRIES)));
        addAltarRecipe(new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get()), Arrays.asList(new ItemStack(ModItems.FEY_DUST.get()),new ItemStack(Items.OAK_SAPLING), new ItemStack(Items.LILY_OF_THE_VALLEY), new ItemStack(Items.BONE_MEAL), new ItemStack(Items.EGG)));
        addAltarRecipe(new ItemStack(ModItems.SUMMONING_SCROLL_SUMMER_PIXIE.get()), Arrays.asList(new ItemStack(ModItems.FEY_DUST.get()),new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.CHARCOAL), new ItemStack(Items.SUNFLOWER), new ItemStack(Items.GOLD_INGOT)));
        addAltarRecipe(new ItemStack(ModItems.SUMMONING_SCROLL_WINTER_PIXIE.get()), Arrays.asList(new ItemStack(ModItems.FEY_DUST.get()),new ItemStack(Items.SNOWBALL), new ItemStack(Items.QUARTZ), new ItemStack(Items.STRING), new ItemStack(Items.IRON_INGOT)));

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
        return altar_recipes;
    }


    // Ancient's Note : this isn't the 'right' way to do it but it works
}
