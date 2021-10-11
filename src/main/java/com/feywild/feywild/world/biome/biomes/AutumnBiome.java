package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.world.biome.ModConfiguredSurfaceBuilders;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class AutumnBiome implements BiomeType {

    public static final AutumnBiome INSTANCE = new AutumnBiome();

    private AutumnBiome() {

    }

    @Override
    public Biome.Category category() {
        return Biome.Category.MUSHROOM;
    }

    @Override
    public float scale() {
        return WorldGenConfig.biomes.autumn.size;
    }

    @Override
    public float temperature() {
        return 0.8f;
    }

    @Override
    public float downfall() {
        return 0.9f;
    }

    @Override
    public ConfiguredSurfaceBuilder<?> surface() {
        return ModConfiguredSurfaceBuilders.AUTUMN_SURFACE;
    }

    @Override
    public void ambience(BiomeAmbience.Builder builder) {
        builder.waterColor(0x617b64);
        builder.waterFogColor(0x232317);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(BiomeMaker.calculateSkyColor(0.8f));
        builder.backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.autumnSoundtrack, 6000, 12000, false));
        builder.foliageColorOverride(0x6a7039);
        builder.ambientParticle(new ParticleEffectAmbience(ParticleTypes.WITCH, 0.001f));
        builder.grassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP);
    }

    @Override
    public void spawns(MobSpawnInfo.Builder builder) {
        builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 20, 2, 3));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_LARGE_FERN);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_RED_MUSHROOM);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_BROWN_MUSHROOM);
        DefaultBiomeFeatures.addMushroomFieldVegetation(builder);
        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_AUTUMN_WORLD_TREE);
    }

    @Override
    public void overworldSpawns(MobSpawnInfo.Builder builder) {
        builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 20, 4, 4));
        builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 50, 1, 3));
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        builder.addStructureStart(StructureFeatures.SWAMP_HUT);
    }
}
