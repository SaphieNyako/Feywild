package com.feywild.feywild.trade.entries;

import com.feywild.feywild.trade.TradeEntry;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.MerchantOffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

// A trade that picks randomly from a list of other trades
public class CompoundTrade implements VillagerTrades.ITrade {
    
    public final List<VillagerTrades.ITrade> trades;

    public CompoundTrade(List<VillagerTrades.ITrade> trades) {
        this.trades = ImmutableList.copyOf(trades);
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(@Nonnull Entity merchant, @Nonnull Random random) {
        if (this.trades.isEmpty()) {
            return null;
        } else {
            return this.trades.get(random.nextInt(this.trades.size())).getOffer(merchant, random);
        }
    }
    
    public static CompoundTrade fromJson(JsonObject json) {
        ImmutableList.Builder<VillagerTrades.ITrade> trades = ImmutableList.builder();
        for (JsonElement elem : json.get("trades").getAsJsonArray()) {
            trades.add(TradeEntry.tradeFromJson(elem.getAsJsonObject()));
        }
        return new CompoundTrade(trades.build());
    }
}
