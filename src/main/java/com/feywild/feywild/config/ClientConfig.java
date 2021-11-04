package com.feywild.feywild.config;

import io.github.noeppi_noeppi.libx.config.Config;

public class ClientConfig {

    @Config("Whether Feywild should replace the main menu and music")
    public static boolean replace_menu = true;

    @Config("Whether feywild music should be played in the fey biomes")
    public static boolean biome_music = true;

    @Config("Whether tree particles should spawn.")
    public static boolean tree_particles = true;

    @Config("Whether Fey Flowers particles should spawn.")
    public static boolean flower_particles = true;
}
