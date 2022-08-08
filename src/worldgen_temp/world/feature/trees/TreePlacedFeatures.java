package com.feywild.feywild.world.feature.trees;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class TreePlacedFeatures {

    public static final Holder<PlacedFeature> SPRING_PLACED = PlacementUtils.register("spring_placed",
            TreeConfiguredFeatures.SPRING_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(-3, 0.01f, 6)));

    public static final Holder<PlacedFeature> SUMMER_PLACED = PlacementUtils.register("summer_placed",
            TreeConfiguredFeatures.SUMMER_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(-3, 0.01f, 6)));

    public static final Holder<PlacedFeature> WINTER_PLACED = PlacementUtils.register("winter_placed",
            TreeConfiguredFeatures.WINTER_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(-3, 0.01f, 6)));

    public static final Holder<PlacedFeature> AUTUMN_PLACED = PlacementUtils.register("autumn_placed",
            TreeConfiguredFeatures.AUTUMN_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(-3, 0.01f, 6)));

}
