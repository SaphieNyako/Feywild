package com.feywild.feywild.block.entity.mana;

import net.minecraft.nbt.Tag;
import net.minecraft.nbt.IntTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.*;

public class CapabilityMana {

    public static final Capability<IManaStorage> MANA = CapabilityManager.get(new CapabilityToken<>() {});

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IManaStorage.class);
    }
}
