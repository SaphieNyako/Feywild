package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import org.moddingx.libx.sandbox.SandBox;
import org.moddingx.libx.sandbox.generator.BiomeLayer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class FeywildDimensions {

    public static final ResourceKey<Level> FEYWILD = ResourceKey.create(Registry.DIMENSION, FeywildMod.getInstance().resource("feywild"));
    public static final ResourceKey<Level> MARKETPLACE = ResourceKey.create(Registry.DIMENSION, FeywildMod.getInstance().resource("marketplace"));
    
    public static final ResourceKey<BiomeLayer> FEYWILD_LAYER = ResourceKey.create(SandBox.BIOME_LAYER, FeywildMod.getInstance().resource("default"));
}
