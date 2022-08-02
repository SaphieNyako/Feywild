package com.feywild.feywild.config;

import com.feywild.feywild.config.data.AdvancedSpawns;
import com.feywild.feywild.config.data.CommonSpawns;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
import org.moddingx.libx.annotation.config.RegisterConfig;
import org.moddingx.libx.config.Config;
import org.moddingx.libx.config.validate.IntRange;
import org.moddingx.libx.util.data.ResourceList;

import java.util.List;

@RegisterConfig("mobs")
public class MobConfig {

    public static class spawns {

        @Config("Spring pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns spring_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                BiomeTags.IS_RIVER, BiomeTags.IS_FOREST, Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_MAGICAL
        ));

        @Config("Spring pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns spring_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Summer pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns summer_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                Tags.Biomes.IS_LUSH, Tags.Biomes.IS_HOT, Tags.Biomes.IS_MAGICAL
        ));

        @Config("Summer pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns summer_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Autumn pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns autumn_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                Tags.Biomes.IS_SWAMP, Tags.Biomes.IS_MUSHROOM, Tags.Biomes.IS_SPOOKY, BiomeTags.IS_FOREST, Tags.Biomes.IS_MAGICAL
        ));

        @Config("Autumn pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns autumn_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Winter pixie spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns winter_pixie = new AdvancedSpawns(3, 1, 1, List.of(
                Tags.Biomes.IS_DEAD, Tags.Biomes.IS_SNOWY, Tags.Biomes.IS_COLD, Tags.Biomes.IS_MAGICAL
        ));

        @Config("Winter pixie spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns winter_pixie_feywild = new CommonSpawns(50, 1, 2);

        @Config("Summer Bee Knight spawn weight in the overworld, minimum entities to spawn in a group.")
        public static AdvancedSpawns summer_bee_knight = new AdvancedSpawns(1, 1, 1, List.of(
                Tags.Biomes.IS_HOT
        ));

        @Config("Summer Bee Knight spawn weight in the feywild, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static CommonSpawns summer_bee_knight_feywild = new CommonSpawns(30, 1, 2);

        @Config("Shroomling spawn weight in the overworld, minimum entities to spawn in a group and biomes types where entity should spawn.")
        public static AdvancedSpawns shroomling = new AdvancedSpawns(1, 1, 1, List.of(
                Tags.Biomes.IS_MUSHROOM
        ));

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

        @Config({
                "What dimension biomes should be whitelisted for feywild mobs.",
                "Note: Fey will always spawn in feywild dimension"
        })
        public static ResourceList feywild_entity_biomes = new ResourceList(true, builder -> {
            builder.parse("twilightforest:forest");
            builder.parse("twilightforest:dense_forest");
        });
    }
}
