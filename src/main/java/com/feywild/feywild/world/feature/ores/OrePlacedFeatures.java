package com.feywild.feywild.world.feature.ores;

import com.feywild.feywild.config.WorldGenConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class OrePlacedFeatures {

    public static final Holder<PlacedFeature> FEY_GEM_ORE_PLACED = PlacementUtils.register("fey_gem_ore_placed",
            OresConfiguredFeatures.FEY_GEM_ORE, OrePlacement.commonOrePlacement(WorldGenConfig.ores.fey_gem.weight(), // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(WorldGenConfig.ores.fey_gem.min_height()),
                            VerticalAnchor.aboveBottom(WorldGenConfig.ores.fey_gem.max_height()))));

}
