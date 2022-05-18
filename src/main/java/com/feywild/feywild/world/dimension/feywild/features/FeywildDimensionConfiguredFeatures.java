package com.feywild.feywild.world.dimension.feywild.features;

import com.feywild.feywild.block.ModTrees;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

@RegisterClass(prefix = "wg", priority = -2)
public class FeywildDimensionConfiguredFeatures {

    //TODO TREES

    public static final Holder<ConfiguredFeature<?, ?>> autumnPumpkins = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(FeywildDimensionWorldGeneration.autumnPumpkins, NoneFeatureConfiguration.INSTANCE));

    public static final Holder<ConfiguredFeature<?, ?>> sunflowers = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(FeywildDimensionWorldGeneration.sunflowers, NoneFeatureConfiguration.INSTANCE));

    public static final Holder<ConfiguredFeature<?, ?>> dandelions = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(FeywildDimensionWorldGeneration.dandelions, NoneFeatureConfiguration.INSTANCE));

    public static final Holder<ConfiguredFeature<?, ?>> crocus = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(FeywildDimensionWorldGeneration.crocus, NoneFeatureConfiguration.INSTANCE));

    public static final Holder<ConfiguredFeature<?, ?>> springTrees = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(Feature.TREE, (ModTrees.springTree.getFeatureBuilder(new Random(), true)).build()));

    public static final Holder<ConfiguredFeature<?, ?>> autumnTrees = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(Feature.TREE, (ModTrees.autumnTree.getFeatureBuilder(new Random(), true)).build()));

    public static final Holder<ConfiguredFeature<?, ?>> summerTrees = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(Feature.TREE, (ModTrees.summerTree.getFeatureBuilder(new Random(), true)).build()));

    public static final Holder<ConfiguredFeature<?, ?>> winterTrees = new NoneFeatureHolder<>(Registry.CONFIGURED_FEATURE_REGISTRY,
            new ConfiguredFeature<>(Feature.TREE, (ModTrees.winterTree.getFeatureBuilder(new Random(), true)).build()));

}

