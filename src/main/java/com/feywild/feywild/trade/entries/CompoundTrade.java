package com.feywild.feywild.trade.entries;

import com.feywild.feywild.trade.TradeEntry;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

// A trade that picks randomly from a list of other trades
public class CompoundTrade implements VillagerTrades.ItemListing {

    public final List<VillagerTrades.ItemListing> trades;

    public CompoundTrade(List<VillagerTrades.ItemListing> trades) {
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
        ImmutableList.Builder<VillagerTrades.ItemListing> trades = ImmutableList.builder();
        for (JsonElement elem : json.get("trades").getAsJsonArray()) {
            trades.add(TradeEntry.tradeFromJson(elem.getAsJsonObject()));
        }
        return new CompoundTrade(trades.build());
    }
}
