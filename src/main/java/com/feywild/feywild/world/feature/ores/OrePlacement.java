package com.feywild.feywild.world.feature.ores;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class OrePlacement {

    public static List<PlacementModifier> orePlacement(PlacementModifier modifierA, PlacementModifier modifierB) {
        return List.of(modifierA, InSquarePlacement.spread(), modifierB, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int weight, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(weight), modifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int weight, PlacementModifier modifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(weight), modifier);
    }
}
