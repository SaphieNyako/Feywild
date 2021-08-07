package com.feywild.feywild.quest.task;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class KillTask extends RegistryTaskType<EntityType<?>, Entity> {
    
    public static final KillTask INSTANCE = new KillTask();
    
    private KillTask() {
        super("entity", ForgeRegistries.ENTITIES);
    }

    @Override
    public Class<Entity> testType() {
        return Entity.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayerEntity player, EntityType<?> element, Entity match) {
        return match.getType() == element;
    }
}
