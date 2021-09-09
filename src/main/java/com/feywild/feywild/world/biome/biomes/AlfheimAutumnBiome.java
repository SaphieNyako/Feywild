package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.sound.ModSoundEvents;
import mythicbotany.ModEntities;
import mythicbotany.alfheim.AlfheimBiomes;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Supplier;

public class AlfheimAutumnBiome extends BaseBiome {

    public static final AlfheimAutumnBiome INSTANCE = new AlfheimAutumnBiome();

    private AlfheimAutumnBiome() {

    }

    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {
        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 20, 2, 3));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 20, 1, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.autumnPixie, 50, 1, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 50, 1, 3));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntities.alfPixie, 30, 4, 10));
        DefaultBiomeFeatures.commonSpawns(mobSpawnBuilder);

        //Standard
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addSwampClayDisk(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultCarvers(biomeGenerationSettingsBuilder);

        /* AUTUMN FEATURES*/
        biomeGenerationSettingsBuilder.addStructureStart(StructureFeatures.SWAMP_HUT);

        DefaultBiomeFeatures.addMushroomFieldVegetation(biomeGenerationSettingsBuilder);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_LARGE_FERN);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_RED_MUSHROOM);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_BROWN_MUSHROOM);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        return AlfheimBiomes.alfheimBiome()
                .depth(0.125f).scale(WorldGenConfig.biomes.autumn.size)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(0x43d5ee)
                        .waterFogColor(0x041f33)
                        .fogColor(0xc0d8ff)
                        .skyColor(BiomeMaker.calculateSkyColor(0.8F))
                        .backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.autumnSoundtrack, 6000, 12000, false))
                        .foliageColorOverride(0x6a7039)
                        .ambientParticle(new ParticleEffectAmbience(ParticleTypes.WITCH, 0.001F))
                        .grassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP).build())
                .biomeCategory(Biome.Category.MUSHROOM).temperature(0.8F)
                .mobSpawnSettings(mobSpawnBuilder.build())
                .generationSettings(biomeGenerationSettingsBuilder.build())
                .build();
    }
}
