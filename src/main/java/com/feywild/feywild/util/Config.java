package com.feywild.feywild.util;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;
import java.util.Arrays;

public class Config {

    //TODO: CONFIG for Biomes, Spawn ore, Structure Spawn

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    //CONFIG VARIABLES
    public static ForgeConfigSpec.IntValue FEY_DUST_DURATION;
    public static ForgeConfigSpec.BooleanValue SPAWN_LEXICON;

    public static MobConfig SPRING_PIXIE_CONFIG;
    public static MobConfig SUMMER_PIXIE_CONFIG;
    public static MobConfig AUTUMN_PIXIE_CONFIG;
    public static MobConfig WINTER_PIXIE_CONFIG;
    public static MobConfig DWARF_CONFIG;

    static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        setConfigVariables(SERVER_BUILDER, CLIENT_BUILDER);
        setMobConfigs(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();

    }

    //CONFIG METHOD
    private static void setConfigVariables(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        FEY_DUST_DURATION = CLIENT_BUILDER.comment("How long fey dust effect lasts:")
                .defineInRange("fey_dust_duration", 15, 0, 180);

        SPAWN_LEXICON = CLIENT_BUILDER.comment("Should the Feywild Lexicon be avialable on login: ").define("spawn_with_book", false);
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

    //LOAD
    public static void loadConfigFile(ForgeConfigSpec config, String path) {

        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync().autosave().writingMode(WritingMode.REPLACE).build();

        file.load();
        config.setConfig(file);

    }

}
