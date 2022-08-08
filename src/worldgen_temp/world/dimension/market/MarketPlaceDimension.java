package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MarketPlaceDimension {

    public static final ResourceKey<Level> MARKET_PLACE_DIMENSION =
            ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(FeywildMod.getInstance().modid, "market_place_dimension"));

    public static void register() {
        System.out.println("Registering ModDimensions for " + FeywildMod.getInstance().modid);
    }

}
