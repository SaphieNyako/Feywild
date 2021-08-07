package com.feywild.feywild.world.biome.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Supplier;

public abstract class BaseBiome {

    public abstract Biome biomeSetup(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale);

    protected void addDefaultSpawns(MobSpawnInfo.Builder mobSpawnBuilder) {
        DefaultBiomeFeatures.farmAnimals(mobSpawnBuilder);
        DefaultBiomeFeatures.commonSpawns(mobSpawnBuilder);
    }
}
