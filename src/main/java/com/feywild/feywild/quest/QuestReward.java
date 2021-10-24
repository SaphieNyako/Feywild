package com.feywild.feywild.quest;

import com.feywild.feywild.quest.reward.RewardType;
import com.feywild.feywild.quest.reward.RewardTypes;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class QuestReward {

    private final RewardType<Object> reward;
    private final Object element;

    private QuestReward(RewardType<Object> reward, Object element) {
        this.reward = reward;
        this.element = element;
        if (!this.reward.element().isAssignableFrom(element.getClass())) {
            throw new IllegalStateException("Can't create quest task: element type mismatch");
        }
    }

    public static <T> QuestReward of(RewardType<T> type, T element) {
        //noinspection unchecked
        return new QuestReward((RewardType<Object>) type, element);
    }

    public void grantReward(ServerPlayer player) {
        this.reward.grantReward(player, this.element);
    }

    public JsonObject toJson() {
        JsonObject json = this.reward.toJson(this.element);
        json.addProperty("id", RewardTypes.getId(this.reward).toString());
        return json;
    }

    public static QuestReward fromJson(JsonObject json) {
        //noinspection unchecked
        RewardType<Object> reward = (RewardType<java.lang.Object>) RewardTypes.getType(new ResourceLocation(json.get("id").getAsString()));
        Object element = reward.fromJson(json);
        return new QuestReward(reward, element);
    }
}
