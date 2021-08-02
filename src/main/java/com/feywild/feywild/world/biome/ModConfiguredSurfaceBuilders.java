package com.feywild.feywild.world.biome;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class ModConfiguredSurfaceBuilders {

    public static RegistryKey<ConfiguredSurfaceBuilder<?>> SPRING_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.getInstance().modid, "spring_surface"));

    public static RegistryKey<ConfiguredSurfaceBuilder<?>> SUMMER_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.getInstance().modid, "summer_surface"));

    public static RegistryKey<ConfiguredSurfaceBuilder<?>> AUTUMN_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.getInstance().modid, "autumn_surface"));

    public static RegistryKey<ConfiguredSurfaceBuilder<?>> WINTER_SURFACE
            = RegistryKey.create(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY,
            new ResourceLocation(FeywildMod.getInstance().modid, "winter_surface"));

}
