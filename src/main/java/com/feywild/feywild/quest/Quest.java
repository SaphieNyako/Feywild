package com.feywild.feywild.quest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Quest {

    public final ResourceLocation id;
    // One of them is required
    public final Set<ResourceLocation> parents;
    
    public final boolean repeatable;
    public final int reputation;

    public final Item icon;
    public final QuestDisplay start;
    @Nullable
    public final QuestDisplay complete;

    public final List<QuestTask> tasks;
    public final List<QuestReward> rewards;

    public Quest(ResourceLocation id, Set<ResourceLocation> parents, boolean repeatable, int reputation, Item icon, QuestDisplay start, @Nullable QuestDisplay complete, List<QuestTask> tasks, List<QuestReward> rewards) {
        this.id = id;
        this.parents = ImmutableSet.copyOf(parents);
        this.repeatable = repeatable;
        this.reputation = reputation;
        this.icon = icon;
        this.start = start;
        this.complete = complete;
        this.tasks = ImmutableList.copyOf(tasks);
        this.rewards = ImmutableList.copyOf(rewards);
        if (this.parents.isEmpty() && !this.id.getPath().equals("root")) {
            throw new IllegalStateException("Can't create non-root quest without parents: " + this.id);
        }
        if (this.parents.contains(this.id)) {
            throw new IllegalStateException("Can't create quest with self-reference: " + this.id);
        }
        if (this.complete != null && this.tasks.isEmpty()) {
            throw new IllegalStateException("A quest that has no tasks can't have a completion: " + this.id);
        }
        if (this.tasks.isEmpty() && this.repeatable) {
            throw new IllegalStateException("A quest that has no tasks can't be repeatable: " + this.id);
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
        if (this.repeatable) {
            json.addProperty("repeatable", true);
        }
        json.addProperty("reputation", this.reputation);
        json.addProperty("icon", Objects.requireNonNull(this.icon.getRegistryName()).toString());
        json.add("start", this.start.toJson());
        if (this.complete != null) {
            json.add("complete", this.complete.toJson());
        }
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
        boolean repeatable = json.has("repeatable") && json.get("repeatable").getAsBoolean();
        int reputation = json.has("reputation") ? json.get("reputation").getAsInt() : 5;
        Item icon = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.get("icon").getAsString()));
        QuestDisplay start = QuestDisplay.fromJson(json.get("start").getAsJsonObject());
        QuestDisplay complete = json.has("complete") ? QuestDisplay.fromJson(json.get("complete").getAsJsonObject()) : null;
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
        return new Quest(id, parents.build(), repeatable, reputation, icon, start, complete, tasks.build(), rewards.build());
    }
}
