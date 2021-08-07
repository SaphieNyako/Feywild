package com.feywild.feywild.trade;

import com.feywild.feywild.util.DatapackHelper;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.entity.EntityType;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Map;

public class TradeManager {
    
    private static Map<ResourceLocation, TradeData> trades = ImmutableMap.of();
    
    public static TradeData getTrades(EntityType<?> type, String category) {
        if (type.getRegistryName() == null) {
            return TradeData.EMPTY;
        } else {
            ResourceLocation entityId = type.getRegistryName();
            ResourceLocation id = new ResourceLocation(entityId.getNamespace(), entityId.getPath() + "/" + category);
            return trades.getOrDefault(id, TradeData.EMPTY);
        }
    }
    
    public static IFutureReloadListener createReloadListener() {
        return new ReloadListener<Void>() {
            @Nonnull
            @Override
            protected Void prepare(@Nonnull IResourceManager rm, @Nonnull IProfiler profiler) {
                return null;
            }

            @Override
            protected void apply(@Nonnull Void value, @Nonnull IResourceManager rm, @Nonnull IProfiler profiler) {
                trades = DatapackHelper.loadData(rm, "feywild_trades", TradeData::fromJson);
            }
        };
    }
}
