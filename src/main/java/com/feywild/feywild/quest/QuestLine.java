package com.feywild.feywild.quest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public class QuestLine {
    
    public static final QuestLine EMPTY = new QuestLine(ImmutableMap.of());
    
    private final Map<ResourceLocation, Quest> questMap;
    private final List<Quest> quests;
    private final Map<ResourceLocation, ResourceLocation> questOrder;
    
    public QuestLine(Map<ResourceLocation, Quest> quests) {
        this.questMap = ImmutableMap.copyOf(quests);
        //noinspection UnstableApiUsage
        this.quests = quests.values().stream().sorted(QuestManager.QUEST_ORDER).collect(ImmutableList.toImmutableList());
        ImmutableMap.Builder<ResourceLocation, ResourceLocation> questOrder = ImmutableMap.builder();
        for (int i = 0; i < this.quests.size() - 1; i++) {
            questOrder.put(this.quests.get(i).id, this.quests.get(i + 1).id);
        }
        this.questOrder = questOrder.build();
    }
    
}
