package com.feywild.feywild.block.entity.mana;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityMana {

    @CapabilityInject(IManaStorage.class)
    public static Capability<IManaStorage> MANA = null;

    public static void register() {
        
        CapabilityManager.INSTANCE.register(IManaStorage.class, new Capability.IStorage<IManaStorage>() {
            
                // These methods are actually never used if not used by the mod itself
                // Will also be removed in 1.17
                // We just do nothing.
                @Override
                public INBT writeNBT(Capability<IManaStorage> capability, IManaStorage instance, Direction side) {
                    return IntNBT.valueOf(0);
                }

                @Override
                public void readNBT(Capability<IManaStorage> capability, IManaStorage instance, Direction side, INBT nbt) {
                    //
                }
            }, () -> new ManaStorage(1000)
        );
    }
}
