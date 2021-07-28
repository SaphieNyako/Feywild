package com.feywild.feywild.world.gen;

import com.feywild.feywild.FeywildMod;
import com.google.common.collect.Lists;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
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
    public static void generateOres(FMLLoadCompleteEvent event) {
        for (OreType ore : OreType.values()) {
            //replace base stone with the ore.
            OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ore.getBlock().defaultBlockState(), ore.getMaxVeinSize());

            //determines placement height and spawn weight
            ConfiguredPlacement configuredPlacement = Placement.RANGE.configured(
                    new TopSolidRangeConfig(ore.getMinHeight(), 0, ore.getMaxHeight())).squared()
                    .count(ore.getSpawnWeight());

            //registration
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
                    ore.getBlock().getRegistryName(),
                    Feature.ORE.configured(oreFeatureConfig).decorated(configuredPlacement));

            for (Biome biome : ForgeRegistries.BIOMES) {
                if (!biome.getBiomeCategory().equals(Biome.Category.NETHER)
                        && !biome.getBiomeCategory().equals(Biome.Category.THEEND)) {
                    addFeatureToBiome(biome, GenerationStage.Decoration.UNDERGROUND_ORES,
                            WorldGenRegistries.CONFIGURED_FEATURE.get(ore.getBlock().getRegistryName()));
                }
            }
        }
    }

    private static void addFeatureToBiome(Biome biome, GenerationStage.Decoration decoration
            , ConfiguredFeature<?, ?> configuredFeature
    ) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(
                biome.getGenerationSettings().features()
        );

        while (biomeFeatures.size() <= decoration.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }

        List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
        features.add(() -> configuredFeature);
        biomeFeatures.set(decoration.ordinal(), features);

        // Change features that contains the Configured Features of the Biome
        ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(),
                biomeFeatures, "field_242484_f");
    }

}
