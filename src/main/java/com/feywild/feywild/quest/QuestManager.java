package com.feywild.feywild.quest;

import com.feywild.feywild.util.DatapackHelper;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class QuestManager {

    private static Map<Alignment, QuestLine> questLines = ImmutableMap.of();

    public static QuestLine getQuests(Alignment alignment) {
        return questLines.getOrDefault(alignment, QuestLine.EMPTY);
    }
    
    public static IFutureReloadListener createReloadListener() {
        return new ReloadListener<Void>() {
            @Nonnull
            @Override
            protected Void prepare(@Nonnull IResourceManager rm, @Nonnull IProfiler profiler) {
                return null;
            }

            @Override
            protected void apply(@Nonnull Void value, @Nonnull IResourceManager rm, @Nonnull IProfiler profiler) {
                EnumMap<Alignment, QuestLine> lines = new EnumMap<>(Alignment.class);
                for (Alignment alignment : Alignment.values()) {
                    lines.put(alignment, new QuestLine(DatapackHelper.loadData(rm, "feywild_quests/" + alignment.id, Quest::fromJson)));
                }
                questLines = Collections.unmodifiableMap(lines);
            }
        };
    }
}
