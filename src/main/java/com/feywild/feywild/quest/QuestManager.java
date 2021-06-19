package com.feywild.feywild.quest;

import com.feywild.feywild.FeywildMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class QuestManager implements IFutureReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static QuestManager instance;

    public static QuestManager instance()
    {
        if(instance == null)
        {
            instance = new QuestManager();
        }
        return instance;
    }



    @Override
    public CompletableFuture<Void> reload(IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler1, Executor executor, Executor executor1) {
        return CompletableFuture.allOf(CompletableFuture.runAsync(() ->
        {

            String path = "quests";
            Quest.Serializer serializer = new Quest.Serializer();

            List<ResourceLocation> resources = (List<ResourceLocation>) iResourceManager.listResources(path, s -> s.endsWith(".json"));

            resources.forEach(resourceLocation -> {

                    try (InputStream stream = iResourceManager.getResource(resourceLocation).getInputStream();  Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))){

                        Quest quest = serializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class)));

                        if(!QuestMap.quests.contains(quest))
                            QuestMap.quests.add(quest);

                    } catch (IOException e) {
                        e.printStackTrace();
                }
            });
        },executor)).thenCompose(iStage::wait);
    }
}
