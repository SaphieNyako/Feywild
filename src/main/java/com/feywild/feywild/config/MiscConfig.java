package com.feywild.feywild.config;

import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.validator.IntRange;

public class MiscConfig {

    @Config("Whether players should spawn with a Feywild Lexicon")
    public static boolean initial_lexicon = true;

    @Config("Whether the players should be able to select one of the court's scrolls on first login, on first time opening the guide book or none.")
    public static ScrollConfig initial_scroll = ScrollConfig.BOOK;

    @Config("The duration in ticks for the levitation effect applied by fey dust.")
    @IntRange(min = 1)
    public static int fey_dust_ticks = 30;

}
