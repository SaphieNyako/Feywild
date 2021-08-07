package com.feywild.feywild.world.biome;

import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

@RegisterClass
public class ModSurfaceBuilders {

    public static final SurfaceBuilder<SurfaceBuilderConfig> loggingDefault = new LoggingSurfaceBuilder<>(() -> SurfaceBuilder.DEFAULT, SurfaceBuilderConfig.CODEC);
}
