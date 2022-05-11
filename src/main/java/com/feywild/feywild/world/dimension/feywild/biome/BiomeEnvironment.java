package com.feywild.feywild.world.dimension.feywild.biome;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeEnvironment {

    public static Biome.BiomeBuilder feywildBiome() {
        return new Biome.BiomeBuilder()
                .temperature(0.9f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .precipitation(Biome.Precipitation.RAIN)
                .downfall(1);
    }

    public static MobSpawnSettings.Builder springMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder()
                .creatureGenerationProbability(0.2f);
        BiomeDefaultFeatures.farmAnimals(builder);
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.springPixie, MobConfig.spawns.spring_pixie.weight(), MobConfig.spawns.spring_pixie.min(), MobConfig.spawns.spring_pixie.max()))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 1, 1, 1));
    }

    public static BiomeSpecialEffects.Builder springAmbience() {
        return new BiomeSpecialEffects.Builder()
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.HAPPY_VILLAGER, 0.001f))
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .fogColor(0xc0d8ff)
                .skyColor(OverworldBiomes.calculateSkyColor(0.7f));

    }

    public static BiomeGenerationSettings.Builder springFeatures() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addExtraEmeralds(builder);
        return builder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_WATER)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.JUNGLE_BUSH);
    }

    public static MobSpawnSettings.Builder summerMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder()
                .creatureGenerationProbability(0.1f);
        BiomeDefaultFeatures.farmAnimals(builder);
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.summerPixie, MobConfig.spawns.summer_pixie.weight(), MobConfig.spawns.summer_pixie.min(), MobConfig.spawns.summer_pixie.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.beeKnight, MobConfig.spawns.summer_bee_knight.weight(), MobConfig.spawns.summer_bee_knight.min(), MobConfig.spawns.summer_bee_knight.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BEE, 20, 2, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 1, 2, 5));
    }

    public static BiomeSpecialEffects.Builder summerAmbience() {
        return new BiomeSpecialEffects.Builder()
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIT, 0.001f))
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .fogColor(0xc0d8ff)
                .skyColor(OverworldBiomes.calculateSkyColor(0.9f)); //TODO change sky colors

    }

    public static BiomeGenerationSettings.Builder summerFeatures() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addSavannaGrass(builder);
        BiomeDefaultFeatures.addForestFlowers(builder);
        BiomeDefaultFeatures.addSavannaExtraGrass(builder);
        BiomeDefaultFeatures.addJungleMelons(builder);
        BiomeDefaultFeatures.addJungleGrass(builder);
        BiomeDefaultFeatures.addExtraGold(builder);
        return builder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);
    }

    public static MobSpawnSettings.Builder autumnMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.autumnPixie, MobConfig.spawns.autumn_pixie.weight(), MobConfig.spawns.autumn_pixie.min(), MobConfig.spawns.autumn_pixie.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.shroomling, MobConfig.spawns.shroomling.weight(), MobConfig.spawns.shroomling.min(), MobConfig.spawns.shroomling.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 20, 2, 3))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 10, 2, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 3, 2, 5));
    }

    public static BiomeSpecialEffects.Builder autumnAmbience() {
        return new BiomeSpecialEffects.Builder()
                .foliageColorOverride(0x6a7039)
                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.WITCH, 0.001f))
                .waterColor(0x617b64)
                .waterFogColor(0x232317)
                .fogColor(0xc0d8ff)
                .skyColor(OverworldBiomes.calculateSkyColor(0.8f));

    }

    public static BiomeGenerationSettings.Builder autumnFeatures() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addMushroomFieldVegetation(builder);
        return builder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_OLD_GROWTH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_OLD_GROWTH);
    }

    public static MobSpawnSettings.Builder winterMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.winterPixie, MobConfig.spawns.winter_pixie.weight(), MobConfig.spawns.winter_pixie.min(), MobConfig.spawns.winter_pixie.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 10, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 5, 1, 1))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 10, 3, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 10, 3, 5));
    }

    public static BiomeSpecialEffects.Builder winterAmbience() {
        return new BiomeSpecialEffects.Builder()
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.001f)) //TODO change particles
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .fogColor(0xc0d8ff)
                .skyColor(OverworldBiomes.calculateSkyColor(0));

    }

    public static BiomeGenerationSettings.Builder winterFeatures() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addCommonBerryBushes(builder);
        BiomeDefaultFeatures.addDefaultGrass(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addIcebergs(builder);
        return builder
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH)
                //   .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, VillagePlacements.PILE_SNOW_VILLAGE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.JUNGLE_BUSH);
    }

    public static MobSpawnSettings.Builder oceanMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 10, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 20, 3, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 20, 3, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 20, 3, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 20, 3, 4));

    }

    public static BiomeSpecialEffects.Builder oceanAmbience() {
        return new BiomeSpecialEffects.Builder()
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.001f)) //TODO change particles
                .waterColor(0x45adf2)
                .waterFogColor(0x41633)
                .fogColor(0x45adf2)
                .skyColor(OverworldBiomes.calculateSkyColor(0.8f));

    }

    public static BiomeGenerationSettings.Builder oceanFeatures() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        return builder
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, AquaticPlacements.WARM_OCEAN_VEGETATION)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, AquaticPlacements.SEA_PICKLE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM);
    }

    public static MobSpawnSettings.Builder goldenMountainMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.beeKnight, MobConfig.spawns.summer_bee_knight.weight(), MobConfig.spawns.summer_bee_knight.min(), MobConfig.spawns.summer_bee_knight.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 20, 2, 4));

    }

    public static BiomeSpecialEffects.Builder goldenMountainAmbience() {
        return new BiomeSpecialEffects.Builder()
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIT, 0.001f))
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .fogColor(0xc0d8ff)
                .skyColor(OverworldBiomes.calculateSkyColor(0.9f)); //TODO change sky colors

    }

    public static BiomeGenerationSettings.Builder goldenMountainFeatures() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addSavannaGrass(builder);
        return builder
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, VegetationPlacements.PATCH_SUNFLOWER);
    }

}
