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

public class AlfheimWinterBiome extends BaseBiome {

    public static final AlfheimWinterBiome INSTANCE = new AlfheimWinterBiome();

    private AlfheimWinterBiome() {

    }

    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {
        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.winterPixie, 50, 1, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.OCELOT, 5, 1, 1));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.POLAR_BEAR, 10, 1, 2));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 10, 3, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 50, 3, 5));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntities.alfPixie, 30, 4, 10));
        DefaultBiomeFeatures.commonSpawns(mobSpawnBuilder);

        //Standard
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultMonsterRoom(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultCarvers(biomeGenerationSettingsBuilder);

        /* WINTER FEATURES */
        biomeGenerationSettingsBuilder.addStructureStart(StructureFeatures.IGLOO);

        DefaultBiomeFeatures.addBerryBushes(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addSparseBerryBushes(biomeGenerationSettingsBuilder);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_SPIKE);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.PILE_SNOW);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.FOSSIL);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.JUNGLE_BUSH);

        DefaultBiomeFeatures.addDefaultGrass(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addSurfaceFreezing(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addIcebergs(biomeGenerationSettingsBuilder);

        return AlfheimBiomes.alfheimBiome()
                .depth(0.125f).scale(WorldGenConfig.biomes.winter.size)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(0x43d5ee)
                        .waterFogColor(0x041f33)
                        .fogColor(0xc0d8ff)
                        .skyColor(BiomeMaker.calculateSkyColor(0))
                        .backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.winterSoundtrack, 6000, 12000, false))
                        .ambientParticle(new ParticleEffectAmbience(ParticleTypes.ENCHANTED_HIT, 0.001F))
                        .build())
                .biomeCategory(Biome.Category.ICY).temperature(0)
                .mobSpawnSettings(mobSpawnBuilder.build())
                .generationSettings(biomeGenerationSettingsBuilder.build())
                .build();
    }
}
