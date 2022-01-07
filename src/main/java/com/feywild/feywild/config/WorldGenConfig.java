package com.feywild.feywild.config;

import com.feywild.feywild.config.data.BiomeData;
import com.feywild.feywild.config.data.OreData;
import com.feywild.feywild.config.data.StructureData;
import com.feywild.feywild.config.validator.StructureCfg;
import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.Group;

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

    @Group("Structure setting. Average distance must be greater that minimum distance.")
    public static class structures {

        @Config("The minimum and average distance between two spring world trees")
        @StructureCfg
        public static StructureData spring_world_tree = new StructureData(50, 100);

        @Config("The minimum and average distance between two summer world trees")
        @StructureCfg
        public static StructureData summer_world_tree = new StructureData(50, 100);

        @Config("The minimum and average distance between two autumn world trees")
        @StructureCfg
        public static StructureData autumn_world_tree = new StructureData(50, 100);

        @Config("The minimum and average distance between two winter world trees")
        @StructureCfg
        public static StructureData winter_world_tree = new StructureData(50, 100);

        @Config("The minimum and average distance between two bee keeps")
        @StructureCfg
        public static StructureData bee_keep = new StructureData(20, 30);

        @Config("The minimum and average distance between two libraries")
        @StructureCfg
        public static StructureData library = new StructureData(25, 35);

        @Config("The minimum and average distance between two blacksmiths")
        @StructureCfg
        public static StructureData blacksmith = new StructureData(25, 35);
    }

    public static class ores {

        @Config("Spawn settings for fey gem ore.")
        public static OreData fey_gem = new OreData(20, 5, -32, 32);
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
