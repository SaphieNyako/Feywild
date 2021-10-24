package com.feywild.feywild.quest.task;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.server.level.ServerPlayer;
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
    public boolean checkCompleted(ServerPlayer player, EntityType<?> element, Entity match) {
        return match.getType() == element;
    }
}
