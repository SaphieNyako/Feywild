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

public class WinterBiome implements BiomeType {

    public static final WinterBiome INSTANCE = new WinterBiome();

    private WinterBiome() {

    }

    @Override
    public Biome.Category category() {
        return Biome.Category.ICY;
    }

    @Override
    public Biome.RainType rain() {
        return Biome.RainType.SNOW;
    }

    @Override
    public float scale() {
        return WorldGenConfig.biomes.winter.size;
    }

    @Override
    public float temperature() {
        return 0;
    }

    @Override
    public float downfall() {
        return 0.5f;
    }

    @Override
    public ConfiguredSurfaceBuilder<?> surface() {
        return ModConfiguredSurfaceBuilders.WINTER_SURFACE;
    }

    @Override
    public void ambience(BiomeAmbience.Builder builder) {
        builder.waterColor(0x3f76e4);
        builder.waterFogColor(0x50533);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(BiomeMaker.calculateSkyColor(0));
        builder.backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.winterSoundtrack, 6000, 12000, false));
        builder.ambientParticle(new ParticleEffectAmbience(ParticleTypes.ENCHANTED_HIT, 0.001f));
    }

    @Override
    public void spawns(MobSpawnInfo.Builder builder) {
        builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.POLAR_BEAR, 10, 1, 2));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.addBerryBushes(builder);
        DefaultBiomeFeatures.addSparseBerryBushes(builder);

        builder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_SPIKE);
        builder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
        builder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.PILE_SNOW);
        builder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.FOSSIL);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.JUNGLE_BUSH);

        DefaultBiomeFeatures.addDefaultGrass(builder);
        DefaultBiomeFeatures.addSurfaceFreezing(builder);
        DefaultBiomeFeatures.addIcebergs(builder);
    }

    @Override
    public void overworldSpawns(MobSpawnInfo.Builder builder) {
        builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.OCELOT, 5, 1, 1));
        builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 10, 3, 4));
        builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 50, 3, 5));
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        builder.addStructureStart(StructureFeatures.IGLOO);
    }
}
