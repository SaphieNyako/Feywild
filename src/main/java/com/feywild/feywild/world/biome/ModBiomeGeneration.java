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

        /* SPRING */
        registerBiome(ModConfiguredSurfaceBuilders.SPRING_SURFACE.getLocation(),
                Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SAND.getDefaultState());
        /* SUMMER */
        registerBiome(ModConfiguredSurfaceBuilders.SUMMER_SURFACE.getLocation(),
                Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SAND.getDefaultState());
        /* AUTUMN */
        registerBiome(ModConfiguredSurfaceBuilders.AUTUMN_SURFACE.getLocation(),
                Blocks.GRASS_BLOCK.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SAND.getDefaultState());
        /* WINTER */
        registerBiome(ModConfiguredSurfaceBuilders.WINTER_SURFACE.getLocation(),
                Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), Blocks.SAND.getDefaultState());

    }

   @SubscribeEvent
    public static void setupBiome(final FMLCommonSetupEvent event) {

        event.enqueueWork(() ->
        {
            addBiome(ModBiomes.SPRING_BIOME.get(), BiomeManager.BiomeType.WARM, 100, MAGICAL, FOREST);
            addBiome(ModBiomes.SUMMER_BIOME.get(), BiomeManager.BiomeType.WARM, 100, MAGICAL, HOT);
            addBiome(ModBiomes.AUTUMN_BIOME.get(), BiomeManager.BiomeType.WARM, 100, MAGICAL, MUSHROOM);
            addBiome(ModBiomes.WINTER_BIOME.get(), BiomeManager.BiomeType.WARM, 100, MAGICAL, COLD);
        });

    }

    private static void registerBiome(ResourceLocation biomeResourceLocation, BlockState topBlock, BlockState fillerBlock, BlockState underwaterBlock) {

        Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, biomeResourceLocation, ModSurfaceBuilders.LOGGING_DEFAULT.get().func_242929_a(
                new SurfaceBuilderConfig(topBlock, fillerBlock, underwaterBlock)));
    }

    private static void addBiome(Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {

        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES,
                Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome)));

        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));

    }

}
