package com.feywild.feywild.events;

import com.feywild.feywild.quest.Quest;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class QuestCompletionEvent extends Event {

    private final ServerPlayerEntity entity;
    private final Quest quest;
    private final int totalReputation;
    
    public QuestCompletionEvent(ServerPlayerEntity entity, Quest quest, int totalReputation) {
        this.entity = entity;
        this.quest = quest;
        this.totalReputation = totalReputation;
    }

    public ServerPlayerEntity getEntity() {
        return this.entity;
    }

    public Quest getQuest() {
        return this.quest;
    }

    public int getTotalReputation() {
        return this.totalReputation;
    }
}
