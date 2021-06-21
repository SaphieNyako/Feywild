package com.feywild.feywild.util;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;
import java.util.Arrays;

public class Config {

    //TODO: CONFIG for Biomes, Spawn ore, Structure Spawn, Tree patches

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    //CONFIG VARIABLES
    public static ForgeConfigSpec.IntValue FEY_DUST_DURATION;
    public static ForgeConfigSpec.BooleanValue SPAWN_LEXICON;
    public static ForgeConfigSpec.BooleanValue BETA;

    public static ForgeConfigSpec.IntValue FEY_GEM_MAX_VEIN_SIZE;
    public static ForgeConfigSpec.IntValue FEY_GEM_MIN_HEIGHT;
    public static ForgeConfigSpec.IntValue FEY_GEM_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue FEY_GEM_WEIGHT;

    public static MobConfig SPRING_PIXIE_CONFIG;
    public static MobConfig SUMMER_PIXIE_CONFIG;
    public static MobConfig AUTUMN_PIXIE_CONFIG;
    public static MobConfig WINTER_PIXIE_CONFIG;
    public static MobConfig DWARF_CONFIG;

    public static ForgeConfigSpec.IntValue SPRING_WORLD_TREE_AVERAGE_DISTANCE;
    public static ForgeConfigSpec.IntValue SPRING_WORLD_TREE_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue SUMMER_WORLD_TREE_AVERAGE_DISTANCE;
    public static ForgeConfigSpec.IntValue SUMMER_WORLD_TREE_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue AUTUMN_WORLD_TREE_AVERAGE_DISTANCE;
    public static ForgeConfigSpec.IntValue AUTUMN_WORLD_TREE_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue WINTER_WORLD_TREE_AVERAGE_DISTANCE;
    public static ForgeConfigSpec.IntValue WINTER_WORLD_TREE_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue BLACKSMITH_AVERAGE_DISTANCE;
    public static ForgeConfigSpec.IntValue BLACKSMITH_MIN_DISTANCE;
    public static ForgeConfigSpec.IntValue LIBRARY_AVERAGE_DISTANCE;
    public static ForgeConfigSpec.IntValue LIBRARY_MIN_DISTANCE;

    public static ForgeConfigSpec.DoubleValue TREE_PATCHES_CHANCE;
    public static ForgeConfigSpec.IntValue TREE_PATCHES_SIZE;

    public static ForgeConfigSpec.IntValue SPRING_BIOME_WEIGHT;
    public static ForgeConfigSpec.IntValue SUMMER_BIOME_WEIGHT;
    public static ForgeConfigSpec.IntValue AUTUMN_BIOME_WEIGHT;
    public static ForgeConfigSpec.IntValue WINTER_BIOME_WEIGHT;

    static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        //we can do this in one method, but I like the overview...
        setConfigVariables(SERVER_BUILDER, CLIENT_BUILDER);
        setConfigOres(SERVER_BUILDER, CLIENT_BUILDER);
        setMobConfigs(SERVER_BUILDER, CLIENT_BUILDER);
        setStructureConfigs(SERVER_BUILDER, CLIENT_BUILDER);
        setTreePatchesConfig(SERVER_BUILDER, CLIENT_BUILDER);
        setBiomeConfig(SERVER_BUILDER, CLIENT_BUILDER);
        setBetaVariables(SERVER_BUILDER,CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();

    }

    //CONFIG METHOD

    private static void setBetaVariables(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER){
        BETA = CLIENT_BUILDER.comment("This may not work, expect fatal errors, here be dragons!\n Activate beta features:").define("beta", false);
    }

    private static void setConfigVariables(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        FEY_DUST_DURATION = CLIENT_BUILDER.comment("How long fey dust effect lasts:")
                .defineInRange("fey_dust_duration", 15, 0, 180);

        SPAWN_LEXICON = CLIENT_BUILDER.comment("Should the Feywild Lexicon be avialable on login: ").define("spawn_with_book", true);
    }

    private static void setConfigOres(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        FEY_GEM_MAX_VEIN_SIZE = CLIENT_BUILDER.comment("max vein size for Fey Gems:")
                .defineInRange("fey_gem_max_vein_size", 5, 0, 20);
        FEY_GEM_MIN_HEIGHT = CLIENT_BUILDER.comment("min height for Fey Gems:")
                .defineInRange("fey_gem_min_height", 11, 0, 64);
        FEY_GEM_MAX_HEIGHT = CLIENT_BUILDER.comment("max height for Fey Gems:")
                .defineInRange("fey_gem_max_height", 48, 0, 64);
        FEY_GEM_WEIGHT = CLIENT_BUILDER.comment("weight for Fey Gems:")
                .defineInRange("fey_gem_weight", 10, 0, 100);

    }

    private static void setMobConfigs(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        SPRING_PIXIE_CONFIG = new MobConfig("Spring Pixie", 20, 1, 1,
                Arrays.asList("RIVER", "FOREST", "PLAINS"));
        SUMMER_PIXIE_CONFIG = new MobConfig("Summer Pixie", 20, 1, 1,
                Arrays.asList("LUSH", "HOT"));
        AUTUMN_PIXIE_CONFIG = new MobConfig("Autumn Pixie", 20, 1, 1,
                Arrays.asList("SWAMP", "MUSHROOM", "SPOOKY", "FOREST"));
        WINTER_PIXIE_CONFIG = new MobConfig("Winter Pixie", 20, 1, 1,
                Arrays.asList("DEAD", "SNOWY", "COLD"));
        DWARF_CONFIG = new MobConfig("Dwarf", 20, 1, 1, Arrays.asList("ALL"));

        SPRING_PIXIE_CONFIG.apply(CLIENT_BUILDER);
        SUMMER_PIXIE_CONFIG.apply(CLIENT_BUILDER);
        AUTUMN_PIXIE_CONFIG.apply(CLIENT_BUILDER);
        WINTER_PIXIE_CONFIG.apply(CLIENT_BUILDER);
        DWARF_CONFIG.apply(CLIENT_BUILDER);

    }

    private static void setStructureConfigs(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        SPRING_WORLD_TREE_AVERAGE_DISTANCE = CLIENT_BUILDER.comment("Average Distance between next Spring World Tree:")
                .defineInRange("spring_world_tree_average_distance", 100, 0, 500);
        SPRING_WORLD_TREE_MIN_DISTANCE = CLIENT_BUILDER.comment("Min Distance between next Spring World Tree:")
                .defineInRange("spring_world_tree_mine_distance", 50, 0, 500);
        SUMMER_WORLD_TREE_AVERAGE_DISTANCE = CLIENT_BUILDER.comment("Average Distance between next Summer World Tree:")
                .defineInRange("summer_world_tree_average_distance", 100, 0, 500);
        SUMMER_WORLD_TREE_MIN_DISTANCE = CLIENT_BUILDER.comment("Min Distance between next Summer World Tree:")
                .defineInRange("summer_world_tree_mine_distance", 50, 0, 500);
        AUTUMN_WORLD_TREE_AVERAGE_DISTANCE = CLIENT_BUILDER.comment("Average Distance between next Autumn World Tree:")
                .defineInRange("autumn_world_tree_average_distance", 100, 0, 500);
        AUTUMN_WORLD_TREE_MIN_DISTANCE = CLIENT_BUILDER.comment("Min Distance between next Autumn World Tree:")
                .defineInRange("autumn_world_tree_mine_distance", 50, 0, 500);
        WINTER_WORLD_TREE_AVERAGE_DISTANCE = CLIENT_BUILDER.comment("Average Distance between next Winter World Tree:")
                .defineInRange("winter_world_tree_average_distance", 100, 0, 500);
        WINTER_WORLD_TREE_MIN_DISTANCE = CLIENT_BUILDER.comment("Min Distance between next Winter World Tree:")
                .defineInRange("winter_world_tree_mine_distance", 50, 0, 500);
        BLACKSMITH_AVERAGE_DISTANCE = CLIENT_BUILDER.comment("Average Distance between next Blacksmith:")
                .defineInRange("blacksmith_average_distance", 50, 0, 500);
        BLACKSMITH_MIN_DISTANCE = CLIENT_BUILDER.comment("Min Distance between next Blacksmith:")
                .defineInRange("blacksmith_min_distance", 40, 0, 500);
        LIBRARY_AVERAGE_DISTANCE = CLIENT_BUILDER.comment("Average Distance between next Library:")
                .defineInRange("library_average_distance", 35, 0, 500);
        LIBRARY_MIN_DISTANCE = CLIENT_BUILDER.comment("Min Distance between next Blacksmith:")
                .defineInRange("blacksmith_min_distance", 25, 0, 500);

    }

    private static void setTreePatchesConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        TREE_PATCHES_CHANCE = CLIENT_BUILDER.comment("Chance of Fey Tree patch spawning per biome: ")
                .defineInRange("tree_patches_chance", 0.01, 0.0, 1.0);
        TREE_PATCHES_SIZE = CLIENT_BUILDER.comment("Size of the Fey Tree patches:")
                .defineInRange("tree_patches_size", 3, 0, 10);
    }

    private static void setBiomeConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        SPRING_BIOME_WEIGHT = CLIENT_BUILDER.comment("Spring biome weight:")
                .defineInRange("spring_biome_weight", 20, 0, 100);
        SUMMER_BIOME_WEIGHT = CLIENT_BUILDER.comment("Summer biome weight:")
                .defineInRange("summer_biome_weight", 20, 0, 100);
        AUTUMN_BIOME_WEIGHT = CLIENT_BUILDER.comment("Autumn biome weight:")
                .defineInRange("autumn_biome_weight", 20, 0, 100);
        WINTER_BIOME_WEIGHT = CLIENT_BUILDER.comment("Winter biome weight:")
                .defineInRange("winter_biome_weight", 20, 0, 100);

    }

    //LOAD
    public static void loadConfigFile(ForgeConfigSpec config, String path) {

        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync().autosave().writingMode(WritingMode.REPLACE).build();

        file.load();
        config.setConfig(file);

    }

}
