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

public class AlfheimSummerBiome extends BaseBiome {

    public static final AlfheimSummerBiome INSTANCE = new AlfheimSummerBiome();

    private AlfheimSummerBiome() {

    }

    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {
        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.summerPixie, 40, 4, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.BEE, 20, 2, 3));
        mobSpawnBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.PILLAGER, 1, 2, 5));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntities.alfPixie, 50, 4, 10));
        DefaultBiomeFeatures.commonSpawns(mobSpawnBuilder);

        //Standard
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addExtraGold(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultCarvers(biomeGenerationSettingsBuilder);

        /* SUMMER FEATURES */
        biomeGenerationSettingsBuilder.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        DefaultBiomeFeatures.addSavannaGrass(biomeGenerationSettingsBuilder);
        // DefaultBiomeFeatures.addWarmFlowers(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addForestFlowers(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addSavannaExtraGrass(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addJungleExtraVegetation(biomeGenerationSettingsBuilder);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);

        return AlfheimBiomes.alfheimBiome()
                .depth(0.125f).scale(WorldGenConfig.biomes.summer.size)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(0x43d5ee)
                        .waterFogColor(0x041f33)
                        .fogColor(0xc0d8ff)
                        .skyColor(BiomeMaker.calculateSkyColor(0.9F))
                        .backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.summerSoundtrack, 6000, 12000, true))
                        .ambientParticle(new ParticleEffectAmbience(ParticleTypes.CRIT, 0.001F))
                        .build())
                .biomeCategory(Biome.Category.SAVANNA).temperature(0.9F).downfall(0)
                .mobSpawnSettings(mobSpawnBuilder.build())
                .generationSettings(biomeGenerationSettingsBuilder.build())
                .build();

    }
}
