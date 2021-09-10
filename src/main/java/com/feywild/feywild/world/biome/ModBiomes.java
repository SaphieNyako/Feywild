package com.feywild.feywild.world.biome;

import com.feywild.feywild.world.biome.biomes.*;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.world.biome.Biome;

@RegisterClass
public class ModBiomes {
    
    public static final Biome blossomingWealds = BiomeFactory.create(BiomeEnvironment.OVERWORLD, SpringBiome.INSTANCE);
    public static final Biome goldenSeelieFields = BiomeFactory.create(BiomeEnvironment.OVERWORLD, SummerBiome.INSTANCE);
    public static final Biome eternalFall = BiomeFactory.create(BiomeEnvironment.OVERWORLD, AutumnBiome.INSTANCE);
    public static final Biome frozenRetreat = BiomeFactory.create(BiomeEnvironment.OVERWORLD, WinterBiome.INSTANCE);
}
