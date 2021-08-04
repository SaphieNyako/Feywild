package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;

public interface TaskType<T, X> {
    
    Class<T> element();
    Class<X> testType();
    
    boolean checkCompleted(ServerPlayerEntity player, T element, X match);
    
    T fromJson(JsonObject json);
    JsonObject toJson(T element);
    
    default boolean repeatable() {
        return true;
    }
}
