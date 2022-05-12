package com.feywild.feywild.world.dimension.feywild;

import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.world.dimension.feywild.biome.FeywildBiomes;

public class FeywildGeneration {

    public static void setupBiomes() {
        if (CompatConfig.mythic_alfheim.overworld) {
            FeywildDimension.addBiome(FeywildBiomes.blossomingWealds, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.HILLS));
            FeywildDimension.addBiome(FeywildBiomes.goldenSeelieFields, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.FLAT));
            FeywildDimension.addBiome(FeywildBiomes.eternalFall, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.DEFAULT));
            FeywildDimension.addBiome(FeywildBiomes.frozenRetreat, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.MODERATE));
            FeywildDimension.addBiome(FeywildBiomes.elesianRivers, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.LAKE));
            FeywildDimension.addBiome(FeywildBiomes.goldenMountains, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.MOUNTAINS));
        }
    }
}
