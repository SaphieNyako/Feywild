package com.feywild.feywild.quest.player;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.*;
import com.feywild.feywild.quest.task.TaskType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.*;

public class QuestData {
    
    public static QuestData get(ServerPlayerEntity player) {
        // Capability should always be there.
        // If not print a warning and get default instance
        return player.getCapability(CapabilityQuests.QUESTS).orElseGet(() -> {
            FeywildMod.getInstance().logger.warn("Quest Data capability not present on player: " + player);
            return new QuestData();
        });
    }
    
    @Nullable
    private ServerPlayerEntity player;
    
    @Nullable
    private Alignment alignment;
    // Quests are completed but the player did not interact with the fey since then
    private final List<ResourceLocation> pendingCompletion = new ArrayList<>();
    private final Set<ResourceLocation> completedQuests = new HashSet<>();
    private final Map<ResourceLocation, QuestProgress> activeQuests = new HashMap<>();
    
    // Called when the capability is attached to the player
    public void attach(ServerPlayerEntity player) {
        this.player = player;
        // If the datapacks changed since last login, start the new quests that are available now.
        this.startNextQuests();
    }
    
    // Tries to initialize the quests for one aligment
    // returns false if already aligned for different alinment.
    // true for the same alignment
    public boolean initialize(Alignment alignment) {
        if (this.alignment == null) {
            QuestLine quests = QuestManager.getQuests(alignment);
            if (quests != null) {
                this.alignment = alignment;
                this.startNextQuests();
                return true;
            } else {
                return false;
            }
        } else {
            return this.alignment == alignment;
        }
    }
    
    @Nullable
    public QuestLine getQuestLine() {
        return alignment == null ? null : QuestManager.getQuests(alignment);
    }
    
    // if there are quests pending for completion, picks the first one, grants
    // rewards and returns a quest display for the user
    // If there's non, returns null.
    @Nullable
    public QuestDisplay completePendingQuest() {
        QuestLine quests = this.getQuestLine();
        if (quests != null && this.player != null && !this.pendingCompletion.isEmpty()) {
            while (!this.pendingCompletion.isEmpty()) {
                ResourceLocation id = this.pendingCompletion.remove(0);
                QuestDisplay display = this.tryComplete(this.player, quests, id);
                if (display != null) {
                    return display;
                }
            }
        }
        return null;
    }
    
    @Nullable
    private QuestDisplay tryComplete(ServerPlayerEntity player, QuestLine quests, ResourceLocation id) {
        Quest quest = quests.getQuest(id);
        if (quest != null) {
            QuestDisplay display = quest.tasks.isEmpty() ? quest.start : quest.complete;
            if (display != null) {
                for (QuestReward reward : quest.rewards) {
                    reward.grantReward(player);
                }
                return display;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public boolean hasCompleted(Quest quest) {
        return this.completedQuests.contains(quest.id);
    }
    
    // True if the task was completed
    // Can be used so fey entities only accept gifts for quests
    public <T> boolean checkComplete(TaskType<?, T> type, T element) {
        boolean success = false;
        QuestLine quests = this.getQuestLine();
        if (quests != null && this.player != null) {
            // Check each active quest if the task can be completed somewhere
            for (QuestProgress progress : this.activeQuests.values()) {
                if (progress.checkComplete(this.player, quests, type, element)) {
                    // Something was completed. Set the success flag
                    success = true;
                }
            }
            if (success) {
                // Something was completed. Move all completed quests into the
                // completed quests set and unlock new quests
                Iterator<QuestProgress> itr = this.activeQuests.values().iterator();
                while (itr.hasNext()) {
                    QuestProgress progress = itr.next();
                    if (progress.shouldBeComplete(quests)) {
                        // grant rewards and remove quest from active quests
                        this.pendingCompletion.add(progress.quest);
                        this.completedQuests.add(progress.quest);
                        itr.remove();
                    }
                }
                this.startNextQuests();
            }
        }
        return success;
    }
    
    public void startNextQuests() {
        QuestLine quests = this.getQuestLine();
        if (quests != null) {
            for (Quest newQuest : quests.getNextQuests(this.activeQuests.keySet(), this.completedQuests)) {
                if (newQuest.tasks.isEmpty()) {
                    // Empty quest will never be active but always pending for completion
                    if (!this.pendingCompletion.contains(newQuest.id)) {
                        this.pendingCompletion.add(newQuest.id);
                    }
                } else {
                    if (!activeQuests.containsKey(newQuest.id)) {
                        QuestProgress progress = new QuestProgress(newQuest.id);
                        this.activeQuests.put(newQuest.id, progress);
                    }
                }
            }
        }
    }
    
    public CompoundNBT write() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("Alignment", Alignment.optionId(this.alignment));
        ListNBT pending = new ListNBT();
        for (ResourceLocation quest : this.pendingCompletion) {
            pending.add(StringNBT.valueOf(quest.toString()));
        }
        nbt.put("Pending", pending);
        ListNBT completed = new ListNBT();
        for (ResourceLocation quest : this.completedQuests) {
            completed.add(StringNBT.valueOf(quest.toString()));
        }
        nbt.put("Completed", completed);
        CompoundNBT active = new CompoundNBT();
        for (Map.Entry<ResourceLocation, QuestProgress> entry : this.activeQuests.entrySet()) {
            active.put(entry.getKey().toString(), entry.getValue().write());
        }
        nbt.put("Active", active);
        return nbt;
    }

    public void read(CompoundNBT nbt) {
        this.alignment = Alignment.byOptionId(nbt.getString("Alignment"));
        ListNBT pending = nbt.getList("Pending", Constants.NBT.TAG_STRING);
        this.pendingCompletion.clear();
        for (int i = 0; i < pending.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(pending.getString(i));
            if (id != null) this.pendingCompletion.add(id);
        }
        ListNBT completed = nbt.getList("Completed", Constants.NBT.TAG_STRING);
        this.completedQuests.clear();
        for (int i = 0; i < completed.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(completed.getString(i));
            if (id != null) this.completedQuests.add(id);
        }
        CompoundNBT active = nbt.getCompound("Active");
        this.activeQuests.clear();
        for (String key : active.getAllKeys()) {
            ResourceLocation id = ResourceLocation.tryParse(key);
            if (id != null) {
                QuestProgress progress = new QuestProgress(id);
                progress.read(active.getCompound(key));
                this.activeQuests.put(id, progress);
            }
        }
    }
}
