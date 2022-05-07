package com.feywild.feywild.config;

import com.feywild.feywild.config.data.BiomeData;
import com.feywild.feywild.config.data.OreData;
import io.github.noeppi_noeppi.libx.config.Config;

public class WorldGenConfig {

    public static class biomes {

        @Config("Weight and size for Spring Biome: Blossoming Waelds")
        public static BiomeData spring = new BiomeData(15);

        @Config("Weight and size for Summer Biome: Golden Seelie Fields")
        public static BiomeData summer = new BiomeData(15);

        @Config("Weight and size for Autumn Biome: Eternal Fall")
        public static BiomeData autumn = new BiomeData(15);

        @Config("Weight and size for Winter Biome: Frozen Retreat")
        public static BiomeData winter = new BiomeData(15);

    }

    public static class ores {

        @Config("Spawn settings for fey gem ore.")
        public static OreData fey_gem = new OreData(20, 5, -60, 60);
    }

    public static class tree_patches {

        @Config("Whether spring tree patches should be enabled")
        public static boolean spring = true;

        @Config("Whether summer tree patches should be enabled")
        public static boolean summer = true;

        @Config("Whether autumn tree patches should be enabled")
        public static boolean autumn = true;

        @Config("Whether winter tree patches should be enabled")
        public static boolean winter = true;
    }
}
