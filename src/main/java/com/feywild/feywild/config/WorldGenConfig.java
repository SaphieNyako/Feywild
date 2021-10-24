package com.feywild.feywild.config;

import com.feywild.feywild.config.data.BiomeData;
import com.feywild.feywild.config.data.OreData;
import com.feywild.feywild.config.data.StructureData;
import com.feywild.feywild.config.validator.StructureCfg;
import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.Group;
import io.github.noeppi_noeppi.libx.config.validator.FloatRange;
import io.github.noeppi_noeppi.libx.config.validator.IntRange;

public class WorldGenConfig {

    public static class biomes {

        public static BiomeData spring = new BiomeData(15, 0.005f);
        public static BiomeData summer = new BiomeData(15, 0.005f);
        public static BiomeData autumn = new BiomeData(15, 0.005f);
        public static BiomeData winter = new BiomeData(15, 0.005f);
    }

    @Group("Structure setting. Avergae distance must be greater that minimum distance.")
    public static class structures {

        @StructureCfg
        public static StructureData spring_world_tree = new StructureData(50, 100);
        
        @StructureCfg
        public static StructureData summer_world_tree = new StructureData(50, 100);
        
        @StructureCfg
        public static StructureData autumn_world_tree = new StructureData(50, 100);
        
        @StructureCfg
        public static StructureData winter_world_tree = new StructureData(50, 100);
        
        @StructureCfg
        public static StructureData bee_keep = new StructureData(20, 30);
        
        @StructureCfg
        public static StructureData library = new StructureData(25, 35);
        
        @StructureCfg
        public static StructureData blacksmith = new StructureData(25, 35);
    }

    public static class ores {

        @Config("Spawn settings for fey gem ore.")
        public static OreData fey_gem = new OreData(10, 5, 11, 45);
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

        @Config("Tree patch spawn chance.")
        @FloatRange(min = 0, max = 1)
        public static float chance = 0.01f;

        @Config("Tree patch spawn size.")
        @IntRange(min = 1)
        public static int size = 3;
    }
}
