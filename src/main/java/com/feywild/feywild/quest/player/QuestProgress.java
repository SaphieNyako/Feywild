package com.feywild.feywild.quest.player;

import com.feywild.feywild.quest.Quest;
import com.feywild.feywild.quest.QuestLine;
import com.feywild.feywild.quest.task.TaskType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class QuestProgress {
    
    public final ResourceLocation quest;
    private final Map<Integer, Integer> taskProgress = new HashMap<>();

    public QuestProgress(ResourceLocation quest) {
        this.quest = quest;
    }

    @Nullable
    public <T> String checkComplete(ServerPlayer player, QuestLine quests, TaskType<?, T> type, T element) {
        String progressMsg = null;
        Quest quest = quests.getQuest(this.quest);
        if (quest != null) {
            // Check each task of the quest
            for (int i = 0; i < quest.tasks.size(); i++) {
                if (quest.tasks.get(i).checkCompleted(player, type, element)) {
                    // Success. Increase the counter for that task by one.
                    this.taskProgress.putIfAbsent(i, 0);
                    this.taskProgress.computeIfPresent(i, (idx, value) -> value + 1);
                    if (progressMsg == null) progressMsg = this.taskProgress.get(i) + " / " + quest.tasks.get(i).times;
                }
            }
        }
        return progressMsg;
    }
    
    public boolean valid(QuestLine quests) {
        // If a quest got removed we still store the progress in case the
        // datapack is added later. However, we don't display it.
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
    
    public CompoundTag write() {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<Integer, Integer> entry : this.taskProgress.entrySet()) {
            nbt.putInt(Integer.toString(entry.getKey()), entry.getValue());
        }
        return nbt;
    }

    public void read(CompoundTag nbt) {
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
