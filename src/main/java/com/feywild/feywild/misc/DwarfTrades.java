package com.feywild.feywild.misc;

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


    public static void registerTrades(){
        addTrade(new ItemStack(Items.COAL,32), new ItemStack(Items.DIAMOND));
        addTrade(new ItemStack(Items.IRON_ORE,3), new ItemStack(Items.IRON_INGOT,5));
        addTrade(new ItemStack(Items.REDSTONE,16), new ItemStack(Items.LAPIS_LAZULI, 4));

    }

    public static void addTrade(ItemStack required, ItemStack reward) {trades.put(required,reward);}

    public static void removeTrade(ItemStack required){
        trades.remove(required);
    }

    public static HashMap<ItemStack,ItemStack> getTrades(){
        return trades;
    }
}
