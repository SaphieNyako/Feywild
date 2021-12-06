package com.feywild.feywild.config;

import com.feywild.feywild.config.data.CommonSpawns;
import com.feywild.feywild.config.data.PixieSpawns;
import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.validator.IntRange;
import io.github.noeppi_noeppi.libx.util.ResourceList;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class MobConfig {

    public static class spawns {

        public static PixieSpawns spring_pixie = new PixieSpawns(20, 1, 1, List.of(
                BiomeDictionary.Type.RIVER, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.PLAINS,
                BiomeDictionary.Type.MAGICAL
        ));

        public static PixieSpawns summer_pixie = new PixieSpawns(20, 1, 1, List.of(
                BiomeDictionary.Type.LUSH, BiomeDictionary.Type.HOT, BiomeDictionary.Type.MAGICAL
        ));

        public static PixieSpawns autumn_pixie = new PixieSpawns(20, 1, 1, List.of(
                BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.SPOOKY,
                BiomeDictionary.Type.FOREST, BiomeDictionary.Type.MAGICAL
        ));

        public static PixieSpawns winter_pixie = new PixieSpawns(20, 1, 1, List.of(
                BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.MAGICAL
        ));

        public static CommonSpawns summer_bee_knight = new CommonSpawns(25, 1, 2);
        public static CommonSpawns dwarf_blacksmith = new CommonSpawns(5, 1, 1);

        public static PixieSpawns shroomling = new PixieSpawns(15, 1, 1, List.of(
                BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.MAGICAL));

    }

    public static class bee_knight {

        @Config("How much reputation the player needs to have for the guards to not attack him.")
        @IntRange(min = 0)
        public static int required_reputation = 35;

        @Config("What should be the aggrevation range of the Bee Knights, when someone tries to steal their honey!")
        @IntRange(min = 3)
        public static int aggrevation_range = 20;
    }

    public static class dimensions {

        @Config(value = "What dimension biomes should be allowed for Feywild mobs")
        public static ResourceList black_list_biomes = new ResourceList(false, builder -> {
            builder.parse("undergarden:*");
        });
    }
}
