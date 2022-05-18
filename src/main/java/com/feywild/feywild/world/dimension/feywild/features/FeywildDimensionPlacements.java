package com.feywild.feywild.world.dimension.feywild.features;

import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
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
            new PlacedFeature(FeywildDimensionConfiguredFeatures.springTrees, FeywildPlacementUtils.springTrees));

    public static final Holder<PlacedFeature> summerTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.summerTrees, FeywildPlacementUtils.summerTrees));

    public static final Holder<PlacedFeature> autumnTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.autumnTrees, FeywildPlacementUtils.autumnTrees));

    public static final Holder<PlacedFeature> winterTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.winterTrees, FeywildPlacementUtils.winterTrees));
}
