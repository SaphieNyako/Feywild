package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.feature.ModConfiguredFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

import java.util.function.Supplier;

public class AutumnBiome extends BaseBiome{


    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.MOOSHROOM, 20, 4, 4));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 20, 2, 3));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 20, 4, 4));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.AUTUMN_PIXIE.get(), 40, 4, 4));

        //Standard
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withOverworldOres(biomeGenerationSettingsBuilder);

        /* AUTUMN FEATURES*/
        biomeGenerationSettingsBuilder.withStructure(StructureFeatures.SWAMP_HUT);

        DefaultBiomeFeatures.withMushroomBiomeVegetation(biomeGenerationSettingsBuilder);

        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_SWAMP);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_LARGE_FERN);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_RED_MUSHROOM);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_BROWN_MUSHROOM);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_NORMAL);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_NORMAL);

        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        /* CUSTOMISED */
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_PUMPKINS);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_TREES);


        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_NORMAL);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_NORMAL);


        DefaultBiomeFeatures.withFrozenTopLayer(biomeGenerationSettingsBuilder);
        return (new Biome.Builder()).precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.MUSHROOM).depth(depth).scale(scale).temperature(0.8F).downfall(0.9F)
                .setEffects((new BiomeAmbience.Builder())
                        .setWaterColor(6388580)
                        .setWaterFogColor(2302743)
                        .setFogColor(12638463)
                        .withSkyColor(getSkyColorWithTemperatureModifier(0.8F))
                        .withFoliageColor(6975545)
                        .withGrassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP)
                        .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
                .withMobSpawnSettings(mobSpawnBuilder.copy()).withGenerationSettings(biomeGenerationSettingsBuilder.build()).build();

    }
}
