package com.feywild.feywild.trade;

import com.feywild.feywild.trade.entries.CompoundTrade;
import com.feywild.feywild.trade.entries.SimpleTrade;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.npc.VillagerTrades;

// Holds one trade with a weight
public class TradeEntry {
    
    public final int weight;
    public final VillagerTrades.ItemListing trade;

    public TradeEntry(int weight, VillagerTrades.ItemListing trade) {
        this.weight = weight;
        this.trade = trade;
    }
    
    public static TradeEntry fromJson(JsonObject json) {
        int weight = json.has("weight") ? json.get("weight").getAsInt() : 1;
        return new TradeEntry(weight, tradeFromJson(json));
    }
    
    public static VillagerTrades.ItemListing tradeFromJson(JsonObject json) {
        String type = json.has("type") ? json.get("type").getAsString() : "simple";
        switch (type) {
            case "simple": return SimpleTrade.fromJson(json);
            case "compound": return CompoundTrade.fromJson(json);
            default: throw new IllegalStateException("Unknown trade type: " + type);
        }
    }
}
