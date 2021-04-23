package com.feywild.feywild.misc;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DwarfTrades {

    private static HashMap<ItemStack,ItemStack> trades  = new HashMap<>();
    private static HashMap<ItemStack, ItemStack> tamedTrades = new HashMap<>();


    public static void registerTrades(){
        /*UNTAMED*/
        addTrade(new ItemStack(Items.COAL,32), new ItemStack(Items.DIAMOND));
        addTrade(new ItemStack(Items.IRON_ORE,3), new ItemStack(Items.IRON_INGOT,5));
        addTrade(new ItemStack(Items.REDSTONE,16), new ItemStack(Items.LAPIS_LAZULI, 4));
        addTrade(new ItemStack(Items.IRON_ORE, 10), new ItemStack(ModItems.LESSER_FEY_GEM.get(), 2));
        addTrade(new ItemStack(Items.GOLD_ORE, 7), new ItemStack(ModItems.GREATER_FEY_GEM.get(), 1));
        addTrade(new ItemStack(Items.GOLD_ORE,3), new ItemStack(Items.GOLD_INGOT,5));

        /*TAMED*/
        addTamedTrade(new ItemStack(Items.DIAMOND,4), new ItemStack(ModBlocks.FEY_ALTAR.get().asItem()));

    }

    public static void addTrade(ItemStack required, ItemStack reward) {trades.put(required,reward);}


    public static void addTamedTrade(ItemStack required, ItemStack reward) {tamedTrades.put(required,reward);}

    public static void removeTrade(ItemStack required){
        trades.remove(required);
    }

    public static void removeTamedTrade(ItemStack required){
        tamedTrades.remove(required);
    }

    public static HashMap<ItemStack,ItemStack> getTrades(){
        return trades;
    }
    public static HashMap<ItemStack,ItemStack> getTamedTrades(){
        return tamedTrades;
    }
}
