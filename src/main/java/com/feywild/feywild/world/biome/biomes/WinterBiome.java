package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.WinterTree;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.feature.ModConfiguredFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.Random;
import java.util.function.Supplier;

public class WinterBiome extends BaseBiome{


    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();


        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.WINTER_PIXIE.get(), 40, 4, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.OCELOT, 5, 1, 1));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.POLAR_BEAR, 10, 1, 2));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 10, 3, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SNOW_GOLEM, 10, 3, 4));


        //Standard
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingsBuilder);

        /* WINTER FEATURES */
        biomeGenerationSettingsBuilder.addStructureStart(StructureFeatures.IGLOO);

        DefaultBiomeFeatures.addBerryBushes(biomeGenerationSettingsBuilder);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_SPIKE);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.PILE_SNOW);

        DefaultBiomeFeatures.addDefaultGrass(biomeGenerationSettingsBuilder);

        DefaultBiomeFeatures.addSurfaceFreezing(biomeGenerationSettingsBuilder);

        DefaultBiomeFeatures.addIcebergs(biomeGenerationSettingsBuilder);


      return (new Biome.Builder()).precipitation(Biome.RainType.SNOW)
              .biomeCategory(Biome.Category.ICY).depth(depth).scale(scale).temperature(0.0F).downfall(0.5F).specialEffects((new BiomeAmbience.Builder())
                      .waterColor(4159204)
                      .waterFogColor(329011)
                      .fogColor(12638463)
                      .skyColor(getSkyColorWithTemperatureModifier(0.0F))
                      .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build())
              .mobSpawnSettings(mobSpawnBuilder.build()).generationSettings(biomeGenerationSettingsBuilder.build()).build();


    }
}
