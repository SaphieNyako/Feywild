package com.feywild.feywild;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;

public class FeyPlayerData {
    
    private static final String KEY = "FeyWildPlayerData";
    
    public static CompoundNBT get(PlayerEntity player) {
        CompoundNBT persistent = player.getPersistentData();
        if (!persistent.contains(KEY, Constants.NBT.TAG_COMPOUND)) {
            persistent.put(KEY, new CompoundNBT());
        }
        return persistent.getCompound(KEY);
    }
    
    public static void copy(PlayerEntity source, PlayerEntity target) {
        if (source.getPersistentData().contains(KEY, Constants.NBT.TAG_COMPOUND)) {
            target.getPersistentData().put(KEY, source.getPersistentData().getCompound(KEY).copy());
        }
    }
}
