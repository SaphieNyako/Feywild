package com.feywild.feywild.world.gen;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.world.feature.ModConfiguredFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

public class ModTreeGeneration {

    public static final ResourceLocation SPRING_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "blossoming_wealds");
    public static final ResourceLocation SUMMER_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "golden_seelie_fields");
    public static final ResourceLocation AUTUMN_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "eternal_fall");
    public static final ResourceLocation WINTER_BIOME = new ResourceLocation(FeywildMod.getInstance().modid, "frozen_retreat");
    public static final ResourceLocation ALFHEIM_PLAINS = new ResourceLocation("mythicbotany", "alfheim_plains");
    public static final ResourceLocation GOLDEN_FIELDS = new ResourceLocation("mythicbotany", "golden_fields");
    public static final ResourceLocation ALFHEIM_HILLS = new ResourceLocation("mythicbotany", "alfheim_hills");
    public static final ResourceLocation DREAMWOOD_FOREST = new ResourceLocation("mythicbotany", "dreamwood_forest");
    
    public static void loadBiome(BiomeLoadingEvent event) {
        Random random = new Random();
        @Nullable
        ResourceLocation biomeId = event.getName();
        if (biomeId == null) return;

        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, biomeId);
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        //SPRING TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.PLAINS) || types.contains(BiomeDictionary.Type.RIVER) || types.contains(BiomeDictionary.Type.FOREST))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.spring) {
            addLooseTrees(event, ModTrees.springTree, random, types.contains(BiomeDictionary.Type.FOREST));
        }

        //SUMMER TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.HOT) || types.contains(BiomeDictionary.Type.LUSH))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.summer) {
            addLooseTrees(event, ModTrees.summerTree, random, false);
        }

        //AUTUMN TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.SWAMP) || types.contains(BiomeDictionary.Type.MUSHROOM) || types.contains(BiomeDictionary.Type.SPOOKY))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.autumn) {
            addLooseTrees(event, ModTrees.autumnTree, random, false);
        }

        //WINTER TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.DEAD) || types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && WorldGenConfig.tree_patches.winter) {
            addLooseTrees(event, ModTrees.winterTree, random, false);
        }


        /* BIOME DECORATION GENERATION */

        if (SPRING_BIOME.equals(biomeId) || (ALFHEIM_PLAINS.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SPRING_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SPRING_DANDELION);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SPRING_FLOWERS);
        }

        if (SUMMER_BIOME.equals(biomeId) || (GOLDEN_FIELDS.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SUMMER_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SUMMER_SUNFLOWER);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.SUMMER_WARM_FLOWERS);
        }

        if (AUTUMN_BIOME.equals(biomeId) || (ALFHEIM_HILLS.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_PUMPKINS);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_SWAMP_FLOWERS);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AUTUMN_SMALL_MUSHROOMS);
        }

        if (WINTER_BIOME.equals(biomeId) || (DREAMWOOD_FOREST.equals(biomeId) && CompatConfig.mythic_alfheim.alfheim)) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_TREES);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_CROCUS);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WINTER_FLOWERS);
        }
    }
    
    private static void addLooseTrees(BiomeLoadingEvent event, BaseTree tree, Random random, boolean isForest) {
        event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, tree.getConfiguredFeature(random, true)
                .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (isForest ? 2 : 1) * WorldGenConfig.tree_patches.chance, WorldGenConfig.tree_patches.size))));
    }
}
