package com.feywild.feywild.quest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public class QuestLine {
    
    public static final QuestLine EMPTY = new QuestLine(ImmutableMap.of());
    
    private final Map<ResourceLocation, Quest> quests;
    private final Map<ResourceLocation, List<Quest>> questOrder;
    
    public QuestLine(Map<ResourceLocation, Quest> quests) {
        this.quests = ImmutableMap.copyOf(quests);
        ImmutableMap.Builder<ResourceLocation, List<Quest>> questOrder = ImmutableMap.builder();
        for (Quest quest : this.quests.values()) {
            //noinspection UnstableApiUsage
            questOrder.put(quest.id, quest.parents.stream().map(rl -> {
                if (this.quests.containsKey(rl)) {
                    return this.quests.get(rl);
                } else {
                    throw new IllegalStateException("Invalid reference in quest: " + rl);
                }
            }).collect(ImmutableList.toImmutableList()));
        }
        this.questOrder = questOrder.build();
    }
}
