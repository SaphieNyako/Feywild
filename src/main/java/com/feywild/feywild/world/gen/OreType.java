package com.feywild.feywild.world.gen;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.util.configs.Config;
import net.minecraft.block.Block;
import net.minecraft.util.LazyValue;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

import java.util.Objects;

public enum OreType {
    FEY_GEM_ORE(ModBlocks.feyGemBlock, Config.FEY_GEM_CONFIG.getCachedSize(), Config.FEY_GEM_CONFIG.getCachedMinHeight(), Config.FEY_GEM_CONFIG.getCachedMaxHeight(), Config.FEY_GEM_CONFIG.getCachedWeight());

    private final Block block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;
    private final int spawnWeight;
    private final LazyValue<ConfiguredFeature<?, ?>> feature;

    OreType(Block block, int maxVeinSize, int minHeight, int maxHeight, int spawnWeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.spawnWeight = spawnWeight;
        this.feature = new LazyValue<>(() -> {
            OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, block.defaultBlockState(), maxVeinSize);
            ConfiguredPlacement<?> configuredPlacement = Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)).squared().count(spawnWeight);
            return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.ORE.configured(oreFeatureConfig).decorated(configuredPlacement));
        });
    }

    public static OreType get(Block block) {
        for (OreType ore : values()) {
            if (block == ore.block) {
                return ore;
            }
        }
        return null;
    }

    public Block getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getSpawnWeight() {
        return spawnWeight;
    }

    public ConfiguredFeature<?, ?> getFeature() {
        return feature.get();
    }
}
