package com.feywild.feywild.trade.recipe;

import com.feywild.feywild.trade.TradeData;
import com.feywild.feywild.trade.TradeLevelData;
import com.feywild.feywild.trade.entries.CompoundTrade;
import com.feywild.feywild.trade.entries.SimpleTrade;
import com.feywild.feywild.trade.item.CompoundStackFactory;
import com.feywild.feywild.trade.item.SimpleStackFactory;
import com.feywild.feywild.trade.item.StackFactory;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TradeRecipeManager {
    
    public static List<TradeRecipe> getRecipes(ResourceLocation id, TradeData data) {
        ImmutableList.Builder<TradeRecipe> recipe = ImmutableList.builder();
        for (int i = 1; i <= data.getMaxLevel(); i++) {
            recipe.add(getRecipe(id, i, data.getLevel(i)));
        }
        return recipe.build();
    }
    
    private static TradeRecipe getRecipe(ResourceLocation id, int level, TradeLevelData data) {
        List<TradeRecipe.Entry> trades = data.getAllTrades().stream().flatMap(TradeRecipeManager::expandTrades).collect(Collectors.toList());
        return new TradeRecipe(id, level, trades);
    }
    
    private static Stream<TradeRecipe.Entry> expandTrades(VillagerTrades.ItemListing trade) {
        if (trade instanceof SimpleTrade) {
            return Stream.of(expandSimpleTrade((SimpleTrade) trade));
        } else if (trade instanceof CompoundTrade) {
            return ((CompoundTrade) trade).trades.stream().flatMap(TradeRecipeManager::expandTrades);
        } else {
            return Stream.empty();
        }
    }
    
    private static TradeRecipe.Entry expandSimpleTrade(SimpleTrade trade) {
        List<ItemStack> input = expandFactory(trade.input).collect(Collectors.toList());
        List<ItemStack> additional = expandFactory(trade.additional).collect(Collectors.toList());
        List<ItemStack> output = expandFactory(trade.output).collect(Collectors.toList());
        return new TradeRecipe.Entry(input, additional, output);
    }
    
    private static Stream<ItemStack> expandFactory(StackFactory factory) {
        if (factory instanceof SimpleStackFactory) {
            return Stream.of(((SimpleStackFactory) factory).getStack());
        } else if (factory instanceof CompoundStackFactory) {
            return ((CompoundStackFactory) factory).factories.stream().flatMap(TradeRecipeManager::expandFactory);
        } else {
            return Stream.empty();
        }
    }
}
