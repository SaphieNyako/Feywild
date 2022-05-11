package com.feywild.feywild.world.dimension.feywild.biome;

import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.world.level.biome.Biome;

@RegisterClass(priority = -1)
public class FeywildBiomes {

    public static final Biome blossomingWealds = BiomeEnvironment.feywildBiome()
            .temperature(0.7f)
            .downfall(0.8f)
            .biomeCategory(Biome.BiomeCategory.FOREST)
            .mobSpawnSettings(BiomeEnvironment.springMobs().build())
            .specialEffects(BiomeEnvironment.springAmbience().build())
            .generationSettings(BiomeEnvironment.springFeatures().build())
            .build();

    public static final Biome goldenSeelieFields = BiomeEnvironment.feywildBiome()
            .temperature(0.9f)
            .downfall(0)
            .biomeCategory(Biome.BiomeCategory.SAVANNA)
            .mobSpawnSettings(BiomeEnvironment.summerMobs().build())
            .specialEffects(BiomeEnvironment.summerAmbience().build())
            .generationSettings(BiomeEnvironment.summerFeatures().build())
            .build();

    public static final Biome eternalFall = BiomeEnvironment.feywildBiome()
            .temperature(0.8f)
            .downfall(0.9f)
            .biomeCategory(Biome.BiomeCategory.MUSHROOM)
            .precipitation(Biome.Precipitation.RAIN)
            .mobSpawnSettings(BiomeEnvironment.autumnMobs().build())
            .specialEffects(BiomeEnvironment.autumnAmbience().build())
            .generationSettings(BiomeEnvironment.autumnFeatures().build())
            .build();

    public static final Biome frozenRetreat = BiomeEnvironment.feywildBiome()
            .temperature(0)
            .downfall(0.5f)
            .biomeCategory(Biome.BiomeCategory.ICY)
            .precipitation(Biome.Precipitation.SNOW)
            .mobSpawnSettings(BiomeEnvironment.winterMobs().build())
            .specialEffects(BiomeEnvironment.winterAmbience().build())
            .generationSettings(BiomeEnvironment.winterFeatures().build())
            .build();

    public static final Biome eliasianSea = BiomeEnvironment.feywildBiome()
            .temperature(0.8f)
            .downfall(0.7f)
            .biomeCategory(Biome.BiomeCategory.OCEAN)
            .mobSpawnSettings(BiomeEnvironment.oceanMobs().build())
            .specialEffects(BiomeEnvironment.oceanAmbience().build())
            .generationSettings(BiomeEnvironment.oceanFeatures().build())
            .build();

    public static final Biome goldenMountains = BiomeEnvironment.feywildBiome()
            .temperature(0.9f)
            .downfall(0)
            .biomeCategory(Biome.BiomeCategory.MOUNTAIN)
            .mobSpawnSettings(BiomeEnvironment.goldenMountainMobs().build())
            .specialEffects(BiomeEnvironment.goldenMountainAmbience().build())
            .generationSettings(BiomeEnvironment.goldenMountainFeatures().build())
            .build();

}
