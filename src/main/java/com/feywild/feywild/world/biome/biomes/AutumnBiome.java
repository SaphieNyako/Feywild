package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.block.trees.AutumnTree;
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
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

import java.util.Random;
import java.util.function.Supplier;

public class AutumnBiome extends BaseBiome{


    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.MOOSHROOM, 20, 4, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 20, 2, 3));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 20, 4, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.AUTUMN_PIXIE.get(), 40, 4, 4));

        //Standard
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingsBuilder);

        /* AUTUMN FEATURES*/
        biomeGenerationSettingsBuilder.addStructureStart(StructureFeatures.SWAMP_HUT);

        DefaultBiomeFeatures.addMushroomFieldVegetation(biomeGenerationSettingsBuilder);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_SWAMP);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_LARGE_FERN);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_RED_MUSHROOM);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_BROWN_MUSHROOM);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_NORMAL);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_NORMAL);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        DefaultBiomeFeatures.addSurfaceFreezing(biomeGenerationSettingsBuilder);
        return (new Biome.Builder()).precipitation(Biome.RainType.RAIN)
                .biomeCategory(Biome.Category.MUSHROOM).depth(depth).scale(scale).temperature(0.8F).downfall(0.9F)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(6388580)
                        .waterFogColor(2302743)
                        .fogColor(12638463)
                        .skyColor(getSkyColorWithTemperatureModifier(0.8F))
                        .foliageColorOverride(6975545)
                        .grassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP)
                        .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build())
                .mobSpawnSettings(mobSpawnBuilder.build()).generationSettings(biomeGenerationSettingsBuilder.build()).build();

    }
}
