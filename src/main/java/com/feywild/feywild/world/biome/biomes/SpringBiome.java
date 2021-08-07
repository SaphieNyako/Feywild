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

public class SpringBiome extends BaseBiome {

    public static final SpringBiome INSTANCE = new SpringBiome();

    private SpringBiome() {

    }

    @Override
    public Biome biomeSetup(Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {

        final BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder =
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(surfaceBuilder);

        // Mob Spawn
        final MobSpawnInfo.Builder mobSpawnBuilder = new MobSpawnInfo.Builder();

        mobSpawnBuilder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.springPixie, 40, 4, 4));
        mobSpawnBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ILLUSIONER, 50, 1, 3));
        this.addDefaultSpawns(mobSpawnBuilder);
        //Standard
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addExtraEmeralds(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultOverworldLandStructures(biomeGenerationSettingsBuilder);
        DefaultBiomeFeatures.addDefaultCarvers(biomeGenerationSettingsBuilder);

        /* SPRING FEATURES */
        biomeGenerationSettingsBuilder.addStructureStart(StructureFeatures.JUNGLE_TEMPLE);

        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);

        // biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_FOREST);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.JUNGLE_BUSH);
        biomeGenerationSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_WATER);

        return new Biome.Builder().precipitation(Biome.RainType.RAIN)
                .biomeCategory(Biome.Category.FOREST).depth(depth).scale(scale).temperature(0.7F).downfall(0.8F)
                .specialEffects((new BiomeAmbience.Builder())
                        .waterColor(0x3f76e4)
                        .waterFogColor(0x50533)
                        .fogColor(0xc0d8ff)
                        .skyColor(BiomeMaker.calculateSkyColor(0.7F))
                        .backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.springSoundtrack, 6000, 12000, true))
                        .ambientParticle(new ParticleEffectAmbience(ParticleTypes.HAPPY_VILLAGER, 0.001F))
                        .build())
                .mobSpawnSettings(mobSpawnBuilder.build()).generationSettings(biomeGenerationSettingsBuilder.build()).build();
    }
}
