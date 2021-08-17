package com.feywild.feywild.config;

import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.validator.FloatRange;
import io.github.noeppi_noeppi.libx.config.validator.IntRange;

public class WorldGenConfig {
    
    public static class biomes {

        public static class spring {

            @Config("Weight for the spring biome to be generated")
            @IntRange(min = 0)
            public static int weight = 15;

            @Config("Size of a spring biome")
            @FloatRange(min = 0, max = 1)
            public static float size = 0.005f;
        }

        public static class summer {

            @Config("Weight for the summer biome to be generated")
            @IntRange(min = 0)
            public static int weight = 15;

            @Config("Size of a summer biome")
            @FloatRange(min = 0, max = 1)
            public static float size = 0.005f;
        }

        public static class autumn {

            @Config("Weight for the autumn biome to be generated")
            @IntRange(min = 0)
            public static int weight = 15;

            @Config("Size of a autumn biome")
            @FloatRange(min = 0, max = 1)
            public static float size = 0.005f;
        }

        public static class winter {

            @Config("Weight for the winter biome to be generated")
            @IntRange(min = 0)
            public static int weight = 15;

            @Config("Size of a winter biome")
            @FloatRange(min = 0, max = 1)
            public static float size = 0.005f;
        }
    }
    
    public static class structures {
        
        public static class spring_world_tree {
            
            @Config("The minimum distance between two spring world trees")
            @IntRange(min = 1)
            public static int minimum_distance = 50;
            
            @Config("The average distance between two spring world trees. Must be higher than minimum.")
            @IntRange(min = 1)
            public static int average_distance = 100;
        }

        public static class summer_world_tree {

            @Config("The minimum distance between two summer world trees")
            @IntRange(min = 1)
            public static int minimum_distance = 50;

            @Config("The average distance between two summer world trees. Must be higher than minimum.")
            @IntRange(min = 1)
            public static int average_distance = 100;
        }

        public static class autumn_world_tree {

            @Config("The minimum distance between two autumn world trees")
            @IntRange(min = 1)
            public static int minimum_distance = 50;

            @Config("The average distance between two autumn world trees. Must be higher than minimum.")
            @IntRange(min = 1)
            public static int average_distance = 100;
        }

        public static class winter_world_tree {

            @Config("The minimum distance between two winter world trees")
            @IntRange(min = 1)
            public static int minimum_distance = 50;

            @Config("The average distance between two winter world trees. Must be higher than minimum.")
            @IntRange(min = 1)
            public static int average_distance = 100;
        }

        public static class library {

            @Config("The minimum distance between two libraries")
            @IntRange(min = 1)
            public static int minimum_distance = 40;

            @Config("The average distance between two libraries. Must be higher than minimum.")
            @IntRange(min = 1)
            public static int average_distance = 50;
        }

        public static class blacksmith {

            @Config("The minimum distance between two blacksmiths")
            @IntRange(min = 1)
            public static int minimum_distance = 25;

            @Config("The average distance between two blacksmiths. Must be higher than minimum.")
            @IntRange(min = 1)
            public static int average_distance = 35;
        }
    }
    
    public static class ores {
        
        public static class fey_gem {
            
            @Config("Weight for fey gems to spawn")
            @IntRange(min = 0)
            public static int weight = 10;
            
            @Config("Size for fey gems vines")
            @IntRange(min = 0)
            public static int size = 5;
            
            @Config("Minimum height for fey gems vines")
            @IntRange(min = 0, max = 255)
            public static int min_height = 11;
            
            @Config("Maximum height for fey gems vines")
            @IntRange(min = 0, max = 255)
            public static int max_height = 5;
        }
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

    public static class dwarf_market{
        @Config("The time between the market refresh.")
        @IntRange(min = 1)
        public static int refresh_time = 10000;
    }
}
