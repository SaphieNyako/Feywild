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

public class WinterBiome extends BaseBiome{


    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();


        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.WINTER_PIXIE.get(), 40, 4, 4));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.OCELOT, 5, 1, 1));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.POLAR_BEAR, 10, 1, 2));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 10, 3, 4));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SNOW_GOLEM, 10, 3, 4));


        //Standard
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withOverworldOres(biomeGenerationSettingsBuilder);

        /* WINTER FEATURES */
        biomeGenerationSettingsBuilder.withStructure(StructureFeatures.IGLOO);

        DefaultBiomeFeatures.withChanceBerries(biomeGenerationSettingsBuilder);

        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_SPIKE);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.PILE_SNOW);

       // DefaultBiomeFeatures.withDefaultFlowers(biomeGenerationSettingsBuilder);


        DefaultBiomeFeatures.withBadlandsGrass(biomeGenerationSettingsBuilder);

        DefaultBiomeFeatures.withFrozenTopLayer(biomeGenerationSettingsBuilder);

        DefaultBiomeFeatures.withIcebergs(biomeGenerationSettingsBuilder);

        /* WINTER FEATURES */
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_TREES);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_FLOWERS);


      return (new Biome.Builder()).precipitation(Biome.RainType.SNOW)
              .category(Biome.Category.ICY).depth(depth).scale(scale).temperature(0.0F).downfall(0.5F).setEffects((new BiomeAmbience.Builder())
                      .setWaterColor(4159204)
                      .setWaterFogColor(329011)
                      .setFogColor(12638463)
                      .withSkyColor(getSkyColorWithTemperatureModifier(0.0F))
                      .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
              .withMobSpawnSettings(mobSpawnBuilder.copy()).withGenerationSettings(biomeGenerationSettingsBuilder.build()).build();


}
}
