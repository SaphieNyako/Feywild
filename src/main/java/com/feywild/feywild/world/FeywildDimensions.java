package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.moddingx.libx.sandbox.SandBox;
import org.moddingx.libx.sandbox.generator.BiomeLayer;

public class FeywildDimensions {

    public static final ResourceKey<Level> FEYWILD = ResourceKey.create(Registries.DIMENSION, FeywildMod.getInstance().resource("feywild"));
    public static final ResourceKey<Level> MARKETPLACE = ResourceKey.create(Registries.DIMENSION, FeywildMod.getInstance().resource("marketplace"));
    
    public static final ResourceKey<BiomeLayer> FEYWILD_LAYER = ResourceKey.create(SandBox.BIOME_LAYER, FeywildMod.getInstance().resource("default"));
}
