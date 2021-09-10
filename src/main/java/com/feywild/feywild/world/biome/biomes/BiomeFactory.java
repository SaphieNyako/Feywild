package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;

public class BiomeFactory {
    
    public static Biome create(BiomeEnvironment env, BiomeType type) {

        BiomeAmbience.Builder ambience = env.defaultAmbience();
        type.ambience(ambience);
        env.postProcess(ambience, type);
        
        MobSpawnInfo.Builder spawns = env.defaultSpawns();
        type.spawns(spawns);
        env.postProcess(spawns, type);

        BiomeGenerationSettings.Builder generation = env.defaultGeneration(type.surface());
        type.generation(generation);
        env.postProcess(generation, type);
        
        Biome.Builder biome = env.init();
        biome.biomeCategory(type.category());
        biome.precipitation(type.rain());
        biome.depth(type.depth());
        biome.scale(type.scale());
        biome.temperature(type.temperature());
        biome.downfall(type.downfall());
        biome.specialEffects(ambience.build());
        biome.mobSpawnSettings(spawns.build());
        biome.generationSettings(generation.build());
        
        env.postProcess(biome, type);
        return biome.build();
    }
}
