package com.feywild.feywild.world.dimension.feywild;

import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.world.dimension.feywild.biome.FeywildBiomes;

public class FeywildGeneration {

    public static void setupBiomes() {
        if (CompatConfig.mythic_alfheim.overworld) {
            FeywildDimension.addBiome(FeywildBiomes.blossomingWealds, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.FLAT));
            FeywildDimension.addBiome(FeywildBiomes.goldenSeelieFields, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.FLAT));
            FeywildDimension.addBiome(FeywildBiomes.eternalFall, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.FLAT));
            FeywildDimension.addBiome(FeywildBiomes.frozenRetreat, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.FLAT));
            FeywildDimension.addBiome(FeywildBiomes.eliasianSea, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.OCEAN));
            FeywildDimension.addBiome(FeywildBiomes.goldenMountains, new BiomeConfiguration(BiomeConfiguration.BiomeTemplate.MOUNTAINS));
        }
    }
}
