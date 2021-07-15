package com.feywild.feywild.quest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class QuestManager implements IFutureReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static QuestManager instance;

    public static QuestManager instance() {
        if (instance == null) {
            instance = new QuestManager();
        }
        return instance;
    }


    @Nonnull
    @Override
    public CompletableFuture<Void> reload(IStage iStage, @Nonnull IResourceManager iResourceManager, @Nonnull IProfiler iProfiler, @Nonnull IProfiler iProfiler1, @Nonnull Executor executor, @Nonnull Executor executor1) {
        return CompletableFuture.allOf(CompletableFuture.runAsync(() ->
        {

            String path = "feywild_quests";
            Quest.Serializer serializer = new Quest.Serializer();

            List<ResourceLocation> resources = (List<ResourceLocation>) iResourceManager.listResources(path, s -> s.endsWith(".json"));

            resources.forEach(resourceLocation -> {

                try (InputStream stream = iResourceManager.getResource(resourceLocation).getInputStream(); Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                    Quest quest = serializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class)));

                    if (!QuestMap.quests.contains(quest))
                        QuestMap.quests.add(quest);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.print("You are not abiding by the rules of the feywild! (Quest setup is wrong)");
                }
            });
        }, executor)).thenCompose(iStage::wait);
    }
}
