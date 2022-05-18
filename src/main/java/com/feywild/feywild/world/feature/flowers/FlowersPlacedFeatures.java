package com.feywild.feywild.world.feature.flowers;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.*;

import javax.annotation.Nullable;
import java.util.List;

public class FlowersPlacedFeatures {

    //TODO generation numbers see VegetationPlacements and VegetationFeatures

    public static final Holder<PlacedFeature> SPRING_FLOWERS_PLACED =
            PlacementUtils.register("spring_flowers_placed",
                    FlowersConfiguredFeatures.SPRING_FLOWERS, CountPlacement.of(2), RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> AUTUMN_FLOWERS_PLACED =
            PlacementUtils.register("autumn_flowers_placed",
                    FlowersConfiguredFeatures.AUTUMN_FLOWERS, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> AUTUMN_BROWN_MUSHROOM_PLACED =
            PlacementUtils.register("autumn_brown_mushroom_placed",
                    VegetationFeatures.PATCH_BROWN_MUSHROOM, getMushroomPlacement(32, CountPlacement.of(1)));

    public static final Holder<PlacedFeature> AUTUMN_RED_MUSHROOM_PLACED =
            PlacementUtils.register("autumn_red_mushroom_placed",
                    VegetationFeatures.PATCH_RED_MUSHROOM, getMushroomPlacement(16, CountPlacement.of(1)));
    //TODO PUMPKIN PATCHES

    public static final Holder<PlacedFeature> SUMMER_FLOWERS_PLACED =
            PlacementUtils.register("summer_flowers_placed",
                    FlowersConfiguredFeatures.SUMMER_FLOWERS, CountPlacement.of(3), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> SUMMER_SUNFLOWERS_PLACED =
            PlacementUtils.register("summer_sunflowers_placed",
                    VegetationFeatures.PATCH_SUNFLOWER, RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    //TODO MELON PATCHES

    public static final Holder<PlacedFeature> WINTER_FLOWERS_PLACED =
            PlacementUtils.register("winter_flowers_placed",
                    FlowersConfiguredFeatures.WINTER_FLOWERS, CountPlacement.of(2), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    private static List<PlacementModifier> getMushroomPlacement(int rarity, @Nullable PlacementModifier modifier) {
        ImmutableList.Builder<PlacementModifier> builder = ImmutableList.builder();
        if (modifier != null) {
            builder.add(modifier);
        }

        if (rarity != 0) {
            builder.add(RarityFilter.onAverageOnceEvery(rarity));
        }

        builder.add(InSquarePlacement.spread());
        builder.add(PlacementUtils.HEIGHTMAP);
        builder.add(BiomeFilter.biome());
        return builder.build();
    }
}
