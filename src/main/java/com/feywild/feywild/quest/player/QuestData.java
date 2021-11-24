package com.feywild.feywild.quest.player;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.compat.MineMentionCompat;
import com.feywild.feywild.events.QuestCompletionEvent;
import com.feywild.feywild.quest.*;
import com.feywild.feywild.quest.task.TaskType;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class QuestData {

    // Quests are completed but the player did not interact with the fey since then
    private final List<ResourceLocation> pendingCompletion = new ArrayList<>();
    private final Set<ResourceLocation> completedQuests = new HashSet<>();
    private final Map<ResourceLocation, QuestProgress> activeQuests = new HashMap<>();
    @Nullable
    private ServerPlayer player;
    @Nullable
    private Alignment alignment;
    @Nullable // Before the player accepted the first quest, this will hold the alignment it would have
    private Alignment pendingAlignment;
    private int reputation;

    public static QuestData get(ServerPlayer player) {
        // Capability should always be there.
        // If not print a warning and get default instance
        return player.getCapability(CapabilityQuests.QUESTS).orElseGet(() -> {
            FeywildMod.getInstance().logger.warn("Quest Data capability not present on player: " + player);
            return new QuestData();
        });
    }

    // Called when the capability is attached to the player
    public void attach(ServerPlayer player) {
        this.player = player;
        // If the datapacks changed since last login, start the new quests that are available now.
        this.startNextQuests();
    }

    public boolean canComplete(Alignment alignment) {
        return this.alignment == alignment;
    }

    @Nullable
    public QuestDisplay initialize(Alignment alignment) {
        if (this.alignment == null) {
            QuestLine quests = QuestManager.getQuests(alignment);
            Quest rootQuest = quests == null ? null : quests.getQuest(new ResourceLocation(FeywildMod.getInstance().modid, "root"));
            if (rootQuest != null) {
                this.pendingAlignment = alignment;
                return rootQuest.start;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void acceptAlignment() {
        if (this.pendingAlignment != null && this.alignment == null && this.player != null) {
            this.alignment = this.pendingAlignment;
            this.pendingAlignment = null;
            this.reputation = 0;
            this.pendingCompletion.clear();
            this.completedQuests.clear();
            this.activeQuests.clear();
            QuestLine quests = QuestManager.getQuests(this.alignment);
            Quest rootQuest = quests == null ? null : quests.getQuest(new ResourceLocation(FeywildMod.getInstance().modid, "root"));
            if (rootQuest != null && rootQuest.tasks.isEmpty()) {
                for (QuestReward reward : rootQuest.rewards) {
                    reward.grantReward(this.player);
                }
                this.reputation += rootQuest.reputation;
                this.completedQuests.add(rootQuest.id);
            }
            this.startNextQuests();
            if (ModList.get().isLoaded("minemention")) {
                MineMentionCompat.availabilityChange(this.player);
            }
        }
    }

    public void denyAlignment() {
        this.pendingAlignment = null;
    }

    public boolean reset() {
        Alignment oldAlignment = this.alignment;
        this.alignment = null;
        this.reputation = 0;
        this.pendingCompletion.clear();
        this.completedQuests.clear();
        this.activeQuests.clear();
        if (ModList.get().isLoaded("minemention")) {
            MineMentionCompat.availabilityChange(this.player);
        }
        return oldAlignment != null;
    }

    @Nullable
    public QuestLine getQuestLine() {
        return this.alignment == null ? null : QuestManager.getQuests(this.alignment);
    }

    public int getReputation() {return this.alignment == null ? 0 : this.reputation;}

    @Nullable
    public Alignment getAlignment() {
        return this.alignment;
    }

    @Nullable
    public QuestDisplay getActiveQuestDisplay(ResourceLocation id) {
        QuestLine quests = this.getQuestLine();
        if (quests != null && this.player != null && this.activeQuests.containsKey(id)) {
            Quest quest = quests.getQuest(id);
            if (quest != null) {
                return quest.start;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<SelectableQuest> getActiveQuests() {
        QuestLine quests = this.getQuestLine();
        if (quests != null && this.player != null) {
            ImmutableList.Builder<SelectableQuest> list = ImmutableList.builder();
            for (QuestProgress progress : this.activeQuests.values().stream().sorted(Comparator.comparing(q -> q.quest)).collect(Collectors.toList())) {
                Quest quest = quests.getQuest(progress.quest);
                if (quest != null) {
                    list.add(new SelectableQuest(quest.id, quest.icon, quest.start));
                }
            }
            return list.build();
        } else {
            return ImmutableList.of();
        }
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
    private QuestDisplay tryComplete(ServerPlayer player, QuestLine quests, ResourceLocation id) {
        Quest quest = quests.getQuest(id);
        if (quest != null) {
            QuestDisplay display = quest.tasks.isEmpty() ? quest.start : quest.complete;
            if (display != null) {
                for (QuestReward reward : quest.rewards) {
                    reward.grantReward(player);
                }
                this.reputation += quest.reputation;
                MinecraftForge.EVENT_BUS.post(new QuestCompletionEvent(this.player, quest, this.reputation));
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
            String msgToDisplay = null;
            // Check each active quest if the task can be completed somewhere
            for (QuestProgress progress : this.activeQuests.values()) {
                String progressMsg = progress.checkComplete(this.player, quests, type, element);
                if (progressMsg != null) {
                    // Something was completed. Set the success flag
                    success = true;
                    if (msgToDisplay == null) msgToDisplay = progressMsg;
                }
            }
            if (success) {
                triggerAfterComplete(quests, msgToDisplay);
            }
        }
        return success;
    }

    public <T, X> List<CompletableTaskInfo<T, X>> getAllCurrentTasks(TaskType<T, X> type) {
        ImmutableList.Builder<CompletableTaskInfo<T, X>> list = ImmutableList.builder();
        QuestLine quests = this.getQuestLine();
        if (quests != null && this.player != null) {
            for (QuestProgress progress : this.activeQuests.values()) {
                return progress.getQuestElements(player, quests, type)
                        .map(elem -> new CompletableTaskInfo<>(type, elem, element -> {
                            String progressMsg = progress.checkComplete(this.player, quests, type, element);
                            if (progressMsg != null) {
                                // Something was completed.
                                triggerAfterComplete(quests, progressMsg);
                            }
                        })).toList();
            }
        }
        return list.build();
    }

    private void triggerAfterComplete(QuestLine quests, String msgToDisplay) {
        // Something was completed. Move all completed quests into the
        // completed quests set and unlock new quests
        boolean shouldNotify = false;
        Iterator<QuestProgress> itr = this.activeQuests.values().iterator();
        while (itr.hasNext()) {
            QuestProgress progress = itr.next();
            if (progress.shouldBeComplete(quests)) {
                // grant rewards and remove quest from active quests
                this.pendingCompletion.add(progress.quest);
                this.completedQuests.add(progress.quest);
                shouldNotify = true;
                itr.remove();
            }
        }
        if (this.player != null) {
            if (shouldNotify) {
                this.player.displayClientMessage(new TranslatableComponent("message.feywild.quest_completion"), true);
            } else {
                this.player.displayClientMessage(new TextComponent(msgToDisplay), true);
            }
        }
        this.startNextQuests();
    }

    public void startNextQuests() {
        QuestLine quests = this.getQuestLine();
        boolean hasEmptyQuests = false;
        if (quests != null) {
            for (Quest newQuest : quests.getNextQuests(this.activeQuests.keySet(), this.completedQuests)) {
                if (newQuest.tasks.isEmpty()) {
                    // Empty quest will never be active but always pending for completion
                    if (!this.pendingCompletion.contains(newQuest.id)) {
                        this.pendingCompletion.add(newQuest.id);
                        this.completedQuests.add(newQuest.id);
                        hasEmptyQuests = true;
                    }
                } else {
                    if (!this.activeQuests.containsKey(newQuest.id)) {
                        QuestProgress progress = new QuestProgress(newQuest.id);
                        this.activeQuests.put(newQuest.id, progress);
                    }
                }
            }
        }
        if (hasEmptyQuests) {
            // We have quests that instantly got pending for completion
            // so we need to start their children quests now.
            startNextQuests();
        }
    }

    public CompoundTag write() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Alignment", Alignment.optionId(this.alignment));
        nbt.putInt("Reputation", reputation);
        ListTag pending = new ListTag();
        for (ResourceLocation quest : this.pendingCompletion) {
            pending.add(StringTag.valueOf(quest.toString()));
        }
        nbt.put("Pending", pending);
        ListTag completed = new ListTag();
        for (ResourceLocation quest : this.completedQuests) {
            completed.add(StringTag.valueOf(quest.toString()));
        }
        nbt.put("Completed", completed);
        CompoundTag active = new CompoundTag();
        for (Map.Entry<ResourceLocation, QuestProgress> entry : this.activeQuests.entrySet()) {
            active.put(entry.getKey().toString(), entry.getValue().write());
        }
        nbt.put("Active", active);
        return nbt;
    }

    public void read(CompoundTag nbt) {
        this.alignment = Alignment.byOptionId(nbt.getString("Alignment"));
        this.reputation = nbt.getInt("Reputation");
        ListTag pending = nbt.getList("Pending", Constants.NBT.TAG_STRING);
        this.pendingCompletion.clear();
        for (int i = 0; i < pending.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(pending.getString(i));
            if (id != null) this.pendingCompletion.add(id);
        }
        ListTag completed = nbt.getList("Completed", Constants.NBT.TAG_STRING);
        this.completedQuests.clear();
        for (int i = 0; i < completed.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(completed.getString(i));
            if (id != null) this.completedQuests.add(id);
        }
        CompoundTag active = nbt.getCompound("Active");
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
