package com.feywild.feywild.world.biome.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class SpringBiome implements BiomeType {

    public static final SpringBiome INSTANCE = new SpringBiome();

    private SpringBiome() {

    }

    @Override
    public Biome.BiomeCategory category() {
        return Biome.BiomeCategory.FOREST;
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
    public void ambience(BiomeSpecialEffects.Builder builder) {
        builder.waterColor(0x3f76e4);
        builder.waterFogColor(0x50533);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(OverworldBiomes.calculateSkyColor(0.7f));
        builder.ambientParticle(new AmbientParticleSettings(ParticleTypes.HAPPY_VILLAGER, 0.001f));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.JUNGLE_BUSH);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_WATER);
        BiomeDefaultFeatures.addExtraEmeralds(builder);
        
        // TODO structures
//        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_SPRING_WORLD_TREE);
    }

    @Override
    public void overworldSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 50, 1, 3));
        BiomeDefaultFeatures.farmAnimals(builder);
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        // TODO structures
//        builder.addStructureStart(StructureFeatures.JUNGLE_TEMPLE);
    }
}
