package com.feywild.feywild.quest.reward;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;

public interface RewardType<T> {

    Class<T> element();

    void grantReward(ServerPlayer player, T element);

    T fromJson(JsonObject json);

    JsonObject toJson(T element);
}
