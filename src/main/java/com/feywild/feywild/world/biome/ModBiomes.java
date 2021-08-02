package com.feywild.feywild.world.biome;

import com.feywild.feywild.util.configs.Config;
import com.feywild.feywild.world.biome.biomes.AutumnBiome;
import com.feywild.feywild.world.biome.biomes.SpringBiome;
import com.feywild.feywild.world.biome.biomes.SummerBiome;
import com.feywild.feywild.world.biome.biomes.WinterBiome;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;

@RegisterClass
public class ModBiomes {
    
    public static final Biome blossomingWealds = new SpringBiome().biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(ModConfiguredSurfaceBuilders.SPRING_SURFACE), 0.125f, (float) Config.SPRING_BIOME_CONFIG.getCachedSize());
    public static final Biome goldenSeelieFields = new SummerBiome().biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(ModConfiguredSurfaceBuilders.SUMMER_SURFACE), 0.125f, (float) Config.SUMMER_BIOME_CONFIG.getCachedSize());
    public static final Biome eternalFall = new AutumnBiome().biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(ModConfiguredSurfaceBuilders.AUTUMN_SURFACE), 0.125f, (float) Config.AUTUMN_BIOME_CONFIG.getCachedSize());
    public static final Biome frozenRetreat = new WinterBiome().biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(ModConfiguredSurfaceBuilders.WINTER_SURFACE), 0.125f, (float) Config.WINTER_BIOME_CONFIG.getCachedSize());
}
