package com.feywild.feywild.world.feature.ores;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.WorldGenConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class OresConfiguredFeatures {

    //TODO add LIVINGROCK REPLACEMENT

    // public static final RuleTest ALFHEIM_LIVINGROCK_REPLACEABLES = new TagMatchTest(BlockTags.LIVINGROCK_REPLACEABLES);

    public static final List<OreConfiguration.TargetBlockState> FEY_GEM_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.feyGemBlock.defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.feyGemBlockDeepSlate.defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> FEY_GEM_ORE = FeatureUtils.register("fey_gem_ore",
            Feature.ORE, new OreConfiguration(FEY_GEM_ORES, WorldGenConfig.ores.fey_gem.size()));

}
