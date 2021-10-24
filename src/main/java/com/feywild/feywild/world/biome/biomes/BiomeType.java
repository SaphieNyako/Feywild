package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;

public interface BiomeType {

    Biome.BiomeCategory category();

    default Biome.Precipitation rain() {return Biome.Precipitation.RAIN;}

    default float depth() {return 0.125f;}

    float scale();

    float temperature();

    float downfall();

    ConfiguredSurfaceBuilder<?> surface();

    default void ambience(BiomeSpecialEffects.Builder builder) {}

    default void spawns(MobSpawnSettings.Builder builder) {}

    default void generation(BiomeGenerationSettings.Builder builder) {}

    default void overworldSpawns(MobSpawnSettings.Builder builder) {}

    default void overworldGen(BiomeGenerationSettings.Builder builder) {}
}
