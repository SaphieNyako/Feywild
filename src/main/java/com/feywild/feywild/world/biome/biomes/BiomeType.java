package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public interface BiomeType {

    Biome.BiomeCategory category();

    default Biome.Precipitation rain() {return Biome.Precipitation.RAIN;}

    float temperature();

    float downfall();

    default void ambience(BiomeSpecialEffects.Builder builder) {}

    default void spawns(MobSpawnSettings.Builder builder) {}

    default void generation(BiomeGenerationSettings.Builder builder) {}

    default void overworldSpawns(MobSpawnSettings.Builder builder) {}

    default void overworldGen(BiomeGenerationSettings.Builder builder) {}
}
