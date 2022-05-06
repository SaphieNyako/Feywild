package com.feywild.feywild.world.feature.flowers;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class FlowersConfiguredFeatures {

    //TODO SET SPAWN RATE
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SPRING_FLOWERS =
            FeatureUtils.register("spring_flowers", Feature.FLOWER,
                    new RandomPatchConfiguration(96, 6, 2,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(new NoiseProvider(2345L,
                                            new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F,
                                            List.of(Blocks.DANDELION.defaultBlockState(),
                                                    Blocks.POPPY.defaultBlockState(),
                                                    Blocks.ALLIUM.defaultBlockState(),
                                                    Blocks.AZURE_BLUET.defaultBlockState(),
                                                    Blocks.RED_TULIP.defaultBlockState(),
                                                    Blocks.ORANGE_TULIP.defaultBlockState(),
                                                    Blocks.WHITE_TULIP.defaultBlockState(),
                                                    Blocks.PINK_TULIP.defaultBlockState(),
                                                    Blocks.OXEYE_DAISY.defaultBlockState(),
                                                    Blocks.CORNFLOWER.defaultBlockState(),
                                                    Blocks.LILY_OF_THE_VALLEY.defaultBlockState()))))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> AUTUMN_FLOWERS =
            FeatureUtils.register("autumn_flowers", Feature.FLOWER,
                    new RandomPatchConfiguration(64, 6, 2,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BLUE_ORCHID)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> SUMMER_FLOWERS =
            FeatureUtils.register("summer_flowers", Feature.FLOWER,
                    new RandomPatchConfiguration(96, 6, 2,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(new NoiseProvider(2345L,
                                            new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F,
                                            List.of(Blocks.DANDELION.defaultBlockState(),
                                                    Blocks.POPPY.defaultBlockState())
                                    )))));
    //PATCH_SUNFLOWER
    //PATCH_MELON

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> WINTER_FLOWERS =
            FeatureUtils.register("winter_flowers", Feature.FLOWER,
                    new RandomPatchConfiguration(96, 6, 2,
                            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                    new SimpleBlockConfiguration(new NoiseProvider(2345L,
                                            new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F,
                                            List.of(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
                                                    Blocks.POPPY.defaultBlockState())
                                    )))));

}
