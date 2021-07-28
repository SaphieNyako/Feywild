package com.feywild.feywild.events;

import com.feywild.feywild.quest.Quest;
import com.google.common.base.Preconditions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;


//TODO add a base event class in case more events are needed
@Cancelable
public class QuestCompletionEvent extends Event {

    private PlayerEntity entity;
    private Quest quest;
    private int reputation;
    private ActionResultType cancellationResult = ActionResultType.PASS;

    public QuestCompletionEvent(PlayerEntity entity, Quest quest, int reputation){
        this.entity = Preconditions.checkNotNull(entity, "Player is null!");
        this.quest = Preconditions.checkNotNull(quest, "Quest is null!");
        this.reputation = reputation;
    }

    public PlayerEntity getEntity() {
        return entity;
    }

    public Quest getQuest() {
        return quest;
    }

    public int getReputation() {
        return reputation;
    }
}
