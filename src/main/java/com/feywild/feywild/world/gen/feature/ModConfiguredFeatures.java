package com.feywild.feywild.world.gen.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModConfiguredFeatures {

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> AUTUMN_TREE;
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> SPRING_TREE;
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> SUMMER_TREE;
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> WINTER_TREE;
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> BLOSSOM_TREE;
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> HEXEN_TREE;

    static {
        AUTUMN_TREE = FeatureUtils.register("feywild:autumn_tree", FeywildFeatures.autumnTree, NoneFeatureConfiguration.INSTANCE);
        SPRING_TREE = FeatureUtils.register("feywild:spring_tree", FeywildFeatures.springTree, NoneFeatureConfiguration.INSTANCE);
        SUMMER_TREE = FeatureUtils.register("feywild:summer_tree", FeywildFeatures.summerTree, NoneFeatureConfiguration.INSTANCE);
        WINTER_TREE = FeatureUtils.register("feywild:winter_tree", FeywildFeatures.winterTree, NoneFeatureConfiguration.INSTANCE);
        BLOSSOM_TREE = FeatureUtils.register("feywild:blossom_tree", FeywildFeatures.blossomTree, NoneFeatureConfiguration.INSTANCE);
        HEXEN_TREE = FeatureUtils.register("feywild:hexen_tree", FeywildFeatures.hexenTree, NoneFeatureConfiguration.INSTANCE);
    }

    public ModConfiguredFeatures() {

    }

}
