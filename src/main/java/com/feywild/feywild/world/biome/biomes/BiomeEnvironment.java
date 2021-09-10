package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public interface BiomeEnvironment {

    BiomeAmbience.Builder defaultAmbience();
    MobSpawnInfo.Builder defaultSpawns();
    BiomeGenerationSettings.Builder defaultGeneration(ConfiguredSurfaceBuilder<?> surface);
    default Biome.Builder init() { return new Biome.Builder(); }
    
    default void postProcess(BiomeAmbience.Builder builder, BiomeType biome) {}
    default void postProcess(MobSpawnInfo.Builder builder, BiomeType biome) {}
    default void postProcess(BiomeGenerationSettings.Builder builder, BiomeType biome) {}
    default void postProcess(Biome.Builder builder, BiomeType biome) {}
    
    BiomeEnvironment OVERWORLD = new BiomeEnvironment() {
        
        @Override
        public BiomeAmbience.Builder defaultAmbience() {
            return new BiomeAmbience.Builder();
        }

        @Override
        public MobSpawnInfo.Builder defaultSpawns() {
            MobSpawnInfo.Builder builder = new MobSpawnInfo.Builder();
            DefaultBiomeFeatures.commonSpawns(builder);
            DefaultBiomeFeatures.farmAnimals(builder);
            return builder;
        }

        @Override
        public BiomeGenerationSettings.Builder defaultGeneration(ConfiguredSurfaceBuilder<?> surface) {
            BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
            builder.surfaceBuilder(surface);
            DefaultBiomeFeatures.addDefaultUndergroundVariety(builder);
            DefaultBiomeFeatures.addDefaultOres(builder);
            DefaultBiomeFeatures.addExtraEmeralds(builder);
            DefaultBiomeFeatures.addDefaultOverworldLandStructures(builder);
            DefaultBiomeFeatures.addDefaultCarvers(builder);
            builder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);
            return builder;
        }

        @Override
        public void postProcess(MobSpawnInfo.Builder builder, BiomeType biome) {
            biome.overworldSpawns(builder);
        }

        @Override
        public void postProcess(BiomeGenerationSettings.Builder builder, BiomeType biome) {
            biome.overworldGen(builder);
        }
    };
}
