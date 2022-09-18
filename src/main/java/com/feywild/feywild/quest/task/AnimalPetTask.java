package com.feywild.feywild.quest.task;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class AnimalPetTask extends RegistryTaskType<EntityType<?>, Entity> {
    
    public static final AnimalPetTask INSTANCE = new AnimalPetTask();

    protected AnimalPetTask() {
        //noinspection unchecked
        super("type", (Class<EntityType<?>>) (Class<?>) EntityType.class, ForgeRegistries.ENTITY_TYPES);
    }

    @Override
    public Class<Entity> testType() {
        return Entity.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayer player, EntityType<?> element, Entity match) {
        return match.getType() == element;
    }

    @Override
    public boolean repeatable() {
        return false;
    }
}
