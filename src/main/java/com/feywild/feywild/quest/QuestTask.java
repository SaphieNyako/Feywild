package com.feywild.feywild.quest;

import com.feywild.feywild.quest.task.RegistryTaskType;
import com.feywild.feywild.quest.task.TaskType;
import com.feywild.feywild.quest.task.TaskTypes;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class QuestTask {

    private final TaskType<Object, Object> task;
    private final Object element;
    public final int times;

    private QuestTask(TaskType<Object, Object> task, Object element, int times) {
        this.task = task;
        this.element = element;
        this.times = times <= 0 ? 1 : times;
        if (!this.task.element().isAssignableFrom(element.getClass())) {
            throw new IllegalStateException("Can't create quest task: element type mismatch");
        }
        if (!this.task.repeatable() && this.times != 1) {
            throw new IllegalStateException("Can't create quest task: can't repeat non-repeatable task type.");
        }
    }

    public static <T> QuestTask of(TaskType<T, ?> type, T element) {
        return of(type, element, 1);
    }

    public static <T> QuestTask of(TaskType<T, ?> type, T element, int times) {
        //noinspection unchecked
        return new QuestTask((TaskType<Object, Object>) type, element, times);
    }
    
    public static <T> QuestTask ofEntry(RegistryTaskType<T, ?> type, T element) {
        return ofEntry(type, element, 1);
    }

    public static <T> QuestTask ofEntry(RegistryTaskType<T, ?> type, T element, int times) {
        ResourceKey<T> key = type.registry().getResourceKey(element).orElse(null);
        if (key == null) throw new IllegalArgumentException("Object not registered: " + element);
        //noinspection unchecked
        return new QuestTask((TaskType<Object, Object>) (TaskType<?, ?>) type, key, times);
    }

    public boolean checkCompleted(ServerPlayer player, TaskType<?, ?> type, Object match) {
        if (this.task == type && this.task.testType().isAssignableFrom(match.getClass())) {
            return this.task.checkCompleted(player, this.element, match);
        } else {
            return false;
        }
    }

    public <T> Optional<T> getQuestValueFor(TaskType<T, ?> type) {
        if (this.task == type) {
            //noinspection unchecked
            return Optional.of((T) this.element);
        } else {
            return Optional.empty();
        }
    }

    public Item icon() {
        return this.task.icon(this.element);
    }

    public JsonObject toJson() {
        JsonObject json = this.task.toJson(this.element);
        json.addProperty("id", TaskTypes.getId(this.task).toString());
        if (this.times != 1) {
            json.addProperty("times", this.times);
        }
        return json;
    }

    public static QuestTask fromJson(JsonObject json) {
        //noinspection unchecked
        TaskType<Object, Object> task = (TaskType<Object, Object>) TaskTypes.getType(new ResourceLocation(json.get("id").getAsString()));
        Object element = task.fromJson(json);
        int times = json.has("times") ? json.get("times").getAsInt() : 1;
        return new QuestTask(task, element, times);
    }
}
