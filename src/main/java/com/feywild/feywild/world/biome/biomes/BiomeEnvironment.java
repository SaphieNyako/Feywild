package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public interface BiomeEnvironment {

    BiomeEnvironment OVERWORLD = new BiomeEnvironment() {

        @Override
        public BiomeSpecialEffects.Builder defaultAmbience() {
            return new BiomeSpecialEffects.Builder();
        }

        @Override
        public MobSpawnSettings.Builder defaultSpawns() {
            MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
            BiomeDefaultFeatures.commonSpawns(builder);
            return builder;
        }

        @Override
        public BiomeGenerationSettings.Builder defaultGeneration(ConfiguredSurfaceBuilder<?> surface) {
            BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
            builder.surfaceBuilder(surface);
            BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
            BiomeDefaultFeatures.addDefaultOres(builder);
            BiomeDefaultFeatures.addDefaultOverworldLandStructures(builder);
            BiomeDefaultFeatures.addDefaultCarvers(builder);
            builder.addFeature(GenerationStep.Decoration.LAKES, Features.LAKE_WATER);
            return builder;
        }

        @Override
        public void postProcess(MobSpawnSettings.Builder builder, BiomeType biome) {
            biome.overworldSpawns(builder);
        }

        @Override
        public void postProcess(BiomeGenerationSettings.Builder builder, BiomeType biome) {
            biome.overworldGen(builder);
        }
    };

    BiomeSpecialEffects.Builder defaultAmbience();

    MobSpawnSettings.Builder defaultSpawns();

    BiomeGenerationSettings.Builder defaultGeneration(ConfiguredSurfaceBuilder<?> surface);

    default Biome.BiomeBuilder init() {return new Biome.BiomeBuilder();}

    default void postProcess(BiomeSpecialEffects.Builder builder, BiomeType biome) {}

    default void postProcess(MobSpawnSettings.Builder builder, BiomeType biome) {}

    default void postProcess(BiomeGenerationSettings.Builder builder, BiomeType biome) {}

    default void postProcess(Biome.BiomeBuilder builder, BiomeType biome) {}
}
