package com.feywild.feywild.config;

import org.moddingx.libx.annotation.config.RegisterConfig;
import org.moddingx.libx.config.Config;

@RegisterConfig("compat")
public class CompatConfig {

    @Config("Whether dwarves in blacksmiths should be replaced with runestones")
    public static boolean replace_dwarves = false;

    @Config("Whether waystones should generate in Feywild structures.")
    public static boolean waystones = true;
}
