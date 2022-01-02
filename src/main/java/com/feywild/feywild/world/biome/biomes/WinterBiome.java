package com.feywild.feywild.world.biome.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WinterBiome implements BiomeType {

    public static final WinterBiome INSTANCE = new WinterBiome();

    private WinterBiome() {

    }

    @Override
    public Biome.BiomeCategory category() {
        return Biome.BiomeCategory.ICY;
    }

    @Override
    public Biome.Precipitation rain() {
        return Biome.Precipitation.SNOW;
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
    public void ambience(BiomeSpecialEffects.Builder builder) {
        builder.waterColor(0x3f76e4);
        builder.waterFogColor(0x50533);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(OverworldBiomes.calculateSkyColor(0));
        builder.ambientParticle(new AmbientParticleSettings(ParticleTypes.ENCHANTED_HIT, 0.001f));
    }

    @Override
    public void spawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 10, 1, 2));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addCommonBerryBushes(builder);

        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE);
        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, VillagePlacements.PILE_SNOW_VILLAGE);
        
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.JUNGLE_BUSH);

        BiomeDefaultFeatures.addDefaultGrass(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addIcebergs(builder);
        
        // TODO structures
//        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_WINTER_WORLD_TREE);
    }

    @Override
    public void overworldSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 5, 1, 1));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 10, 3, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 50, 3, 5));
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        // TODO structures
//        builder.addStructureStart(StructureFeatures.IGLOO);
    }
}
