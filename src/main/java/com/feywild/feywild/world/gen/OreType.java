package com.feywild.feywild.world.gen;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.WorldGenConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;

import java.util.Objects;

public enum OreType {
    FEY_GEM_ORE(ModBlocks.feyGemBlock,
            ModBlocks.feyGemBlockLivingrock,
            WorldGenConfig.ores.fey_gem.size,
            WorldGenConfig.ores.fey_gem.min_height,
            WorldGenConfig.ores.fey_gem.max_height,
            WorldGenConfig.ores.fey_gem.weight
    );

    private final Block block;
    private final Block alfheimBlock;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;
    private final int spawnWeight;
    private final LazyLoadedValue<ConfiguredFeature<?, ?>> feature;
    private final LazyLoadedValue<ConfiguredFeature<?, ?>> alfheimFeature;

    OreType(Block block, Block alfheimBlock, int maxVeinSize, int minHeight, int maxHeight, int spawnWeight) {
        this.block = block;
        this.alfheimBlock = alfheimBlock;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.spawnWeight = spawnWeight;
        this.feature = new LazyLoadedValue<>(() -> {
            OreConfiguration oreFeatureConfig = new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, block.defaultBlockState(), maxVeinSize);
            ConfiguredDecorator<?> configuredPlacement = FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(minHeight, 0, maxHeight)).squared().count(spawnWeight);
            return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.ORE.configured(oreFeatureConfig).decorated(configuredPlacement));
        });
        this.alfheimFeature = new LazyLoadedValue<>(() -> {
            OreConfiguration oreFeatureConfig = new OreConfiguration(FeywildOreGen.ALFHEIM_STONE, alfheimBlock.defaultBlockState(), maxVeinSize);
            ConfiguredDecorator<?> configuredPlacement = FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(minHeight, 0, maxHeight)).squared().count(spawnWeight);
            return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.ORE.configured(oreFeatureConfig).decorated(configuredPlacement));
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
        return this.block;
    }

    public Block getAlfheimBlock() {
        return alfheimBlock;
    }

    public int getMaxVeinSize() {
        return this.maxVeinSize;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getSpawnWeight() {
        return this.spawnWeight;
    }

    public ConfiguredFeature<?, ?> getFeature() {
        return this.feature.get();
    }
    
    public ConfiguredFeature<?, ?> getAlfheimFeature() {
        return this.alfheimFeature.get();
    }

    public static void setupOres() {
        for (OreType ore : values()) {
            // Will trigger registration
            ore.getFeature();
        }
    }
}
