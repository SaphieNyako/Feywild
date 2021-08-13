package com.feywild.feywild.config;

import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.validator.IntRange;

public class MiscConfig {

    @Config("Whether players should spawn with a Feywild Lexicon")
    public static boolean initial_lexicon = true;

    @Config("The duration in ticks for the levitation effect applied by fey dust.")
    @IntRange(min = 1)
    public static int fey_dust_ticks = 30;
}
