package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import io.github.noeppi_noeppi.mods.sandbox.SandBox;
import io.github.noeppi_noeppi.mods.sandbox.biome.BiomeLayer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class FeywildDimensions {

    public static final ResourceKey<Level> FEYWILD = ResourceKey.create(Registry.DIMENSION_REGISTRY, FeywildMod.getInstance().resource("feywild"));
    public static final ResourceKey<Level> MARKETPLACE = ResourceKey.create(Registry.DIMENSION_REGISTRY, FeywildMod.getInstance().resource("marketplace"));
    
    public static final ResourceKey<BiomeLayer> FEYWILD_LAYER = ResourceKey.create(SandBox.BIOME_LAYER_REGISTRY, FeywildMod.getInstance().resource("default"));
}
