package com.feywild.feywild.world.biome;

import com.feywild.feywild.util.configs.Config;
import com.feywild.feywild.world.biome.biomes.AutumnBiome;
import com.feywild.feywild.world.biome.biomes.SpringBiome;
import com.feywild.feywild.world.biome.biomes.SummerBiome;
import com.feywild.feywild.world.biome.biomes.WinterBiome;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.world.biome.Biome;

@RegisterClass
public class ModBiomes {
    
    public static final Biome blossomingWealds = SpringBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.SPRING_SURFACE, 0.125f, (float) Config.SPRING_BIOME_CONFIG.getCachedSize());
    public static final Biome goldenSeelieFields = SummerBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.SUMMER_SURFACE, 0.125f, (float) Config.SUMMER_BIOME_CONFIG.getCachedSize());
    public static final Biome eternalFall = AutumnBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.AUTUMN_SURFACE, 0.125f, (float) Config.AUTUMN_BIOME_CONFIG.getCachedSize());
    public static final Biome frozenRetreat = WinterBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.WINTER_SURFACE, 0.125f, (float) Config.WINTER_BIOME_CONFIG.getCachedSize());
}
