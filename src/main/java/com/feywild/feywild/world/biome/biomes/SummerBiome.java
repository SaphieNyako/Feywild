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
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

import java.util.function.Supplier;

public class SummerBiome extends BaseBiome{


    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.SUMMER_PIXIE.get(), 40, 4, 4));
        mobSpawnBuilder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.BEE, 20, 2, 3));


        //Standard
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withOverworldOres(biomeGenerationSettingsBuilder);

        /* SUMMER FEATURES */
        biomeGenerationSettingsBuilder.withStructure(StructureFeatures.DESERT_PYRAMID);

        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        DefaultBiomeFeatures.withTallGrass(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withWarmFlowers(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withAllForestFlowerGeneration(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withWarmFlowers(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withSavannaGrass(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.withMelonPatchesAndVines(biomeGenerationSettingsBuilder);
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);

        DefaultBiomeFeatures.withExtraGoldOre(biomeGenerationSettingsBuilder);

        /* CUSTOMISED */
        biomeGenerationSettingsBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SUMMER_TREES);

        return (new Biome.Builder()).precipitation(Biome.RainType.NONE)
                .category(Biome.Category.SAVANNA).depth(depth).scale(scale).temperature(0.9F).downfall(0.0F)
                .setEffects((new BiomeAmbience.Builder())
                        .setWaterColor(4159204)
                        .setWaterFogColor(329011)
                        .setFogColor(12638463)
                        .withSkyColor(getSkyColorWithTemperatureModifier(0.9F))
                        .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
                .withMobSpawnSettings(mobSpawnBuilder.copy()).withGenerationSettings(biomeGenerationSettingsBuilder.build()).build();




    }
}
