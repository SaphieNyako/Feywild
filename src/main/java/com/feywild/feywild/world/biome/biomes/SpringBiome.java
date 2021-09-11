package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.world.biome.ModConfiguredSurfaceBuilders;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class SpringBiome implements BiomeType {

    public static final SpringBiome INSTANCE = new SpringBiome();

    private SpringBiome() {

    }

    @Override
    public Biome.Category category() {
        return Biome.Category.FOREST;
    }

    @Override
    public float scale() {
        return WorldGenConfig.biomes.spring.size;
    }

    @Override
    public float temperature() {
        return 0.7f;
    }

    @Override
    public float downfall() {
        return 0.8f;
    }

    @Override
    public ConfiguredSurfaceBuilder<?> surface() {
        return ModConfiguredSurfaceBuilders.SPRING_SURFACE;
    }

    @Override
    public void ambience(BiomeAmbience.Builder builder) {
        builder.waterColor(0x3f76e4);
        builder.waterFogColor(0x50533);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(BiomeMaker.calculateSkyColor(0.7f));
        builder.backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.springSoundtrack, 6000, 12000, false));
        builder.ambientParticle(new ParticleEffectAmbience(ParticleTypes.HAPPY_VILLAGER, 0.001f));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.JUNGLE_BUSH);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_WATER);
        DefaultBiomeFeatures.addExtraEmeralds(builder);
    }

    @Override
    public void overworldSpawns(MobSpawnInfo.Builder builder) {
        builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ILLUSIONER, 50, 1, 3));
        DefaultBiomeFeatures.farmAnimals(builder);
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        builder.addStructureStart(StructureFeatures.JUNGLE_TEMPLE);
    }
}
