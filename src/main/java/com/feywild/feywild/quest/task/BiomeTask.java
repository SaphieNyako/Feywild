package com.feywild.feywild.quest.task;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

// Test type must be a resource locations as biomes are handled in a weird way
public class BiomeTask extends RegistryTaskType<Biome, ResourceLocation> {

    public static final BiomeTask INSTANCE = new BiomeTask();

    private BiomeTask() {
        super("biome", ForgeRegistries.BIOMES);
    }

    @Override
    public Class<ResourceLocation> testType() {
        return ResourceLocation.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayer player, Biome element, ResourceLocation match) {
        return element.getRegistryName() != null && element.getRegistryName().equals(match);
    }

    @Override
    public boolean repeatable() {
        return false;
    }
}

