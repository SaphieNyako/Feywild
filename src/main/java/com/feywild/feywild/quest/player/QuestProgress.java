package com.feywild.feywild.quest.player;

import com.feywild.feywild.quest.Quest;
import com.feywild.feywild.quest.QuestLine;
import com.feywild.feywild.quest.task.TaskType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class QuestProgress {
    
    public final ResourceLocation quest;
    private final Map<Integer, Integer> taskProgress = new HashMap<>();

    public QuestProgress(ResourceLocation quest) {
        this.quest = quest;
    }

    public <T> boolean checkComplete(ServerPlayerEntity player, QuestLine quests, TaskType<?, T> type, T element) {
        boolean success = false;
        Quest quest = quests.getQuest(this.quest);
        if (quest != null) {
            // Check each task of the quest
            for (int i = 0; i < quest.tasks.size(); i++) {
                if (quest.tasks.get(i).checkCompleted(player, type, element)) {
                    success = true;
                    // Success. Increase the counter for that task by one.
                    this.taskProgress.putIfAbsent(i, 0);
                    this.taskProgress.computeIfPresent(i, (idx, value) -> value + 1);
                }
            }
        }
        return success;
    }
    
    public boolean valid(QuestLine quests) {
        // If a quest got removed we still store the progress in case the
        // datapack is added later. However we don't display it.
        return quests.getQuest(this.quest) != null;
    }
    
    public boolean shouldBeComplete(QuestLine quests) {
        Quest quest = quests.getQuest(this.quest);
        if (quest != null) {
            for (int i = 0; i < quest.tasks.size(); i++) {
                int completionTimes = this.taskProgress.getOrDefault(i, 0);
                if (completionTimes < quest.tasks.get(i).times) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    public CompoundNBT write() {
        CompoundNBT nbt = new CompoundNBT();
        for (Map.Entry<Integer, Integer> entry : this.taskProgress.entrySet()) {
            nbt.putInt(Integer.toString(entry.getKey()), entry.getValue());
        }
        return nbt;
    }

    public void read(CompoundNBT nbt) {
        this.taskProgress.clear();
        for (String key : nbt.getAllKeys()) {
            try {
                int task = Integer.parseInt(key);
                this.taskProgress.put(task, nbt.getInt(key));
            } catch (NumberFormatException e) {
                //
            }
        }
    }
}
