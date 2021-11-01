package com.feywild.feywild.quest.task;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureTask extends RegistryTaskType<StructureFeature<?>, StructureFeature<?>> {

    public static final StructureTask INSTANCE = new StructureTask();

    protected StructureTask() {
        super("structure", ForgeRegistries.STRUCTURE_FEATURES);
    }

    @Override
    public Class<StructureFeature<?>> testType() {
        //noinspection unchecked
        return (Class<StructureFeature<?>>) (Class<?>) StructureFeature.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayer player, StructureFeature<?> element, StructureFeature<?> match) {
        return element == match;
    }

    @Override
    public boolean repeatable() {
        return false;
    }
}
