package com.feywild.feywild.world.biome;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

public class ModConfiguredSurfaceBuilders {

    public static final ConfiguredSurfaceBuilder<?> SPRING_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "spring_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT.defaultBlockState(), Blocks.SAND.defaultBlockState());
    public static final ConfiguredSurfaceBuilder<?> SUMMER_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "summer_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT.defaultBlockState(), Blocks.SAND.defaultBlockState());
    public static final ConfiguredSurfaceBuilder<?> AUTUMN_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "autumn_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT.defaultBlockState(), Blocks.SAND.defaultBlockState());
    public static final ConfiguredSurfaceBuilder<?> WINTER_SURFACE = registerConfiguredSurfaceBuilder(new ResourceLocation(FeywildMod.getInstance().modid, "winter_surface"), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SNOW_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());

    private static ConfiguredSurfaceBuilder<?> registerConfiguredSurfaceBuilder(ResourceLocation surfaceBuilderRecourseLocation, BlockState topBlock, BlockState fillerBlock, BlockState underwaterBlock) {
        return Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, surfaceBuilderRecourseLocation, SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderBaseConfiguration(topBlock, fillerBlock, underwaterBlock)));
    }
}
