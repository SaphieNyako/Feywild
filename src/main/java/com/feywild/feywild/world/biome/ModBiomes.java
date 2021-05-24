package com.feywild.feywild.world.biome;

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


    public static final RegistryObject<Biome> SPRING_BIOME
            = Registration.BIOMES.register("spring_biome",
            () -> springBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.SPRING_SURFACE), 0.125f, 0.05f));


    public static final RegistryObject<Biome> SUMMER_BIOME
            = Registration.BIOMES.register("summer_biome",
            () -> summerBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.SUMMER_SURFACE), 0.125f, 0.05f));


    public static final RegistryObject<Biome> AUTUMN_BIOME
            = Registration.BIOMES.register("autumn_biome",
            () -> autumnBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.AUTUMN_SURFACE), 0.125f, 0.05f));

    public static final RegistryObject<Biome> WINTER_BIOME
            = Registration.BIOMES.register("winter_biome",
            () -> winterBiome.biomeSetup(() -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(
                    ModConfiguredSurfaceBuilders.WINTER_SURFACE), 0.125f, 0.05f));


    public static void register() {}

}
