package com.feywild.feywild.util.configs;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class Config {

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    //CONFIG VARIABLES
    public static ForgeConfigSpec.IntValue FEY_DUST_DURATION;
    public static ForgeConfigSpec.BooleanValue SPAWN_LEXICON;
    public static ForgeConfigSpec.BooleanValue BETA;
    public static ForgeConfigSpec.BooleanValue MENU_SCREEN;

    public static PerformanceConfig PERFORMANCE_CONFIG;

    public static ForgeConfigSpec.IntValue MYTHIC;
    public static ForgeConfigSpec.BooleanValue DUNGEONS_GEAR;

    public static OreConfig FEY_GEM_CONFIG;

    public static MobConfig SPRING_PIXIE_CONFIG;
    public static MobConfig SUMMER_PIXIE_CONFIG;
    public static MobConfig AUTUMN_PIXIE_CONFIG;
    public static MobConfig WINTER_PIXIE_CONFIG;
    public static MobConfig DWARF_CONFIG;

    public static BiomeConfig SPRING_BIOME_CONFIG;
    public static BiomeConfig AUTUMN_BIOME_CONFIG;
    public static BiomeConfig SUMMER_BIOME_CONFIG;
    public static BiomeConfig WINTER_BIOME_CONFIG;

    public static TreePatchesConfig TREE_PATCH_CONFIG;

    public static StructureConfig SPRING_WORLD_TREE_CONFIG;
    public static StructureConfig SUMMER_WORLD_TREE_CONFIG;
    public static StructureConfig AUTUMN_WORLD_TREE_CONFIG;
    public static StructureConfig WINTER_WORLD_TREE_CONFIG;
    public static StructureConfig BLACKSMITH_CONFIG;
    public static StructureConfig LIBRARY_CONFIG;

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
        setBetaVariables(SERVER_BUILDER, CLIENT_BUILDER);
        setModPackVariables(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    //CONFIG METHOD

    private static void setBetaVariables(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        BETA = SERVER_BUILDER.comment("This may not work, expect fatal errors, here be dragons!\n Activate beta features:").define("beta", false);
    }

    private static void setModPackVariables(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        MYTHIC = SERVER_BUILDER.comment("Note: This requires the mod: Mythic Botany. 0: feywild biomes spawn in overworld, default alfheim, 1: feywild biomes spawn in overworld, feywild features spawn in alfheim, 2: feywild biomes not active, feywild features spawn in alfheim ").defineInRange("mythic", 1, 0, 2);

        DUNGEONS_GEAR = SERVER_BUILDER.comment("Note: This requires the mod: Dungeons Gear. Set to True if you want Schematics Items for Dungeon Gear.").define("dungeons_gear", false);

        MENU_SCREEN = CLIENT_BUILDER.comment("Use the Feywild background.").define("menu_screen", true);
    }

    private static void setConfigVariables(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        FEY_DUST_DURATION = SERVER_BUILDER.comment("How long fey dust effect lasts:")
                .defineInRange("fey_dust_duration", 15, 0, 180);

        SPAWN_LEXICON = SERVER_BUILDER.comment("Should the Feywild Lexicon be avialable on login: ").define("spawn_with_book", true);

        PERFORMANCE_CONFIG = new PerformanceConfig("Performance", true);

        PERFORMANCE_CONFIG.apply(CLIENT_BUILDER);
    }

    private static void setConfigOres(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        FEY_GEM_CONFIG = new OreConfig("Fey Gem", 10, 5, 11, 48);

        FEY_GEM_CONFIG.apply(SERVER_BUILDER);
    }

    private static void setMobConfigs(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SPRING_PIXIE_CONFIG = new MobConfig("Spring Pixie", 20, 1, 1,
                Arrays.asList("RIVER", "FOREST", "PLAINS", "MAGICAL"));
        SUMMER_PIXIE_CONFIG = new MobConfig("Summer Pixie", 20, 1, 1,
                Arrays.asList("LUSH", "HOT", "MAGICAL"));
        AUTUMN_PIXIE_CONFIG = new MobConfig("Autumn Pixie", 20, 1, 1,
                Arrays.asList("SWAMP", "MUSHROOM", "SPOOKY", "FOREST", "MAGICAL"));
        WINTER_PIXIE_CONFIG = new MobConfig("Winter Pixie", 20, 1, 1,
                Arrays.asList("DEAD", "SNOWY", "COLD", "MAGICAL"));
        DWARF_CONFIG = new MobConfig("Dwarf", 20, 1, 1, Collections.singletonList("ALL"));

        SPRING_PIXIE_CONFIG.apply(SERVER_BUILDER);
        SUMMER_PIXIE_CONFIG.apply(SERVER_BUILDER);
        AUTUMN_PIXIE_CONFIG.apply(SERVER_BUILDER);
        WINTER_PIXIE_CONFIG.apply(SERVER_BUILDER);
        DWARF_CONFIG.apply(SERVER_BUILDER);
    }

    private static void setStructureConfigs(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SPRING_WORLD_TREE_CONFIG = new StructureConfig("Spring World Tree", 100, 50);
        SUMMER_WORLD_TREE_CONFIG = new StructureConfig("Summer World Tree", 100, 50);
        AUTUMN_WORLD_TREE_CONFIG = new StructureConfig("Autumn World Tree", 100, 50);
        WINTER_WORLD_TREE_CONFIG = new StructureConfig("Winter World Tree", 100, 50);

        BLACKSMITH_CONFIG = new StructureConfig("Blacksmith", 50, 40);
        LIBRARY_CONFIG = new StructureConfig("Library", 35, 25);

        SPRING_WORLD_TREE_CONFIG.apply(SERVER_BUILDER);
        SUMMER_WORLD_TREE_CONFIG.apply(SERVER_BUILDER);
        AUTUMN_WORLD_TREE_CONFIG.apply(SERVER_BUILDER);
        WINTER_WORLD_TREE_CONFIG.apply(SERVER_BUILDER);

        BLACKSMITH_CONFIG.apply(SERVER_BUILDER);
        LIBRARY_CONFIG.apply(SERVER_BUILDER);
    }

    private static void setTreePatchesConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        TREE_PATCH_CONFIG = new TreePatchesConfig(true, true, true, true, 0.05, 3);

        TREE_PATCH_CONFIG.apply(SERVER_BUILDER);
    }

    private static void setBiomeConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SPRING_BIOME_CONFIG = new BiomeConfig("Blossoming Wealds", 15,
                0.005d);
        SUMMER_BIOME_CONFIG = new BiomeConfig("Golden Seelie Fields", 15,
                0.005d);
        AUTUMN_BIOME_CONFIG = new BiomeConfig("Eternal Fall", 15,
                0.005d);
        WINTER_BIOME_CONFIG = new BiomeConfig("Frozen Retreat", 15,
                0.005d);

        SPRING_BIOME_CONFIG.apply(SERVER_BUILDER);
        SUMMER_BIOME_CONFIG.apply(SERVER_BUILDER);
        AUTUMN_BIOME_CONFIG.apply(SERVER_BUILDER);
        WINTER_BIOME_CONFIG.apply(SERVER_BUILDER);
    }

    //LOAD
    public static void loadConfigFile(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync().autosave().writingMode(WritingMode.REPLACE).build();

        file.load();
        config.setConfig(file);
    }
}
