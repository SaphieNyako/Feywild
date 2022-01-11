package com.feywild.feywild.config;

import com.feywild.feywild.config.data.ScrollSelectType;
import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.validator.FloatRange;
import io.github.noeppi_noeppi.libx.config.validator.IntRange;

public class MiscConfig {

    @Config("Whether players should spawn with a Feywild Lexicon")
    public static boolean initial_lexicon = true;

    @Config("Whether the players should be able to select one of the court's scrolls on first login, on first time opening the guide book or none.")
    public static ScrollSelectType initial_scroll = ScrollSelectType.NONE;

    @Config("The duration in ticks for the levitation effect applied by fey dust.")
    @IntRange(min = 1)
    public static int fey_dust_ticks = 30;

    @Config("This determines the weight of the rune stone appearing in mineshaft treasure chests.")
    @FloatRange(min = 0.01f, max = 1)
    public static float rune_stone_weight = 0.20f;

    @Config("The amount of time in seconds that the magical honey needs to respawn")
    @IntRange(min = 1)
    public static int magical_honey_timer = 1200;
}
