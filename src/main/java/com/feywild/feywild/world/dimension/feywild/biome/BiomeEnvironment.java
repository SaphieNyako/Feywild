package com.feywild.feywild.world.dimension.feywild.biome;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.dimension.feywild.features.FeywildDimensionPlacements;
import com.feywild.feywild.world.feature.flowers.FlowersPlacedFeatures;
import com.feywild.feywild.world.feature.ores.OrePlacedFeatures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
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
                .creatureGenerationProbability(0.1f);
        BiomeDefaultFeatures.farmAnimals(builder);
        BiomeDefaultFeatures.commonSpawns(builder);
        addRiverSpawns(builder);
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.springPixie,
                        MobConfig.spawns.spring_pixie_feywild.weight(), MobConfig.spawns.spring_pixie_feywild.min(), MobConfig.spawns.spring_pixie_feywild.max()))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 10, 1, 1));
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
        BiomeDefaultFeatures.addDefaultOres(builder);
        addRiverFeatures(builder);
        return builder
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FeywildDimensionPlacements.springDandelions)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeywildDimensionPlacements.springTrees)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacedFeatures.FEY_GEM_ORE_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowersPlacedFeatures.SPRING_FLOWERS_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_WATER)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.DISK_CLAY)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.DISK_SAND);
    }

    public static MobSpawnSettings.Builder summerMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder()
                .creatureGenerationProbability(0.1f);
        BiomeDefaultFeatures.farmAnimals(builder);
        BiomeDefaultFeatures.commonSpawns(builder);
        addRiverSpawns(builder);
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.summerPixie,
                        MobConfig.spawns.summer_pixie_feywild.weight(), MobConfig.spawns.summer_pixie_feywild.min(), MobConfig.spawns.summer_pixie_feywild.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.beeKnight,
                        MobConfig.spawns.summer_bee_knight_feywild.weight(), MobConfig.spawns.summer_bee_knight_feywild.min(), MobConfig.spawns.summer_bee_knight_feywild.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BEE, 20, 2, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 1, 1, 3));
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
        BiomeDefaultFeatures.addDefaultOres(builder);
        addRiverFeatures(builder);
        return builder
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FeywildDimensionPlacements.summerSunflowers)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeywildDimensionPlacements.summerTrees)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacedFeatures.FEY_GEM_ORE_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowersPlacedFeatures.SUMMER_FLOWERS_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowersPlacedFeatures.SUMMER_SUNFLOWERS_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);
    }

    public static MobSpawnSettings.Builder autumnMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        addRiverSpawns(builder);
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                        ModEntityTypes.autumnPixie, MobConfig.spawns.autumn_pixie_feywild.weight(), MobConfig.spawns.autumn_pixie_feywild.min(), MobConfig.spawns.autumn_pixie_feywild.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(
                        ModEntityTypes.shroomling, MobConfig.spawns.shroomling_feywild.weight(), MobConfig.spawns.shroomling_feywild.min(), MobConfig.spawns.shroomling_feywild.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 20, 2, 3))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 10, 2, 3))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.MOOSHROOM, 10, 2, 3))
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
        addRiverFeatures(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);

        return builder
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FeywildDimensionPlacements.autumnPumpkins)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeywildDimensionPlacements.autumnTrees)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacedFeatures.FEY_GEM_ORE_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowersPlacedFeatures.AUTUMN_FLOWERS_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowersPlacedFeatures.AUTUMN_BROWN_MUSHROOM_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowersPlacedFeatures.AUTUMN_RED_MUSHROOM_PLACED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_OLD_GROWTH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_OLD_GROWTH);
    }

    public static MobSpawnSettings.Builder winterMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
        return builder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.winterPixie,
                        MobConfig.spawns.winter_pixie_feywild.weight(), MobConfig.spawns.winter_pixie_feywild.min(), MobConfig.spawns.winter_pixie_feywild.max()))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 10, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 5, 1, 1))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 10, 3, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 10, 3, 5));
    }

    public static BiomeSpecialEffects.Builder winterAmbience() {
        return new BiomeSpecialEffects.Builder()
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.002f))
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .fogColor(0xc0d8ff)
                .skyColor(OverworldBiomes.calculateSkyColor(0));

    }

    public static BiomeGenerationSettings.Builder winterFeatures() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addCommonBerryBushes(builder);
        BiomeDefaultFeatures.addTaigaGrass(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        BiomeDefaultFeatures.addIcebergs(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        return builder
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, FeywildDimensionPlacements.winterCrocus)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeywildDimensionPlacements.winterTrees)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FlowersPlacedFeatures.WINTER_FLOWERS_PLACED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacedFeatures.FEY_GEM_ORE_PLACED)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
    }

    public static void addRiverSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 10, 1, 2));
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 20, 3, 4));
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 20, 3, 4));
        builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 20, 3, 4));
    }

    public static void addRiverFeatures(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, AquaticPlacements.WARM_OCEAN_VEGETATION);
        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, AquaticPlacements.SEA_PICKLE);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
    }

    public static MobSpawnSettings.Builder goldenMountainMobs() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(builder);
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
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacedFeatures.FEY_GEM_ORE_PLACED)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, VegetationPlacements.PATCH_SUNFLOWER);
    }
}
