package com.saphienyako.feywild.util;

import net.minecraft.world.level.Level;

public class FeywildTimeUtil {

    private FeywildTimeUtil(){

    }

    public static boolean isNight(Level level) {
        long time = Math.floorMod(level.getDefaultClockTime(), 24000L);
        return time >= 13000L && time < 23000L;
    }

    public static boolean isDay(Level level) {
        return !isNight(level);
    }
}
