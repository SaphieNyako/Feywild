package com.feywild.feywild.events;

import com.feywild.feywild.quest.Quest;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class QuestCompletionEvent extends Event {

    private final ServerPlayer entity;
    private final Quest quest;
    private final int totalReputation;
    
    public QuestCompletionEvent(ServerPlayer entity, Quest quest, int totalReputation) {
        this.entity = entity;
        this.quest = quest;
        this.totalReputation = totalReputation;
    }

    public ServerPlayer getEntity() {
        return this.entity;
    }

    public Quest getQuest() {
        return this.quest;
    }

    public int getTotalReputation() {
        return this.totalReputation;
    }
}
