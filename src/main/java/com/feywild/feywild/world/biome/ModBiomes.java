package com.feywild.feywild.world.biome;

import com.feywild.feywild.util.Configs.Config;
import com.feywild.feywild.util.Registration;
import com.feywild.feywild.world.biome.biomes.AutumnBiome;
import com.feywild.feywild.world.biome.biomes.SpringBiome;
import com.feywild.feywild.world.biome.biomes.SummerBiome;
import com.feywild.feywild.world.biome.biomes.WinterBiome;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;

public class ModBiomes {

    public static final SpringBiome springBiome = new SpringBiome();
    public static final AutumnBiome autumnBiome = new AutumnBiome();
    public static final SummerBiome summerBiome = new SummerBiome();
    public static final WinterBiome winterBiome = new WinterBiome();

    public static final RegistryObject<Biome> BLOSSOMING_WEALDS
            = Registration.BIOMES.register("blossoming_wealds",
            () -> springBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.SPRING_SURFACE), 0.125f, (float) Config.SPRING_BIOME_CONFIG.getCachedSize()));

    public static final RegistryObject<Biome> GOLDEN_SEELIE_FIELDS
            = Registration.BIOMES.register("golden_seelie_fields",
            () -> summerBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.SUMMER_SURFACE), 0.125f, (float) Config.SUMMER_BIOME_CONFIG.getCachedSize()));

    public static final RegistryObject<Biome> ETERNAL_FALL
            = Registration.BIOMES.register("eternal_fall",
            () -> autumnBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.AUTUMN_SURFACE), 0.125f, (float) Config.AUTUMN_BIOME_CONFIG.getCachedSize()));

    public static final RegistryObject<Biome> FROZEN_RETREAT
            = Registration.BIOMES.register("frozen_retreat",
            () -> winterBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.WINTER_SURFACE), 0.125f, (float) Config.WINTER_BIOME_CONFIG.getCachedSize()));

    public static void register() {}

}
