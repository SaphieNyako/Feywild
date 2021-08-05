package com.feywild.feywild.quest.task;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class EmptyHandKillTask extends RegistryTaskType<EntityType<?>, Entity> {
    
    public static final EmptyHandKillTask INSTANCE = new EmptyHandKillTask();
    
    private EmptyHandKillTask() {
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
