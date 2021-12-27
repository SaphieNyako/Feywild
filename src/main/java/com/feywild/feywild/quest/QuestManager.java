package com.feywild.feywild.quest;

import com.feywild.feywild.util.DatapackHelper;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class QuestManager {

    private static Map<Alignment, QuestLine> questLines = ImmutableMap.of();

    public static QuestLine getQuests(Alignment alignment) {
        return questLines.getOrDefault(alignment, QuestLine.EMPTY);
    }
    
    public static PreparableReloadListener createReloadListener() {
        return new SimplePreparableReloadListener<Void>() {
            @Nonnull
            @Override
            protected Void prepare(@Nonnull ResourceManager rm, @Nonnull ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(@Nonnull Void value, @Nonnull ResourceManager rm, @Nonnull ProfilerFiller profiler) {
                EnumMap<Alignment, QuestLine> lines = new EnumMap<>(Alignment.class);
                for (Alignment alignment : Alignment.values()) {
                    lines.put(alignment, new QuestLine(DatapackHelper.loadData(rm, "feywild_quests/" + alignment.id, Quest::fromJson)));
                }
                questLines = Collections.unmodifiableMap(lines);
            }
        };
    }
}
