package com.feywild.feywild.config;

import io.github.noeppi_noeppi.libx.config.Config;

public class CompatConfig {
    
    @Config("Where should Feywild features be generated if MythicBotany is installed?")
    public static MythicCompat mythic_alfheim = MythicCompat.BOTH;
    
    @Config("Whether waystones should generate in Feywild structures.")
    public static boolean waystones = true;
}
