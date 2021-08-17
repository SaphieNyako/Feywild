package com.feywild.feywild.world.dimension;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModDimensions {

    public static RegistryKey<World> MARKET_PLACE_DIMENSION;

    public static void register() {

        MARKET_PLACE_DIMENSION = RegistryKey.create(Registry.DIMENSION_REGISTRY,
                new ResourceLocation(FeywildMod.getInstance().modid, "market_place_dimension"));
    }
}
