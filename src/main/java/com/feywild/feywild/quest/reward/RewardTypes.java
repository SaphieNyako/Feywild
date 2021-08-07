package com.feywild.feywild.quest.reward;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.ResourceLocation;

public class RewardTypes {

    private static final BiMap<ResourceLocation, RewardType<?>> values = HashBiMap.create();

    public static RewardType<?> getType(ResourceLocation id) {
        if (values.containsKey(id)) {
            return values.get(id);
        } else {
            throw new IllegalStateException("Unknown quest task type: " + id);
        }
    }

    public static ResourceLocation getId(RewardType<?> type) {
        if (values.containsValue(type)) {
            return values.inverse().get(type);
        } else {
            throw new IllegalStateException("Quest task type not registered: " + type);
        }
    }

    public static void register(ResourceLocation id, RewardType<?> type) {
        if (values.containsKey(id)) {
            throw new IllegalStateException("Task type with the same id already registered: " + id);
        } else if (values.containsValue(type)) {
            throw new IllegalStateException("Task type registered twice: " + type);
        } else {
            values.put(id, type);
        }
    }
}
