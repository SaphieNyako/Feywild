package com.feywild.feywild.entity.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TradeManager implements IFutureReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static TradeManager instance;
    private static String tamedPaths[] = {"feywild_trades/tamed/food", "feywild_trades/tamed/loot"};

    public static TradeManager instance()
    {
        if(instance == null)
        {
            instance = new TradeManager();
        }
        return instance;
    }

    @Override
    public CompletableFuture<Void> reload(IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler1, Executor executor, Executor executor1) {
        return CompletableFuture.allOf(CompletableFuture.runAsync(() ->
        {
            //For tamed
            DwarvenTrades.TamedSerializer serializer = new DwarvenTrades.TamedSerializer();
            for (String path : tamedPaths) {
                List<ResourceLocation> resources = (List<ResourceLocation>) iResourceManager.listResources(path, s -> s.endsWith(".json"));

                resources.forEach(resourceLocation -> {

                    try (InputStream stream = iResourceManager.getResource(resourceLocation).getInputStream(); Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                        if(resourceLocation.getPath().contains("common")) {
                            List<ItemStack> common = serializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class)), 0);
                            if(!common.isEmpty()) {
                                if (path.endsWith("loot"))
                                    DwarvenTrades.commonLoot.addAll(common);
                                else if (path.endsWith("food"))
                                    DwarvenTrades.commonFood.addAll(common);
                            }
                        }
                        if(resourceLocation.getPath().contains("legendary")) {
                            List<ItemStack> legendary = serializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class)), 1);
                            if(!legendary.isEmpty()){
                                if(path.endsWith("loot"))
                                    DwarvenTrades.legendaryLoot.addAll(legendary);
                                else if(path.endsWith("food"))
                                    DwarvenTrades.legendaryFood.addAll(legendary);
                            }
                        }


                    } catch (IOException e) {
                        System.out.print("You are not abiding by the rules of the feywild! (Quest setup is wrong)");
                        e.printStackTrace();
                    }
                });
            }
        },executor)).thenCompose(iStage::wait);
    }
}
