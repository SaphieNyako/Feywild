package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.feature.ModConfiguredFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Supplier;

public class SpringBiome extends BaseBiome {

    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();


        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.SPRING_PIXIE.get(), 40, 4, 4));
        DefaultBiomeFeatures.withPassiveMobs(mobSpawnBuilder);

        //Standard
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withOverworldOres(biomeGenerationSettingsBuilder);

        /* SPRING FEATURES */
        biomeGenerationSettingsBuilder.withStructure(StructureFeatures.JUNGLE_PYRAMID);

        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_FOREST);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.JUNGLE_BUSH);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_WATER);

        /* CUSTOMISED */
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SPRING_TREES);

        return (new Biome.Builder()).precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.FOREST).depth(depth).scale(scale).temperature(0.7F).downfall(0.8F)
                .setEffects((new BiomeAmbience.Builder())
                        .setWaterColor(4159204)
                        .setWaterFogColor(329011)
                        .setFogColor(12638463)
                        .withSkyColor(getSkyColorWithTemperatureModifier(0.7F))
                        .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
                .withMobSpawnSettings(mobSpawnBuilder.copy()).withGenerationSettings(biomeGenerationSettingsBuilder.build()).build();

        //.setParticle

    }
}
