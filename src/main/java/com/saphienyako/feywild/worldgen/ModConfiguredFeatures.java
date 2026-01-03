package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class ModConfiguredFeatures {


    public static final List<OreConfiguration.TargetBlockState> FEY_GEM_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.FEY_GEM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> FEY_GEM_ORE = FeatureUtils.register("fey_gem_ore",
            Feature.ORE, new OreConfiguration(FEY_GEM_ORES, 9));
}
