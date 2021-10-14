package com.feywild.feywild.quest.task;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureTask extends RegistryTaskType<Structure<?>, ResourceLocation> {

    public static final StructureTask INSTANCE = new StructureTask();

    protected StructureTask() {
        super("structure", ForgeRegistries.STRUCTURE_FEATURES);
    }

    @Override
    public Class<ResourceLocation> testType() {
        return ResourceLocation.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayerEntity player, Structure<?> element, ResourceLocation match) {
        return element.getRegistryName() != null && element.getRegistryName().equals(match);
    }

    @Override
    public boolean repeatable() {
        return false;
    }
}
