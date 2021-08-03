package com.feywild.feywild.trade;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Random;

public class TradeData {
    
    public static final TradeData EMPTY = new TradeData(ImmutableList.of());
    
    private final List<TradeLevelData> levels;

    public TradeData(List<TradeLevelData> levels) {
        this.levels = ImmutableList.copyOf(levels);
    }

    public int getMaxLevel() {
        return levels.size();
    }
    
    public TradeLevelData getLevel(int level) {
        return this.levels.get(level - 1);
    }

    public VillagerData levelUp(Entity merchant, VillagerData data, MerchantOffers offers, Random random) {
        if (data.getLevel() < this.getMaxLevel()) {
            getLevel(data.getLevel() + 1).applyTo(merchant, offers, random);
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
