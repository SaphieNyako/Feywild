package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.sound.ModSoundEvents;
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

public class SummerBiome extends BaseBiome {

    public static final SummerBiome INSTANCE = new SummerBiome();

    private SummerBiome() {

    }

    public static BiomeAmbience.Builder ambience() {
        return new BiomeAmbience.Builder()
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .fogColor(0xc0d8ff)
                .skyColor(BiomeMaker.calculateSkyColor(0.9F))
                .backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.summerSoundtrack, 6000, 12000, false))
                .ambientParticle(new ParticleEffectAmbience(ParticleTypes.CRIT, 0.001F));

    }

    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.summerPixie, 40, 1, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.BEE, 20, 2, 3));
        mobSpawnBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.PILLAGER, 10, 2, 5));
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

        DefaultBiomeFeatures.addExtraGold(biomeGenerationSettingsBuilder);

        return (new Biome.Builder()).precipitation(Biome.RainType.NONE)
                .biomeCategory(Biome.Category.SAVANNA).depth(depth).scale(scale).temperature(0.9F).downfall(0)
                .specialEffects(ambience().build())
                .mobSpawnSettings(mobSpawnBuilder.build()).generationSettings(biomeGenerationSettingsBuilder.build()).build();
    }
}
