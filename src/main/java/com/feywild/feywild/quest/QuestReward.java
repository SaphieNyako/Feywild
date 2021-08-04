package com.feywild.feywild.quest;

import com.feywild.feywild.quest.reward.RewardType;
import com.google.gson.JsonObject;

public class QuestReward {
    
    // Careful with this
    private final RewardType<Object> reward;
    private final Object element;

    private QuestReward(RewardType<Object> reward, Object element) {
        this.reward = reward;
        this.element = element;
    }

    public static QuestReward fromJson(JsonObject json) {
        return null;
    }
}
