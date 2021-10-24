package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public interface TaskType<T, X> {
    
    Class<T> element();
    Class<X> testType();
    
    boolean checkCompleted(ServerPlayer player, T element, X match);
    
    T fromJson(JsonObject json);
    JsonObject toJson(T element);
    
    default boolean repeatable() {
        return true;
    }
    
    @Nullable
    default Item icon(T element) {
        return null;
    }
}
