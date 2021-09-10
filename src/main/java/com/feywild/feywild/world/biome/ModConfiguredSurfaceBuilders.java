package com.feywild.feywild.world.biome;

import com.feywild.feywild.FeywildMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ModConfiguredSurfaceBuilders {

    public static final ConfiguredSurfaceBuilder<?> SPRING_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "spring_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());
    public static final ConfiguredSurfaceBuilder<?> SUMMER_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "summer_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());
    public static final ConfiguredSurfaceBuilder<?> AUTUMN_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "autumn_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());
    public static final ConfiguredSurfaceBuilder<?> WINTER_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "winter_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SNOW_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());

    private static ConfiguredSurfaceBuilder<?> registerConfiguredSurfaceBuilder(ResourceLocation surfaceBuilderRecourseLocation, BlockState topBlock, BlockState fillerBlock, BlockState underwaterBlock) {
        return Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, surfaceBuilderRecourseLocation, ModSurfaceBuilders.loggingDefault.configured(new SurfaceBuilderConfig(topBlock, fillerBlock, underwaterBlock)));
    }
}
