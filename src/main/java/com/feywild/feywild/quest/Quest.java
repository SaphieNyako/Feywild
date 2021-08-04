package com.feywild.feywild.quest;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class Quest {

    public final ResourceLocation id;
    public final int priority;
    
    public final int reputation;
    
    public final QuestDisplay start;
    public final QuestDisplay complete;
    
    public final List<QuestTask> tasks;
    public final List<QuestReward> rewards;

    public Quest(ResourceLocation id, int priority, int reputation, QuestDisplay start, QuestDisplay complete, List<QuestTask> tasks, List<QuestReward> rewards) {
        this.id = id;
        this.priority = priority;
        this.reputation = reputation;
        this.start = start;
        this.complete = complete;
        this.tasks = tasks;
        this.rewards = rewards;
    }
    
    public static Quest fromJson(ResourceLocation id, JsonElement data) {
        JsonObject json = data.getAsJsonObject();
        int priority = json.get("priority").getAsInt();
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
        return new Quest(id, priority, reputation, start, complete, tasks.build(), rewards.build());
    }
}
