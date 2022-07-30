package com.feywild.feywild.config;

import com.feywild.feywild.config.data.AdvancedSpawns;
import com.feywild.feywild.config.data.CommonSpawns;
import org.moddingx.libx.config.Config;
import org.moddingx.libx.config.validate.IntRange;
import org.moddingx.libx.util.data.ResourceList;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class MobConfig {

    public static class spawns {

        @Config("Spring pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns spring_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                BiomeDictionary.Type.RIVER, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.MAGICAL
        ));

        @Config("Spring pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns spring_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Summer pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns summer_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                BiomeDictionary.Type.LUSH, BiomeDictionary.Type.HOT, BiomeDictionary.Type.MAGICAL
        ));

        @Config("Summer pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns summer_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Autumn pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns autumn_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL
        ));

        @Config("Autumn pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns autumn_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Winter pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns winter_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.MAGICAL
        ));

        @Config("Winter pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns winter_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Summer Bee Knight spawn weight in the overworld, minimum entities to spawn in a group.")
        public static AdvancedSpawns summer_bee_knight = new AdvancedSpawns(1, 1, 1, List.of(
                BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL
        ));

        @Config("Summer Bee Knight spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns summer_bee_knight_feywild = new CommonSpawns(30, 1, 2);

        @Config("Shroomling spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns shroomling = new AdvancedSpawns(1, 1, 1, List.of(
                BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.MAGICAL));

        @Config("Shroomling Knight spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns shroomling_feywild = new CommonSpawns(30, 1, 1);

        @Config("Dwarf Blacksmith spawn weight in the overworld, minimum entities to spawn in a group.")
        public static CommonSpawns dwarf_blacksmith = new CommonSpawns(10, 1, 1);

    }

    public static class bee_knight {

        @Config("How much reputation the player needs to have for the bee_knight to not attack him.")
        @IntRange(min = 0)
        public static int required_reputation = 35;

        @Config("What should be the aggravation range of the Bee Knights, when someone tries to steal their honey!")
        @IntRange(min = 3)
        public static int aggrevation_range = 20;
    }

    public static class dimensions {

        @Config(value = "What dimension biomes should be whitelisted for feywild mobs, note: Fey will always spawn in feywild dimension")
        public static ResourceList white_list_biomes = new ResourceList(true, builder -> {
            builder.parse("twilightforest:forest");
            builder.parse("twilightforest:dense_forest");
        });
    }
}
