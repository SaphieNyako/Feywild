package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.block.trees.SummerTree;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.world.feature.ModConfiguredFeatures;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

import java.util.Random;
import java.util.function.Supplier;

public class SummerBiome extends BaseBiome{


    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.SUMMER_PIXIE.get(), 40, 4, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.BEE, 20, 2, 3));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE,new MobSpawnInfo.Spawners(EntityType.RAVAGER,50,2,5));
        DefaultBiomeFeatures.commonSpawns(mobSpawnBuilder);


        //Standard
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingsBuilder);

        /* SUMMER FEATURES */
        biomeGenerationSettingsBuilder.addStructureStart(StructureFeatures.DESERT_PYRAMID);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        DefaultBiomeFeatures.addSavannaGrass(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addWarmFlowers(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addForestFlowers(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addWarmFlowers(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addSavannaExtraGrass(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addJungleExtraVegetation(biomeGenerationSettingsBuilder);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);

        DefaultBiomeFeatures.addExtraGold(biomeGenerationSettingsBuilder);


        return (new Biome.Builder()).precipitation(Biome.RainType.NONE)
                .biomeCategory(Biome.Category.SAVANNA).depth(depth).scale(scale).temperature(0.9F).downfall(0.0F)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(getSkyColorWithTemperatureModifier(0.9F))
                        .backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.SUMMER_SOUNDTRACK.get(), 6000,12000, true)).build())
                .mobSpawnSettings(mobSpawnBuilder.build()).generationSettings(biomeGenerationSettingsBuilder.build()).build();




    }
}
