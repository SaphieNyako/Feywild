package com.feywild.feywild.entity.util.trades;

import com.feywild.feywild.item.ModItems;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.feywild.feywild.entity.util.trades.DwarvenTrades.*;

public class TamedTradeManager implements IFutureReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static TamedTradeManager instance;
    private static String[] tamedPaths = {"feywild_trades/tamed/food", "feywild_trades/tamed/loot"};
    private static String[] untamedPaths = {"feywild_trades/untamed", "feywild_trades/tamed/static"};

    public static TamedTradeManager instance() {
        if (instance == null) {
            instance = new TamedTradeManager();
        }
        return instance;
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> reload(IStage iStage, @Nonnull IResourceManager iResourceManager, @Nonnull IProfiler iProfiler, @Nonnull IProfiler iProfiler1, @Nonnull Executor executor, @Nonnull Executor executor1) {
        return CompletableFuture.allOf(CompletableFuture.runAsync(() ->
        {
            //For tamed
            DwarvenTrades.TamedSerializer serializer = new DwarvenTrades.TamedSerializer();
            for (String path : tamedPaths) {
                List<ResourceLocation> resources = (List<ResourceLocation>) iResourceManager.listResources(path, s -> s.endsWith(".json"));

                resources.forEach(resourceLocation -> {

                    try (InputStream stream = iResourceManager.getResource(resourceLocation).getInputStream(); Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                        if (resourceLocation.getPath().contains("common")) {
                            List<TradeData> common = serializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class)), 0);
                            if (!common.isEmpty()) {
                                if (path.endsWith("loot"))
                                    DwarvenTrades.commonLoot.addAll(common);
                                else if (path.endsWith("food"))
                                    DwarvenTrades.commonFood.addAll(common);
                            }
                        }
                        if (resourceLocation.getPath().contains("legendary")) {
                            List<TradeData> legendary = serializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class)), 1);
                            if (!legendary.isEmpty()) {
                                if (path.endsWith("loot"))
                                    DwarvenTrades.legendaryLoot.addAll(legendary);
                                else if (path.endsWith("food"))
                                    DwarvenTrades.legendaryFood.addAll(legendary);
                            }
                        }

                    } catch (IOException e) {
                        System.out.print("You are not abiding by the rules of the feywild! (Tamed trade setup is wrong)");
                        e.printStackTrace();
                    }
                });
            }

            DwarvenTrades.UntamedSerializer untamedSerializer = new DwarvenTrades.UntamedSerializer();

            for (String path : untamedPaths) {
                List<ResourceLocation> resources = (List<ResourceLocation>) iResourceManager.listResources(path, s -> s.endsWith(".json"));

                resources.forEach(resourceLocation -> {

                    try (InputStream stream = iResourceManager.getResource(resourceLocation).getInputStream(); Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                        List<DwarvenTrades.SimplyTrade> trades = untamedSerializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class)));
                        int level = Integer.parseInt(String.valueOf(resourceLocation.getPath().charAt(resourceLocation.getPath().length() - 6)));
                        switch (level) {
                            case 1:
                                untamedLevel1.addAll(trades);
                                break;
                            case 2:
                                untamedLevel2.addAll(trades);
                                break;
                            default:
                                //Do nothing
                                break;
                        }
                    } catch (IOException e) {
                        System.out.print("You are not abiding by the rules of the feywild! (Untamed trade setup is wrong)");
                        e.printStackTrace();
                    }
                });
                DWARVEN_TRADES = toIntMap(ImmutableMap.of(
                        1, getTrades(untamedLevel1), 2, getTrades(untamedLevel2), 3, getTrades(Collections.singletonList(new SimplyTrade(new ItemStack(ModItems.lesserFeyGem, 20), new ItemStack(ModItems.summoningScrollDwarfBlacksmith, 1), 1, 1, 10)
                        ))));
            }

        }, executor)).thenCompose(iStage::wait);
    }
}
