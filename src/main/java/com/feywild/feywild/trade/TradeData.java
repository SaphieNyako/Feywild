package com.feywild.feywild.trade;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Random;

// Holds all trades for one trade category (e.g. tamed and untamed) for all levels
public class TradeData {
    
    public static final TradeData EMPTY = new TradeData(ImmutableList.of());
    
    private final List<TradeLevelData> levels;

    public TradeData(List<TradeLevelData> levels) {
        this.levels = ImmutableList.copyOf(levels);
    }

    public int getMaxLevel() {
        return this.levels.size();
    }
    
    public TradeLevelData getLevel(int level) {
        return this.levels.get(level - 1);
    }

    public VillagerData initialize(Entity merchant, VillagerData data, MerchantOffers offers, Random random) {
        if (this.getMaxLevel() >= 1) {
            this.getLevel(1).applyTo(merchant, offers, random);
            return new VillagerData(data.getType(), data.getProfession(), 1);
        } else {
            return data;
        }
    }
    
    public VillagerData levelUp(Entity merchant, VillagerData data, MerchantOffers offers, Random random) {
        if (data.getLevel() < this.getMaxLevel()) {
            this.getLevel(data.getLevel() + 1).applyTo(merchant, offers, random);
            return new VillagerData(data.getType(), data.getProfession(), data.getLevel() + 1);
        } else {
            return data;
        }
    }
    
    public static TradeData fromJson(ResourceLocation id, JsonElement data) {
        ImmutableList.Builder<TradeLevelData> levels = ImmutableList.builder();
        for (JsonElement elem : data.getAsJsonArray()) {
            levels.add(TradeLevelData.fromJson(elem.getAsJsonObject()));
        }
        return new TradeData(levels.build());
    }
}
