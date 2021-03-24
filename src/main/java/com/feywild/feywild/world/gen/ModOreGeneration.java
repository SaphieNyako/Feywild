package com.feywild.feywild.world.gen;


import com.feywild.feywild.FeywildMod;
import com.google.common.collect.Lists;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

//Subscribe for event using Mod ID
@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModOreGeneration {

    @SubscribeEvent
    public static void generateOres(FMLLoadCompleteEvent event)
    {
        for(OreType ore : OreType.values())
        {
            //replace base stone with the ore.
            OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                    ore.getBlock().getDefaultState(), ore.getMaxVeinSize());

            //determines placement height and spawn weight
            ConfiguredPlacement configuredPlacement = Placement.RANGE.configure(
                    new TopSolidRangeConfig(ore.getMinHeight(),0 , ore.getMaxHeight())).square()
                    .func_242731_b(ore.getSpawnWeight());

            //registration
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
                    ore.getBlock().getRegistryName(),
                    Feature.ORE.withConfiguration(oreFeatureConfig).withPlacement(configuredPlacement));

            //determines wich biome to spawn in
            for(Biome biome : ForgeRegistries.BIOMES)
            {
                // not NETHER or THEEND, can also specify specific biomes.
                if(!biome.getCategory().equals(Biome.Category.NETHER)
                        && !biome.getCategory().equals(Biome.Category.THEEND))
                {
                    addFeatureToBiome(biome, GenerationStage.Decoration.UNDERGROUND_ORES,
                            WorldGenRegistries.CONFIGURED_FEATURE.getOrDefault(ore.getBlock().getRegistryName()));
                }
            }
        }
    }


    private static void addFeatureToBiome(Biome biome, GenerationStage.Decoration decoration
            , ConfiguredFeature<?, ?> configuredFeature) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(
                biome.getGenerationSettings().getFeatures()
        );

        while (biomeFeatures.size() <= decoration.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }

        List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
        features.add(() -> configuredFeature);
        biomeFeatures.set(decoration.ordinal(), features);

        /* Change field_242484_f that contains the Configured Features of the Biome */
        ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(),
                biomeFeatures, "field_242484_f");
    }

}
