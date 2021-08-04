package com.feywild.feywild.quest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Set;

public class Quest {

    public final ResourceLocation id;
    // One of them is required
    public final Set<ResourceLocation> parents;
    
    public final int reputation;
    
    public final QuestDisplay start;
    public final QuestDisplay complete;
    
    public final List<QuestTask> tasks;
    public final List<QuestReward> rewards;

    public Quest(ResourceLocation id, Set<ResourceLocation> parents, int reputation, QuestDisplay start, QuestDisplay complete, List<QuestTask> tasks, List<QuestReward> rewards) {
        this.id = id;
        this.parents = parents;
        this.reputation = reputation;
        this.start = start;
        this.complete = complete;
        this.tasks = tasks;
        this.rewards = rewards;
        if (this.parents.isEmpty() && !this.id.getPath().endsWith("/root")) {
            throw new IllegalStateException("Can't create non-root quest without parents.");
        }
    }
    
    public static Quest fromJson(ResourceLocation id, JsonElement data) {
        JsonObject json = data.getAsJsonObject();
        ImmutableSet.Builder<ResourceLocation> parents = ImmutableSet.builder();
        if (json.has("parent") && json.get("parent").isJsonArray()) {
            for (JsonElement elem : json.get("parent").getAsJsonArray()) {
                parents.add(new ResourceLocation(elem.getAsString()));
            }
        } else if (json.has("parent")) {
            parents.add(new ResourceLocation(json.get("parent").getAsString()));
        }
        int reputation = json.has("reputation") ? json.get("reputation").getAsInt() : 5;
        QuestDisplay start = QuestDisplay.fromJson(json.get("start").getAsJsonObject());
        QuestDisplay complete = QuestDisplay.fromJson(json.get("complete").getAsJsonObject());
        ImmutableList.Builder<QuestTask> tasks = ImmutableList.builder();
        if (json.has("tasks")) {
            for (JsonElement elem : json.get("tasks").getAsJsonArray()) {
                tasks.add(QuestTask.fromJson(elem.getAsJsonObject()));
            }
        }
        ImmutableList.Builder<QuestReward> rewards = ImmutableList.builder();
        if (json.has("rewards")) {
            for (JsonElement elem : json.get("rewards").getAsJsonArray()) {
                rewards.add(QuestReward.fromJson(elem.getAsJsonObject()));
            }
        }
        return new Quest(id, parents.build(), reputation, start, complete, tasks.build(), rewards.build());
    }
}
