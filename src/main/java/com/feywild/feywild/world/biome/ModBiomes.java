package com.feywild.feywild.world.biome;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.world.biome.biomes.AutumnBiome;
import com.feywild.feywild.world.biome.biomes.SpringBiome;
import com.feywild.feywild.world.biome.biomes.SummerBiome;
import com.feywild.feywild.world.biome.biomes.WinterBiome;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.world.biome.Biome;

@RegisterClass
public class ModBiomes {
    
    public static final Biome blossomingWealds = SpringBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.SPRING_SURFACE, 0.125f, WorldGenConfig.biomes.spring.size);
    public static final Biome goldenSeelieFields = SummerBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.SUMMER_SURFACE, 0.125f, WorldGenConfig.biomes.summer.size);
    public static final Biome eternalFall = AutumnBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.AUTUMN_SURFACE, 0.125f, WorldGenConfig.biomes.autumn.size);
    public static final Biome frozenRetreat = WinterBiome.INSTANCE.biomeSetup(() -> ModConfiguredSurfaceBuilders.WINTER_SURFACE, 0.125f, WorldGenConfig.biomes.winter.size);
}
