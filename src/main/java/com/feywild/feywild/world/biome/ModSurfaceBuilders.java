package com.feywild.feywild.world.biome;

import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

@RegisterClass
public class ModSurfaceBuilders {

    public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> loggingDefault = new LoggingSurfaceBuilder<>(() -> SurfaceBuilder.DEFAULT, SurfaceBuilderBaseConfiguration.CODEC);
}
