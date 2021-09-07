package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.feature.ModConfiguredFeatures;
import com.feywild.feywild.world.gen.OreType;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("unused")
public class BiomeLoader {

    public static final ResourceLocation SPRING_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "blossoming_wealds");
    public static final ResourceLocation SUMMER_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "golden_seelie_fields");
    public static final ResourceLocation AUTUMN_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "eternal_fall");
    public static final ResourceLocation WINTER_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "frozen_retreat");
    public static final ResourceLocation ALFHEIM_PLAINS = new ResourceLocation("mythicbotany", "alfheim_plains");
    public static final ResourceLocation GOLDEN_FIELDS = new ResourceLocation("mythicbotany", "golden_fields");
    public static final ResourceLocation ALFHEIM_HILLS = new ResourceLocation("mythicbotany", "alfheim_hills");
    public static final ResourceLocation DREAMWOOD_FOREST = new ResourceLocation("mythicbotany", "dreamwood_forest");
    public static final ResourceLocation MUSHROOM_FIELDS = new ResourceLocation("minecraft", "mushroom_fields");
    public static final ResourceLocation MUSHROOM_SHORE = new ResourceLocation("minecraft", "mushroom_field_shore");

    public static final Set<ResourceLocation> IGNORED_BIOMES = ImmutableSet.of(
            new ResourceLocation("bingolobby", "lobby")
    );

    public static final Set<ResourceLocation> SEASONAL_BIOMES = ImmutableSet.of(
            SPRING_BIOME, SUMMER_BIOME, AUTUMN_BIOME, WINTER_BIOME
    );

    public static final Set<ResourceLocation> ALFHEIM_BIOMES = ImmutableSet.of(
            ALFHEIM_PLAINS, GOLDEN_FIELDS, ALFHEIM_HILLS, DREAMWOOD_FOREST
    );

    public static final Set<ResourceLocation> BLACKLIST_BIOMES = MobConfig.dimensions.black_list_biomes;

    public static void loadBiome(BiomeLoadingEvent event) {
        Random random = new Random();
        @Nullable
        ResourceLocation biomeId = event.getName();
        if (biomeId == null || IGNORED_BIOMES.contains(biomeId)) return;

        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, biomeId);
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        ores(event, biomeId, types, random);
        treePatches(event, biomeId, types, random);
        feywildBiomes(event, biomeId, types, random);
        mobSpawns(event, biomeId, types, random);
        commonStructures(event, biomeId, types, random);
    }

    private static void ores(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        for (OreType ore : OreType.values()) {
            if (!event.getCategory().equals(Biome.Category.NETHER) && !event.getCategory().equals(Biome.Category.THEEND)) {
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore.getFeature());
            }
        }
    }

    private static void treePatches(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
            if (((types.contains(BiomeDictionary.Type.PLAINS) || types.contains(BiomeDictionary.Type.RIVER) || types.contains(BiomeDictionary.Type.FOREST))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.spring) {
                addLooseTrees(event, ModTrees.springTree, random, types.contains(BiomeDictionary.Type.FOREST));
            }

            if (((types.contains(BiomeDictionary.Type.HOT) || types.contains(BiomeDictionary.Type.LUSH))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.summer) {
                addLooseTrees(event, ModTrees.summerTree, random, false);
            }

            if (((types.contains(BiomeDictionary.Type.SWAMP) || types.contains(BiomeDictionary.Type.MUSHROOM) || types.contains(BiomeDictionary.Type.SPOOKY))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.autumn) {
                addLooseTrees(event, ModTrees.autumnTree, random, false);
            }

            if (((types.contains(BiomeDictionary.Type.DEAD) || types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD))
                    && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.winter) {
                addLooseTrees(event, ModTrees.winterTree, random, false);
            }
        }
    }

    private static void addLooseTrees(BiomeLoadingEvent event, BaseTree tree, Random random, boolean isForest) {
        event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, tree.getConfiguredFeature(random, true)
                .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (isForest ? 2 : 1) * WorldGenConfig.tree_patches.chance, WorldGenConfig.tree_patches.size))));
    }

    private static void feywildBiomes(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (SPRING_BIOME.equals(biomeId) || (ALFHEIM_PLAINS.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SPRING_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SPRING_DANDELION);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SPRING_FLOWERS);
            event.getGeneration().addStructureStart(ModConfiguredStructures.CONFIGURED_SPRING_WORLD_TREE);
        }

        if (SUMMER_BIOME.equals(biomeId) || (GOLDEN_FIELDS.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SUMMER_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SUMMER_SUNFLOWER);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SUMMER_WARM_FLOWERS);
            event.getGeneration().addStructureStart(ModConfiguredStructures.CONFIGURED_SUMMER_WORLD_TREE);
        }

        if (AUTUMN_BIOME.equals(biomeId) || (ALFHEIM_HILLS.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_PUMPKINS);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_SWAMP_FLOWERS);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_SMALL_MUSHROOMS);
            event.getGeneration().addStructureStart(ModConfiguredStructures.CONFIGURED_AUTUMN_WORLD_TREE);
        }

        if (WINTER_BIOME.equals(biomeId) || (DREAMWOOD_FOREST.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_CROCUS);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_FLOWERS);
            event.getGeneration().addStructureStart(ModConfiguredStructures.CONFIGURED_WINTER_WORLD_TREE);
        }
    }

    private static void mobSpawns(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (!types.contains(BiomeDictionary.Type.NETHER) && !types.contains(BiomeDictionary.Type.END) && !BLACKLIST_BIOMES.contains(biomeId) && !types.contains(BiomeDictionary.Type.OCEAN)) {
            if (!MUSHROOM_FIELDS.equals(biomeId) && !MUSHROOM_SHORE.equals(biomeId)) {
                addSpawn(event, ModEntityTypes.dwarfBlacksmith, EntityClassification.MONSTER, MobConfig.dwarf_blacksmith.weight, MobConfig.dwarf_blacksmith.min, MobConfig.dwarf_blacksmith.max);
            }
            addPixieSpawns(event, biomeId, ModEntityTypes.springPixie, SPRING_BIOME, types, MobConfig.spring_pixie.biomes, MobConfig.spring_pixie.weight, MobConfig.spring_pixie.min, MobConfig.spring_pixie.max);
            addPixieSpawns(event, biomeId, ModEntityTypes.summerPixie, SUMMER_BIOME, types, MobConfig.summer_pixie.biomes, MobConfig.summer_pixie.weight, MobConfig.summer_pixie.min, MobConfig.summer_pixie.max);
            addPixieSpawns(event, biomeId, ModEntityTypes.autumnPixie, AUTUMN_BIOME, types, MobConfig.autumn_pixie.biomes, MobConfig.autumn_pixie.weight, MobConfig.autumn_pixie.min, MobConfig.autumn_pixie.max);
            addPixieSpawns(event, biomeId, ModEntityTypes.winterPixie, WINTER_BIOME, types, MobConfig.winter_pixie.biomes, MobConfig.winter_pixie.weight, MobConfig.winter_pixie.min, MobConfig.winter_pixie.max);
        }
    }

    private static void addPixieSpawns(BiomeLoadingEvent event, ResourceLocation biomeId, EntityType<?> type, ResourceLocation targetBiome, Set<BiomeDictionary.Type> types, List<BiomeDictionary.Type> targetBiomes, int weight, int min, int max) {
        for (BiomeDictionary.Type biomeTag : targetBiomes) {
            if ((types.contains(biomeTag) && (targetBiome.equals(biomeId) || !SEASONAL_BIOMES.contains(biomeId))) || ALFHEIM_BIOMES.contains(biomeId)) {

                addSpawn(event, type, EntityClassification.CREATURE, weight, min, max);
            }
        }
    }

    private static void addSpawn(BiomeLoadingEvent event, EntityType<?> type, EntityClassification classification, int weight, int min, int max) {
        event.getSpawns().getSpawner(classification).add(new MobSpawnInfo.Spawners(type, weight, min, max));
    }

    private static void commonStructures(BiomeLoadingEvent event, ResourceLocation biomeId, Set<BiomeDictionary.Type> types, Random random) {
        if (!types.contains(BiomeDictionary.Type.OCEAN) && types.contains(BiomeDictionary.Type.OVERWORLD)) {
            if (types.contains(BiomeDictionary.Type.PLAINS)) {
                event.getGeneration().addStructureStart(ModConfiguredStructures.CONFIGURED_BLACKSMITH);
                event.getGeneration().addStructureStart(ModConfiguredStructures.CONFIGURED_LIBRARY);
            }
        }
    }
}
