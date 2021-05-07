package com.feywild.feywild.world.biome;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;



public class ModConfiguredSurfaceBuilders {

    /* Creates a custom surface, set up for blocks is done in ModBiomeGeneration */

    /* SPRING */
    public static RegistryKey<ConfiguredSurfaceBuilder<?>> SPRING_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.MOD_ID, "spring_surface"));

    /* SUMMER */
    public static RegistryKey<ConfiguredSurfaceBuilder<?>> SUMMER_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.MOD_ID, "summer_surface"));

    /* AUTUMN */
    public static RegistryKey<ConfiguredSurfaceBuilder<?>> AUTUMN_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.MOD_ID, "autumn_surface"));

    /* WINTER */
    public static RegistryKey<ConfiguredSurfaceBuilder<?>> WINTER_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.MOD_ID, "winter_surface"));

}
