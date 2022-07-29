package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.config.data.AdvancedSpawns;
import com.feywild.feywild.config.data.CommonSpawns;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.feature.ores.OrePlacedFeatures;
import com.feywild.feywild.world.feature.trees.TreePlacedFeatures;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("unused")
public class ModWorldGeneration {

    public static final ResourceLocation SPRING_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "blossoming_wealds");
    public static final ResourceLocation SUMMER_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "golden_seelie_fields");
    public static final ResourceLocation AUTUMN_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "eternal_fall");
    public static final ResourceLocation WINTER_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "frozen_retreat");
    public static final ResourceLocation SPRING_ALFHEIM = new ResourceLocation(FeywildMod.getInstance().modid, "alfheim_spring");
    public static final ResourceLocation SUMMER_ALFHEIM = new ResourceLocation(FeywildMod.getInstance().modid, "alfheim_summer");
    public static final ResourceLocation AUTUMN_ALFHEIM = new ResourceLocation(FeywildMod.getInstance().modid, "alfheim_autumn");
    public static final ResourceLocation WINTER_ALFHEIM = new ResourceLocation(FeywildMod.getInstance().modid, "alfheim_winter");
    public static final ResourceLocation MUSHROOM_FIELDS = new ResourceLocation("minecraft", "mushroom_fields");
    public static final ResourceLocation MUSHROOM_SHORE = new ResourceLocation("minecraft", "mushroom_field_shore");

    public static final Set<ResourceLocation> IGNORED_BIOMES = ImmutableSet.of(
            new ResourceLocation("bingolobby", "lobby")
    );

    public static final Set<ResourceLocation> SEASONAL_BIOMES = ImmutableSet.of(
            SPRING_BIOME, SUMMER_BIOME, AUTUMN_BIOME, WINTER_BIOME
    );

    public static final Set<ResourceLocation> ALFHEIM_BIOMES = ImmutableSet.of(
            SPRING_ALFHEIM, SUMMER_ALFHEIM, AUTUMN_ALFHEIM, WINTER_ALFHEIM
    );

    public static void loadWorldGeneration(BiomeLoadingEvent event) {
        Random random = new Random();
        @Nullable
        ResourceLocation biomeId = event.getName();
        if (biomeId == null || IGNORED_BIOMES.contains(biomeId)) return;

        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, biomeId);
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        ores(event, biomeId, types, random);
        treePatches(event, biomeId, types, random);
        //   feywildBiomes(event, biomeId, types, random);
        mobSpawns(event, biomeId, types, random);
    }

    private static void ores(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (!CompatConfig.mythic_alfheim.locked) {
            if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(OrePlacedFeatures.FEY_GEM_ORE_PLACED);
            }
        }
        if (CompatConfig.mythic_alfheim.alfheim) {
            if (AlfheimCompat.isAlfheim(types)) {
                //event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore.getAlfheimFeature());
            }
        }
    }

    private static void treePatches(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
            if (((types.contains(BiomeDictionary.Type.PLAINS) || types.contains(BiomeDictionary.Type.RIVER) || types.contains(BiomeDictionary.Type.FOREST))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.spring) {

                event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(TreePlacedFeatures.SPRING_PLACED);
            }

            if (((types.contains(BiomeDictionary.Type.HOT) || types.contains(BiomeDictionary.Type.LUSH))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.summer) {

                event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(TreePlacedFeatures.SUMMER_PLACED);
            }

            if (((types.contains(BiomeDictionary.Type.SWAMP) || types.contains(BiomeDictionary.Type.MUSHROOM) || types.contains(BiomeDictionary.Type.SPOOKY))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.autumn) {

                event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(TreePlacedFeatures.AUTUMN_PLACED);
            }

            if (((types.contains(BiomeDictionary.Type.DEAD) || types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.winter) {

                event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(TreePlacedFeatures.WINTER_PLACED);
            }
        }
    }

    /*

    private static void feywildBiomes(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (SPRING_BIOME.equals(biomeId) || (SPRING_ALFHEIM.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.SPRING_TREES);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.SPRING_DANDELION);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.SPRING_FLOWERS);
        }

        if (SUMMER_BIOME.equals(biomeId) || (SUMMER_ALFHEIM.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.SUMMER_TREES);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.SUMMER_SUNFLOWER);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.SUMMER_FLOWERS);
        }

        if (AUTUMN_BIOME.equals(biomeId) || (AUTUMN_ALFHEIM.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.AUTUMN_TREES);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.AUTUMN_PUMPKINS);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.AUTUMN_FLOWERS);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.AUTUMN_MUSHROOMS);
        }

        if (WINTER_BIOME.equals(biomeId) || (WINTER_ALFHEIM.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.WINTER_TREES);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.WINTER_CROCUS);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacements.WINTER_FLOWERS);
        }
    } */

    private static void mobSpawns(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (CompatConfig.mythic_alfheim.locked) {
            // No dwarves, they can only be summoned using an ancient rune stone
            if (SPRING_ALFHEIM.equals(biomeId))
                addSpawn(event, ModEntityTypes.springPixie, MobCategory.CREATURE, MobConfig.spawns.spring_pixie);
            if (SUMMER_ALFHEIM.equals(biomeId)) {
                addSpawn(event, ModEntityTypes.summerPixie, MobCategory.CREATURE, MobConfig.spawns.summer_pixie);
                addSpawn(event, ModEntityTypes.beeKnight, MobCategory.CREATURE, MobConfig.spawns.summer_bee_knight);
            }
            if (AUTUMN_ALFHEIM.equals(biomeId))
                addSpawn(event, ModEntityTypes.autumnPixie, MobCategory.CREATURE, MobConfig.spawns.autumn_pixie);
            if (WINTER_ALFHEIM.equals(biomeId))
                addSpawn(event, ModEntityTypes.winterPixie, MobCategory.CREATURE, MobConfig.spawns.winter_pixie);
        } else {
            if ((types.contains(BiomeDictionary.Type.OVERWORLD) && !types.contains(BiomeDictionary.Type.OCEAN)) || MobConfig.dimensions.white_list_biomes.test(biomeId)) {
                if (!MUSHROOM_FIELDS.equals(biomeId) && !MUSHROOM_SHORE.equals(biomeId)) {
                    addSpawn(event, ModEntityTypes.dwarfBlacksmith, MobCategory.MONSTER, MobConfig.spawns.dwarf_blacksmith);
                }
                addAdvancedSpawns(event, biomeId, ModEntityTypes.springPixie, SPRING_BIOME, SPRING_ALFHEIM, types, MobConfig.spawns.spring_pixie);
                addAdvancedSpawns(event, biomeId, ModEntityTypes.summerPixie, SUMMER_BIOME, SUMMER_ALFHEIM, types, MobConfig.spawns.summer_pixie);
                addAdvancedSpawns(event, biomeId, ModEntityTypes.autumnPixie, AUTUMN_BIOME, AUTUMN_ALFHEIM, types, MobConfig.spawns.autumn_pixie);
                addAdvancedSpawns(event, biomeId, ModEntityTypes.winterPixie, WINTER_BIOME, WINTER_ALFHEIM, types, MobConfig.spawns.winter_pixie);
                addAdvancedSpawns(event, biomeId, ModEntityTypes.beeKnight, SUMMER_BIOME, SUMMER_ALFHEIM, types, MobConfig.spawns.summer_bee_knight);
                addAdvancedSpawns(event, biomeId, ModEntityTypes.shroomling, AUTUMN_BIOME, AUTUMN_ALFHEIM, types, MobConfig.spawns.shroomling);
            }
        }
    }

    private static void addAdvancedSpawns(BiomeLoadingEvent event, ResourceLocation biomeId, EntityType<?> type, ResourceLocation targetBiome, ResourceLocation targetAlfheim, Set<BiomeDictionary.Type> types, AdvancedSpawns data) {
        boolean targeted = targetBiome.equals(biomeId) || targetAlfheim.equals(biomeId);
        boolean seasonalOverworld = SEASONAL_BIOMES.contains(biomeId);
        boolean seasonalAlfheim = ALFHEIM_BIOMES.contains(biomeId);
        boolean seasonal = seasonalOverworld || seasonalAlfheim;
        boolean regularAlfheim = !seasonalAlfheim && AlfheimCompat.isAlfheim(types);
        for (BiomeDictionary.Type biomeTag : data.biomes()) {
            boolean tagged = types.contains(biomeTag);
            if ((types.contains(biomeTag) && (targeted || !seasonal)) || regularAlfheim) {
                CommonSpawns common = new CommonSpawns(targeted ? 2 * data.weight() : data.weight(), data.min(), data.max());
                addSpawn(event, type, MobCategory.CREATURE, common);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void addSpawn(BiomeLoadingEvent event, EntityType<?> type, MobCategory classification, AdvancedSpawns data) {
        addSpawn(event, type, classification, new CommonSpawns(data.weight(), data.min(), data.max()));
    }

    private static void addSpawn(BiomeLoadingEvent event, EntityType<?> type, MobCategory classification, CommonSpawns data) {
        event.getSpawns().getSpawner(classification).add(new MobSpawnSettings.SpawnerData(type, data.weight(), data.min(), data.max()));
    }
}
