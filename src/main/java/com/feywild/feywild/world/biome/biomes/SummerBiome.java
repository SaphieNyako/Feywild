package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class SummerBiome implements BiomeType {

    public static final SummerBiome INSTANCE = new SummerBiome();

    private SummerBiome() {

    }

    @Override
    public Biome.BiomeCategory category() {
        return Biome.BiomeCategory.SAVANNA;
    }

    @Override
    public Biome.Precipitation rain() {
        return Biome.Precipitation.NONE;
    }

    @Override
    public float temperature() {
        return 0.9f;
    }

    @Override
    public float downfall() {
        return 0;
    }
    
    @Override
    public void ambience(BiomeSpecialEffects.Builder builder) {
        builder.waterColor(0x3f76e4);
        builder.waterFogColor(0x50533);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(OverworldBiomes.calculateSkyColor(0.9f));
        builder.ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIT, 0.001f));
    }

    @Override
    public void spawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BEE, 20, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.beeKnight,
                MobConfig.spawns.summer_bee_knight.weight(), MobConfig.spawns.summer_bee_knight.min(), MobConfig.spawns.summer_bee_knight.max()));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addSavannaGrass(builder);
        BiomeDefaultFeatures.addForestFlowers(builder);
        BiomeDefaultFeatures.addSavannaExtraGrass(builder);
        BiomeDefaultFeatures.addJungleMelons(builder);
        BiomeDefaultFeatures.addJungleGrass(builder);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);
        BiomeDefaultFeatures.addExtraGold(builder);
        
        // TODO structures
        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_BEEKEEP);
        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_SUMMER_WORLD_TREE);
    }

    @Override
    public void overworldSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 3, 2, 5));
        BiomeDefaultFeatures.farmAnimals(builder);
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        // TODO structures
        builder.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
    }
}
