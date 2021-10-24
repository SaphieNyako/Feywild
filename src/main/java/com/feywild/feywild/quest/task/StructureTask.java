package com.feywild.feywild.quest.task;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureTask extends RegistryTaskType<StructureFeature<?>, ResourceLocation> {

    public static final StructureTask INSTANCE = new StructureTask();

    protected StructureTask() {
        super("structure", ForgeRegistries.STRUCTURE_FEATURES);
    }

    @Override
    public Class<ResourceLocation> testType() {
        return ResourceLocation.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayer player, StructureFeature<?> element, ResourceLocation match) {
        return element.getRegistryName() != null && element.getRegistryName().equals(match);
    }

    @Override
    public boolean repeatable() {
        return false;
    }
}
