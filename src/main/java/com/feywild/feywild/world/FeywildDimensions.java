package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class FeywildDimensions {

    public static final ResourceKey<Level> FEYWILD = ResourceKey.create(Registry.DIMENSION_REGISTRY, FeywildMod.getInstance().resource("feywild"));
    public static final ResourceKey<Level> MARKETPLACE = ResourceKey.create(Registry.DIMENSION_REGISTRY, FeywildMod.getInstance().resource("marketplace"));
}
