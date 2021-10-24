package com.feywild.feywild;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.Constants;

public class FeyPlayerData {
    
    private static final String KEY = "FeyWildPlayerData";
    
    public static CompoundTag get(Player player) {
        CompoundTag persistent = player.getPersistentData();
        if (!persistent.contains(KEY, Constants.NBT.TAG_COMPOUND)) {
            persistent.put(KEY, new CompoundTag());
        }
        return persistent.getCompound(KEY);
    }
    
    public static void copy(Player source, Player target) {
        if (source.getPersistentData().contains(KEY, Constants.NBT.TAG_COMPOUND)) {
            target.getPersistentData().put(KEY, source.getPersistentData().getCompound(KEY).copy());
        }
    }
}
