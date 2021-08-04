package com.feywild.feywild.data;

import com.feywild.feywild.quest.*;
import com.feywild.feywild.util.DatapackHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class QuestProvider implements IDataProvider {

    private final ModX mod;
    private final DataGenerator generator;
    private final Map<Alignment, Set<Quest>> quests = new HashMap<>();

    public QuestProvider(ModX mod, DataGenerator generator) {
        this.mod = mod;
        this.generator = generator;
    }

    public void setup() {
        
    }
    
    @Override
    public void run(@Nonnull DirectoryCache cache) throws IOException {
        this.setup();
        Set<ResourceLocation> allIds = new HashSet<>();
        for (Alignment alignment : quests.keySet()) {
            Set<ResourceLocation> ids = new HashSet<>();
            for (Quest quest : quests.get(alignment)) {
                if (allIds.contains(quest.id)) {
                    throw new IllegalStateException("Duplicate quest id: " + quest.id);
                } else {
                    allIds.add(quest.id);
                    ids.add(quest.id);
                }
            }
            for (Quest quest : quests.get(alignment)) {
                for (ResourceLocation parent : quest.parents) {
                    if (!ids.contains(parent)) {
                        throw new IllegalStateException("Reference to unknown quest: " + parent + " (in " + quest.id + ")");
                    }
                }
                IDataProvider.save(DatapackHelper.GSON, cache, quest.toJson(), this.generator.getOutputFolder().resolve("data").resolve(quest.id.getNamespace()).resolve("feywild_quests").resolve(quest.id.getPath() + ".json"));
            }
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return mod.modid + "quests";
    }
    
    public QuestBuilder quest(Alignment alignment, String name) {
        return new QuestBuilder(alignment, name);
    }
    
    public class QuestBuilder {
        
        private final Alignment alignment;
        private final ResourceLocation id;
        private final Set<String> parents;
        private int reputation;
        private QuestDisplay start;
        private QuestDisplay complete;
        private final List<QuestTask> tasks;
        private final List<QuestReward> rewards;

        public QuestBuilder(Alignment alignment, String name) {
            this.alignment = alignment;
            this.id = new ResourceLocation(mod.modid, alignment + "/" + name);
            this.parents = new HashSet<>();
            this.reputation = 5;
            this.start = new QuestDisplay(
                    new TranslationTextComponent("quest." + mod.modid + "." + alignment.id + "." + name + ".start.title"),
                    new TranslationTextComponent("quest." + mod.modid + "." + alignment.id + "." + name + ".start.description"),
                    null
            );
            this.complete = new QuestDisplay(
                    new TranslationTextComponent("quest." + mod.modid + "." + alignment.id + "." + name + ".complete.title"),
                    new TranslationTextComponent("quest." + mod.modid + "." + alignment.id + "." + name + ".complete.description"),
                    null
            );
            this.tasks = new ArrayList<>();
            this.rewards = new ArrayList<>();
        }
        
        public QuestBuilder parent(String... ids) {
            this.parents.addAll(Arrays.asList(ids));
            return this;
        }
        
        public QuestBuilder reputation(int reputation) {
            this.reputation = reputation;
            return this;
        }
        
        public QuestBuilder startDisplay(QuestDisplay display) {
            this.start = display;
            return this;
        }
        
        public QuestBuilder completeDisplay(QuestDisplay display) {
            this.complete = display;
            return this;
        }
        
        public QuestBuilder task(QuestTask... tasks) {
            this.tasks.addAll(Arrays.asList(tasks));
            return this;
        }
        
        public QuestBuilder reward(QuestReward... rewards) {
            this.rewards.addAll(Arrays.asList(rewards));
            return this;
        }
        
        public void build() {
            Set<ResourceLocation> parents = this.parents.stream().map(str -> new ResourceLocation(mod.modid, alignment.id + "/" + str)).collect(Collectors.toSet());
            Quest quest = new Quest(this.id, parents, this.reputation, this.start, this.complete, this.tasks, this.rewards);
            quests.computeIfAbsent(this.alignment, k -> new HashSet<>()).add(quest);
        }
    }
}
