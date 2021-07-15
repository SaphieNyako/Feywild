package com.feywild.feywild.setup;

import net.minecraft.world.World;

// TODO can completely be deleted as its only used for client initialisation. However
// for this, FMLClientSetupEvent can bve used in the mod class.
public interface IProxy {

    void init();

    World getClientWorld();
}
