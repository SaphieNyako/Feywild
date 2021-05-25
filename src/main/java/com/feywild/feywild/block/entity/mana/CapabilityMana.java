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
                    @Override
                    public INBT writeNBT(Capability<IManaStorage> capability, IManaStorage instance, Direction side) {
                        return IntNBT.valueOf(instance.getManaStored());
                    }

                    @Override
                    public void readNBT(Capability<IManaStorage> capability, IManaStorage instance, Direction side, INBT nbt) {
                        if (!(instance instanceof ManaStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                        ((ManaStorage) instance).mana = ((IntNBT) nbt).getAsInt();
                    }
                },
                () -> new ManaStorage(1000));
    }

}
