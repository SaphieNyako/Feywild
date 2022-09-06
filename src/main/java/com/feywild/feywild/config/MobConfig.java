package com.feywild.feywild.config;

import org.moddingx.libx.annotation.config.RegisterConfig;
import org.moddingx.libx.config.Config;
import org.moddingx.libx.config.validate.IntRange;

@RegisterConfig("mobs")
public class MobConfig {

    public static class bee_knight {

        @Config("How much reputation the player needs to have for the bee_knight to not attack him.")
        @IntRange(min = 0)
        public static int required_reputation = 35;

        @Config("What should be the aggravation range of the Bee Knights, when someone tries to steal their honey!")
        @IntRange(min = 3)
        public static int aggrevation_range = 20;
    }
}
