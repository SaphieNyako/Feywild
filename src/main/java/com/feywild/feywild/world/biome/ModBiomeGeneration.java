package com.feywild.feywild.world.biome;

import com.feywild.feywild.FeywildMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomeGeneration {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void register(final RegistryEvent.Register<SurfaceBuilder<?>> event) {

        registerConfiguredSurfaceBuilder(ModConfiguredSurfaceBuilders.SPRING_SURFACE.location(),
                Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());

        registerConfiguredSurfaceBuilder(ModConfiguredSurfaceBuilders.SUMMER_SURFACE.location(),
                Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());

        registerConfiguredSurfaceBuilder(ModConfiguredSurfaceBuilders.AUTUMN_SURFACE.location(),
                Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());

        registerConfiguredSurfaceBuilder(ModConfiguredSurfaceBuilders.WINTER_SURFACE.location(),
                Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SNOW_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState());

    }

    @SubscribeEvent
    public static void setupBiome(final FMLCommonSetupEvent event) {

        event.enqueueWork(() ->
        {

            addBiome(ModBiomes.SPRING_BIOME.get(), BiomeManager.BiomeType.WARM, 20, MAGICAL, FOREST);
            addBiome(ModBiomes.SUMMER_BIOME.get(), BiomeManager.BiomeType.WARM, 20, MAGICAL, HOT);
            addBiome(ModBiomes.AUTUMN_BIOME.get(), BiomeManager.BiomeType.WARM, 20, MAGICAL, MUSHROOM);
            addBiome(ModBiomes.WINTER_BIOME.get(), BiomeManager.BiomeType.WARM, 20, MAGICAL, COLD);
        });
    }

    private static void registerConfiguredSurfaceBuilder(ResourceLocation surfaceBuilderRecourseLocation, BlockState topBlock, BlockState fillerBlock, BlockState underwaterBlock) {

        Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, surfaceBuilderRecourseLocation, ModSurfaceBuilders.LOGGING_DEFAULT.get().configured(
                new SurfaceBuilderConfig(topBlock, fillerBlock, underwaterBlock)));
    }

    private static void addBiome(Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {

        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES,
                Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome)));

        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));

    }

}
