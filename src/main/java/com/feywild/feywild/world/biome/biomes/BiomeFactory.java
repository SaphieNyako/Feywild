package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BiomeFactory {

    public static Biome create(BiomeEnvironment env, BiomeType type) {

        BiomeSpecialEffects.Builder ambience = env.defaultAmbience();
        type.ambience(ambience);
        env.postProcess(ambience, type);

        MobSpawnSettings.Builder spawns = env.defaultSpawns();
        type.spawns(spawns);
        env.postProcess(spawns, type);

        BiomeGenerationSettings.Builder generation = env.defaultGeneration();
        type.generation(generation);
        env.postProcess(generation, type);

        Biome.BiomeBuilder biome = env.init();
        biome.biomeCategory(type.category());
        biome.precipitation(type.rain());
        biome.temperature(type.temperature());
        biome.downfall(type.downfall());
        biome.specialEffects(ambience.build());
        biome.mobSpawnSettings(spawns.build());
        biome.generationSettings(generation.build());

        env.postProcess(biome, type);
        return biome.build();
    }
}
