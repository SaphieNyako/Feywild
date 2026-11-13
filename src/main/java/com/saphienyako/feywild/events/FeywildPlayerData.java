package com.saphienyako.feywild.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

public class FeywildPlayerData {

    private static final String KEY = "FeyWildPlayerData";

    public static CompoundTag get(Player player) {
        CompoundTag persistent = player.getPersistentData();

        return persistent.getCompound(KEY).orElseGet(() -> {
            CompoundTag data = new CompoundTag();
            persistent.put(KEY, data);
            return data;
        });
    }

    public static void copy(Player source, Player target) {
        source.getPersistentData().getCompound(KEY).ifPresent(data -> target.getPersistentData().put(KEY, data.copy()));
    }

}
