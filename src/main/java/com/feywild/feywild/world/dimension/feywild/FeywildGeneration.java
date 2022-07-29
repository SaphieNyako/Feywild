package com.feywild.feywild.world.dimension.feywild;

import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.world.dimension.feywild.biome.FeywildBiomes;
import com.feywild.feywild.world.dimension.feywild.features.FeywildDimensionPlacements;

public class FeywildGeneration {

    //TODO add more biomes, if they match a existing BiomeTemplate the other biome won't spawn...

    public static void setupBiomes() {
        if (CompatConfig.mythic_alfheim.overworld) {
            // FeywildDimension.addBiome(FeywildBiomes.blossomingRivers, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.LAKE));
            FeywildDimension.addBiome(FeywildBiomes.goldenSeelieFields, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.DEFAULT));
            FeywildDimension.addBiome(FeywildBiomes.eternalFall, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.HILLS));
            FeywildDimension.addBiome(FeywildBiomes.frozenRetreat, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.MODERATE));
            FeywildDimension.addBiome(FeywildBiomes.blossomingWealds, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.FLAT));
            //   FeywildDimension.addBiome(FeywildBiomes.goldenMountains, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.MOUNTAINS));

            FeywildDimension.addStructure(FeywildDimensionPlacements.beekeep);
            FeywildDimension.addStructure(FeywildDimensionPlacements.autumnWorldTree);
            FeywildDimension.addStructure(FeywildDimensionPlacements.winterWorldTree);
            FeywildDimension.addStructure(FeywildDimensionPlacements.springWorldTree);
            FeywildDimension.addStructure(FeywildDimensionPlacements.summerWorldTree);

        }
    }
}
