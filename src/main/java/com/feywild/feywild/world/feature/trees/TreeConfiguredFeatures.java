package com.feywild.feywild.world.feature.trees;

import com.feywild.feywild.block.ModTrees;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TreeConfiguredFeatures {

    public static final Holder<PlacedFeature> SPRING_CHECKED = PlacementUtils.register("spring_checked", Objects.requireNonNull(ModTrees.springTree.getConfiguredFeature(new Random(), true)),
            PlacementUtils.filteredByBlockSurvival(ModTrees.springTree.getSapling()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SPRING_SPAWN =
            FeatureUtils.register("spring_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(SPRING_CHECKED,
                            0.01F)), SPRING_CHECKED));

    public static final Holder<PlacedFeature> SUMMER_CHECKED = PlacementUtils.register("summer_checked", Objects.requireNonNull(ModTrees.summerTree.getConfiguredFeature(new Random(), true)),
            PlacementUtils.filteredByBlockSurvival(ModTrees.summerTree.getSapling()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> SUMMER_SPAWN =
            FeatureUtils.register("summer_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(SUMMER_CHECKED,
                            0.01F)), SUMMER_CHECKED));

    public static final Holder<PlacedFeature> WINTER_CHECKED = PlacementUtils.register("winter_checked", Objects.requireNonNull(ModTrees.winterTree.getConfiguredFeature(new Random(), true)),
            PlacementUtils.filteredByBlockSurvival(ModTrees.summerTree.getSapling()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WINTER_SPAWN =
            FeatureUtils.register("winter_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WINTER_CHECKED,
                            0.01F)), WINTER_CHECKED));

    public static final Holder<PlacedFeature> AUTUMN_CHECKED = PlacementUtils.register("autumn_checked", Objects.requireNonNull(ModTrees.autumnTree.getConfiguredFeature(new Random(), true)),
            PlacementUtils.filteredByBlockSurvival(ModTrees.summerTree.getSapling()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> AUTUMN_SPAWN =
            FeatureUtils.register("autumn_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(AUTUMN_CHECKED,
                            0.01F)), AUTUMN_CHECKED));

}
