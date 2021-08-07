package com.feywild.feywild.quest.reward;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;

public interface RewardType<T> {

    Class<T> element();

    void grantReward(ServerPlayerEntity player, T element);

    T fromJson(JsonObject json);
    JsonObject toJson(T element);
}
