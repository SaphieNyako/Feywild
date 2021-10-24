package com.feywild.feywild.trade;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

// Holds aĺl trades possible for one trader level
public class TradeLevelData {
    
    private final int minTrades;
    private final int maxTrades;
    private final int maxWeight;
    private final boolean allowDuplicates;
    private final List<Pair<Integer, VillagerTrades.ItemListing>> trades;
    private final LazyLoadedValue<List<VillagerTrades.ItemListing>> tradeView;

    public TradeLevelData(int minTrades, int maxTrades, List<TradeEntry> trades, boolean allowDuplicates) {
        this.minTrades = Math.min(minTrades, maxTrades);
        this.maxTrades = Math.max(minTrades, maxTrades);
        
        int maxWeight = 0;
        ImmutableList.Builder<Pair<Integer, VillagerTrades.ItemListing>> weightedTrades = ImmutableList.builder();
        for (TradeEntry entry : trades) {
            if (entry.weight > 0) {
                maxWeight += entry.weight;
                weightedTrades.add(Pair.of(maxWeight, entry.trade));
            }
        }
        this.maxWeight = maxWeight;
        this.trades = weightedTrades.build();
        this.allowDuplicates = allowDuplicates;
        
        if (this.minTrades < 0 || this.maxTrades <= 0) {
            throw new IllegalStateException("Trader level data must be able to select at least one trade. Current range: [" + this.minTrades + ";" + this.maxTrades + "]");
        }
        if (!this.allowDuplicates && this.trades.size() < this.maxTrades) {
            throw new IllegalStateException("Trader level data without duplicates must define at least as many trades as it can select. Current maximum selection: " + this.maxTrades + ". (Defines trades: " + this.trades.size() + ")");
        }
        
        this.tradeView = new LazyLoadedValue<>(() -> {
            //noinspection UnstableApiUsage
            return this.trades.stream().map(Pair::getRight).collect(ImmutableList.toImmutableList());
        });
    }

    public void applyTo(Entity merchant, MerchantOffers offers, Random random) {
        int tradesToSelect = this.minTrades + random.nextInt(1 + (this.maxTrades - this.minTrades));
        List<VillagerTrades.ItemListing> selected = this.selectTrades(tradesToSelect, random);
        for (VillagerTrades.ItemListing trade : selected) {
            MerchantOffer offer = trade.getOffer(merchant, random);
            if (offer != null) {
                offers.add(offer);
            }
        }
    }
    
    public List<VillagerTrades.ItemListing> selectTrades(int trades, Random random) {
        int tradesToSelect = this.allowDuplicates ? trades : Math.min(trades, this.trades.size());
        if (tradesToSelect == 0) {
            return ImmutableList.of();
        } else if (tradesToSelect == 1) {
            return ImmutableList.of(this.trades.get(this.selectRandomTrade(random)).getRight());
        } else if (!this.allowDuplicates && tradesToSelect == this.trades.size()) {
            //noinspection UnstableApiUsage
            return this.trades.stream().map(Pair::getRight).collect(ImmutableList.toImmutableList());
        } else {
            // Stores which indices have been used, so no trade is selected twice.
            Set<Integer> usedIndices = new HashSet<>();
            ImmutableList.Builder<VillagerTrades.ItemListing> builtTrades = ImmutableList.builder();
            for (int i = 0; i < tradesToSelect; i++) {
                int tradeIdx;
                do {
                    tradeIdx = this.selectRandomTrade(random);
                } while (usedIndices.contains(tradeIdx));
                if (!this.allowDuplicates) {
                    usedIndices.add(tradeIdx);
                }
                builtTrades.add(this.trades.get(tradeIdx).getRight());
            }
            return builtTrades.build();
        }
    }
    
    private int selectRandomTrade(Random random) {
        int id = random.nextInt(this.maxWeight);
        for (int i = 0; i < this.trades.size(); i++) {
            if (id < this.trades.get(i).getLeft()) {
                return i;
            }
        }
        return this.trades.size() - 1;
    }
    
    public List<VillagerTrades.ItemListing> getAllTrades() {
        return this.tradeView.get();
    }
    
    public static TradeLevelData fromJson(JsonObject json) {
        int min;
        int max;
        JsonElement amount = json.get("amount");
        if (amount.isJsonArray() && amount.getAsJsonArray().size() == 2) {
            min = amount.getAsJsonArray().get(0).getAsInt();
            max = amount.getAsJsonArray().get(0).getAsInt();
        } else {
            min = amount.getAsInt();
            max = amount.getAsInt();
        }
        ImmutableList.Builder<TradeEntry> trades = ImmutableList.builder();
        for (JsonElement elem : json.get("trades").getAsJsonArray()) {
            trades.add(TradeEntry.fromJson(elem.getAsJsonObject()));
        }
        boolean allowDuplicates = json.has("duplicates") && json.get("duplicates").getAsBoolean();
        return new TradeLevelData(min, max, trades.build(), allowDuplicates);
    }
}
