package com.feywild.feywild.config;

import org.moddingx.libx.annotation.config.RegisterConfig;
import org.moddingx.libx.config.Config;

@RegisterConfig(value = "client", client = true)
public class ClientConfig {

    @Config("Whether Feywild should replace the main menu and the creative music in game.")
    public static boolean replace_menu = true;

    @Config("Whether tree particles should spawn.")
    public static boolean tree_particles = true;

    @Config("Whether Fey Flowers particles should spawn.")
    public static boolean flower_particles = true;

    @Config("Whether Glow Layers for the Shroomling and Blacksmith should be used: Optifine Users should always disable this!")
    public static boolean mob_glow = true;
}
