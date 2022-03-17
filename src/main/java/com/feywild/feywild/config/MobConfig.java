package com.feywild.feywild.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.validator.IntRange;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.Set;

public class MobConfig {

    public static class shroomling {

        @Config("Shroomling spawn chance")
        @IntRange(min = 0)
        public static int weight = 15;

        @Config("Minimum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int min = 1;

        @Config("Maximum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int max = 1;

        @Config(value = "Biome types where the entity should spawn", mapper = "feywild:biome_types")
        @SuppressWarnings("config")
        public static List<BiomeDictionary.Type> biomes = ImmutableList.of(
                BiomeDictionary.Type.MUSHROOM,
                BiomeDictionary.Type.MAGICAL
        );
    }

    public static class spring_pixie {

        @Config("Spring pixie spawn chance")
        @IntRange(min = 0)
        public static int weight = 20;

        @Config("Minimum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int min = 1;

        @Config("Maximum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int max = 1;

        @Config(value = "Biome types where the entity should spawn", mapper = "feywild:biome_types")
        @SuppressWarnings("config")
        public static List<BiomeDictionary.Type> biomes = ImmutableList.of(
                BiomeDictionary.Type.RIVER, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.MAGICAL
        );
    }

    public static class summer_pixie {

        @Config("Summer pixie spawn chance")
        @IntRange(min = 0)
        public static int weight = 20;

        @Config("Minimum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int min = 1;

        @Config("Maximum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int max = 1;

        @Config(value = "Biome types where the entity should spawn", mapper = "feywild:biome_types")
        @SuppressWarnings("config")
        public static List<BiomeDictionary.Type> biomes = ImmutableList.of(
                BiomeDictionary.Type.LUSH, BiomeDictionary.Type.HOT, BiomeDictionary.Type.MAGICAL
        );
    }

    public static class summer_bee_knight {

        @Config("How much reputation the player needs to have for the guards to not attack him.")
        @IntRange(min = 0)
        public static int required_reputation = 35;

        @Config("What should be the aggrevation range of the Bee Knights, when someone tries to steal their honey!")
        @IntRange(min = 3)
        public static int aggrevation_range = 20;

        @Config("Bee knight spawn chance")
        @IntRange(min = 0)
        public static int weight = 25;

        @Config("Minimum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int min = 1;

        @Config("Maximum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int max = 2;

    }

    public static class autumn_pixie {

        @Config("Autumn pixie spawn chance")
        @IntRange(min = 0)
        public static int weight = 20;

        @Config("Minimum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int min = 1;

        @Config("Maximum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int max = 1;

        @Config(value = "Biome types where the entity should spawn", mapper = "feywild:biome_types")
        @SuppressWarnings("config")
        public static List<BiomeDictionary.Type> biomes = ImmutableList.of(
                BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL
        );
    }

    public static class winter_pixie {

        @Config("Winter pixie spawn chance")
        @IntRange(min = 0)
        public static int weight = 20;

        @Config("Minimum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int min = 1;

        @Config("Maximum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int max = 1;

        @Config(value = "Biome types where the entity should spawn", mapper = "feywild:biome_types")
        @SuppressWarnings("config")
        public static List<BiomeDictionary.Type> biomes = ImmutableList.of(
                BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.MAGICAL
        );
    }

    public static class dwarf_blacksmith {

        @Config("Dwarf blacksmith spawn chance")
        @IntRange(min = 0)
        public static int weight = 5;

        @Config("Minimum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int min = 1;

        @Config("Maximum entities to spawn in a group group")
        @IntRange(min = 1)
        public static int max = 1;
    }

    public static class dimensions {

        @Config(value = "What dimension biomes should be whitelisted for Feywild mobs", mapper = "feywild:resource_location")
        @SuppressWarnings("config")
        public static Set<ResourceLocation> white_list_biomes = ImmutableSet.of(
                new ResourceLocation("twilightforest", "forest"),
                new ResourceLocation("twilightforest", "dense_forest"),
                new ResourceLocation("twilightforest", "dense_mushroom_forest"),
                new ResourceLocation("twilightforest", "enchanted_forest")
        );

    }
}
