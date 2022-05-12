package com.feywild.feywild.world.dimension.feywild.features;

import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

@RegisterClass(prefix = "wg", priority = -3)
public class FeywildDimensionPlacements {

    public static final Holder<PlacedFeature> autumnPumpkins = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.autumnPumpkins, List.of()));

    public static final Holder<PlacedFeature> springDandelions = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.dandelions, List.of()));

    public static final Holder<PlacedFeature> summerSunflowers = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.sunflowers, List.of()));

    public static final Holder<PlacedFeature> winterCrocus = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.crocus, List.of()));

    public static final Holder<PlacedFeature> springTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.springTrees, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(2, 0.01f, 0))));

    public static final Holder<PlacedFeature> summerTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.summerTrees, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(2, 0.01f, 0))));

    public static final Holder<PlacedFeature> autumnTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.autumnTrees, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(2, 0.01f, 0))));

    public static final Holder<PlacedFeature> winterTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.winterTrees, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(2, 0.01f, 0))));
}
