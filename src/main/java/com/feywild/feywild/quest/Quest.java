package com.feywild.feywild.quest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
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
        this.parents = ImmutableSet.copyOf(parents);
        this.reputation = reputation;
        this.start = start;
        this.complete = complete;
        this.tasks = ImmutableList.copyOf(tasks);
        this.rewards = ImmutableList.copyOf(rewards);
        if (this.parents.isEmpty() && !this.id.getPath().endsWith("/root")) {
            throw new IllegalStateException("Can't create non-root quest without parents.");
        }
        if (this.parents.contains(this.id)) {
            throw new IllegalStateException("Can't create quest with self-reference.");
        }
    }
    
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (this.parents.size() == 1) {
            json.addProperty("parent", this.parents.iterator().next().toString());
        } else if (!this.parents.isEmpty()) {
            JsonArray array = new JsonArray();
            this.parents.forEach(rl -> array.add(rl.toString()));
            json.add("parent", array);
        }
        json.addProperty("reputation", this.reputation);
        json.add("start", this.start.toJson());
        json.add("complete", this.start.toJson());
        if (!this.tasks.isEmpty()) {
            JsonArray array = new JsonArray();
            for (QuestTask task : this.tasks) {
                array.add(task.toJson());
            }
            json.add("tasks", array);
        }
        if (!this.rewards.isEmpty()) {
            JsonArray array = new JsonArray();
            for (QuestReward task : this.rewards) {
                array.add(task.toJson());
            }
            json.add("rewards", array);
        }
        return json;
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
