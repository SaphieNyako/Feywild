package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.world.FeywildBiomes;
import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.BiomeData;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class FeyBiomes extends BiomeData {

    private final FeyTrees trees = this.resolve(FeyTrees.class);
    private final FeyPlacements placements = this.resolve(FeyPlacements.class);

    public final Holder<Biome> springBiome = this.biome(FeywildBiomes.SPRING_BIOME, 0.5f, 0.7f)
            .effects(this.effects().ambientParticle(new AmbientParticleSettings(ParticleTypes.HAPPY_VILLAGER, 0.001f)))
            .mobSpawns(this.feySpawns(true, true, Alignment.SPRING, ModEntities.mandragora)
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.botaniaPixie, 60, 2, 4))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.mandragora, 30, 1, 2))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.springTreeEnt, 5, 1, 1))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.blossomTreeEnt, 2, 1, 1))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.dwarfBlacksmith, 3, 1, 1))
                    .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 10, 1, 1))

            )
            .generation(this.feyGen(true, builder -> {
                builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, this.placements.dandelions);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.trees.springTrees);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.trees.blossomTreePatches);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.placements.springFlowers);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_WATER);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.DISK_CLAY);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.DISK_SAND);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.LUSH_CAVES_CEILING_VEGETATION);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.ROOTED_AZALEA_TREE);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.SPORE_BLOSSOM);
            }))
            .build();

    public final Holder<Biome> summerBiome = this.biome(FeywildBiomes.SUMMER_BIOME, 0.9f, 0)
            .effects(this.effects().ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIT, 0.001f)))
            .mobSpawns(this.feySpawns(false, true, Alignment.SUMMER, ModEntities.beeKnight)
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.botaniaPixie, 60, 2, 4))
                    // .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BEE, 20, 2, 3))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.summerTreeEnt, 5, 1, 1))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.blossomTreeEnt, 2, 1, 1))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.dwarfBlacksmith, 3, 1, 1))
                    .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 1, 1, 3))
            )
            .generation(this.feyGen(true, builder -> {
                BiomeDefaultFeatures.addSavannaGrass(builder);
                BiomeDefaultFeatures.addForestFlowers(builder);
                BiomeDefaultFeatures.addSavannaExtraGrass(builder);
                BiomeDefaultFeatures.addJungleMelons(builder);
                BiomeDefaultFeatures.addJungleGrass(builder);
                BiomeDefaultFeatures.addExtraGold(builder);
                builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, this.placements.sunflowers);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.trees.summerTrees);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.trees.blossomTreePatches);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.placements.summerFlowers);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.LUSH_CAVES_CEILING_VEGETATION);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.SPORE_BLOSSOM);
            }))
            .build();

    public final Holder<Biome> autumnBiome = this.biome(FeywildBiomes.AUTUMN_BIOME, 0.5f, 0.9f)
            .effects(this.effects()
                    .ambientParticle(new AmbientParticleSettings(ParticleTypes.WITCH, 0.001f))
                    .foliageColorOverride(0x6a7039)
                    .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
            )
            .mobSpawns(this.feySpawns(false, true, Alignment.AUTUMN, ModEntities.shroomling)
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.botaniaPixie, 60, 2, 4))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.shroomling, 30, 1, 2))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.autumnTreeEnt, 5, 1, 1))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.hexenTreeEnt, 2, 1, 1))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 3))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 3))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.dwarfBlacksmith, 3, 1, 1))
                    .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 3, 2, 5))
            )
            .generation(this.feyGen(true, builder -> {
                BiomeDefaultFeatures.addMushroomFieldVegetation(builder);
                builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, this.placements.autumnPumpkins);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.trees.autumnTrees);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.trees.hexenTreePatches);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.placements.autumnFlowers);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.placements.treeMushrooms);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_OLD_GROWTH);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_OLD_GROWTH);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.SPORE_BLOSSOM);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.GLOW_LICHEN);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.POINTED_DRIPSTONE);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.LARGE_DRIPSTONE);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER);
            }))
            .build();

    public final Holder<Biome> winterBiome = this.biome(FeywildBiomes.WINTER_BIOME, -0.7f, 0.5f)
            .frozen()
            .effects(this.effects().fogColor(0xabd0ef).ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.002f)))
            .mobSpawns(this.feySpawns(false, true, Alignment.WINTER)
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.botaniaPixie, 60, 2, 4))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.hexenTreeEnt, 2, 1, 1))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.winterTreeEnt, 5, 1, 1))
                    //   .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 3, 1, 2))
                    //  .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 1, 1, 1))
                    //   .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 3, 3, 4))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.dwarfBlacksmith, 3, 1, 1))
                    .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 10, 3, 5))
            )
            .generation(this.feyGen(false, builder -> {
                BiomeDefaultFeatures.addCommonBerryBushes(builder);
                BiomeDefaultFeatures.addTaigaGrass(builder);
                BiomeDefaultFeatures.addSurfaceFreezing(builder);
                BiomeDefaultFeatures.addIcebergs(builder);
                builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, this.placements.crocus);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.trees.winterTrees);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, this.placements.winterFlowers);
                builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE);
                builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.SCULK_PATCH_ANCIENT_CITY);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.FOSSIL_LOWER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.FOSSIL_UPPER);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.MONSTER_ROOM);
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.MONSTER_ROOM_DEEP);
            }))
            .build();

    public final Holder<Biome> feywildOcean = this.biome(FeywildBiomes.FEY_OCEAN, 0.5f, 0.5f)
            .mobSpawns(this.feySpawns(false, true, null)
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COD, 10, 2, 4))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 10, 2, 4))
                    .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 10, 2, 4))
            )
            .generation(this.feyGen(false, builder -> {
                // No river as river uses warm ocean feature. Add features manually
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_NORMAL);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.KELP_COLD);
            }))
            .build();

    public FeyBiomes(Properties properties) {
        super(properties);
    }

    private MobSpawnSettings.Builder feySpawns(boolean animals, boolean river, @Nullable Alignment alignment, EntityType<?>... additionalFey) {
        MobSpawnSettings.Builder builder = this.spawns()
                .creatureGenerationProbability(0.1f);
        if (animals) {
            BiomeDefaultFeatures.farmAnimals(builder);
        }
        BiomeDefaultFeatures.commonSpawns(builder);
        if (river) {
            builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 5, 1, 2));
            builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 10, 3, 4));
            builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 10, 3, 4));
            builder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 10, 3, 4));
        }
        if (alignment != null) {
            builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(alignment.fey, 60, 1, 2));
        }
        for (EntityType<?> additional : additionalFey) {
            builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(additional, 30, 1, 2));
        }
        return builder;
    }

    private BiomeGenerationSettings.Builder feyGen(boolean river, Consumer<BiomeGenerationSettings.Builder> extra) {
        BiomeGenerationSettings.Builder builder = this.generation();
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        BiomeDefaultFeatures.addExtraEmeralds(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, this.placements.feyGemOre);
        if (river) {
            builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, AquaticPlacements.WARM_OCEAN_VEGETATION);
            builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, AquaticPlacements.SEA_PICKLE);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
        }
        extra.accept(builder);
        return builder;
    }
}
