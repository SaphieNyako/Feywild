package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public interface BiomeType {

    Biome.Category category();

    default Biome.RainType rain() {return Biome.RainType.RAIN;}

    default float depth() {return 0.125f;}

    float scale();

    float temperature();

    float downfall();

    ConfiguredSurfaceBuilder<?> surface();

    default void ambience(BiomeAmbience.Builder builder) {}

    default void spawns(MobSpawnInfo.Builder builder) {}

    default void generation(BiomeGenerationSettings.Builder builder) {}

    default void overworldSpawns(MobSpawnInfo.Builder builder) {}

    default void overworldGen(BiomeGenerationSettings.Builder builder) {}
}
