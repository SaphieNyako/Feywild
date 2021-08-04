package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public interface TaskType<T, X> {
    
    Class<T> element();
    Class<X> testType();
    
    boolean checkCompleted(ServerPlayerEntity player, T element, X match);
    
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
