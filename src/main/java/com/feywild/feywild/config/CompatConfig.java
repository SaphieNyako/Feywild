package com.feywild.feywild.config;

import com.feywild.feywild.config.data.MythicCompat;
import org.moddingx.libx.annotation.config.RegisterConfig;
import org.moddingx.libx.config.Config;

@RegisterConfig("compat")
public class CompatConfig {

    @Config({
            "Where should Feywild features be generated if MythicBotany is installed?",
            "The alfheim_locked option will lock feywild progression behind alfheim."
    })
    public static MythicCompat mythic_alfheim = MythicCompat.BOTH;

    @Config("Whether waystones should generate in Feywild structures.")
    public static boolean waystones = true;
}
