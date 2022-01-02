package com.feywild.feywild.world.gen;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.config.data.OreData;
import io.github.noeppi_noeppi.libx.util.LazyValue;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Objects;
import java.util.function.Supplier;

public enum OreType {
    FEY_GEM_ORE(ModBlocks.feyGemBlock,
            ModBlocks.feyGemBlockLivingrock,
            () -> WorldGenConfig.ores.fey_gem
    );

    private final Block block;
    private final Block alfheimBlock;
    private final Supplier<OreData> data; 
    private final LazyValue<PlacedFeature> feature;
    private final LazyValue<PlacedFeature> alfheimFeature;

    OreType(Block block, Block alfheimBlock, Supplier<OreData> data) {
        this.block = block;
        this.alfheimBlock = alfheimBlock;
        this.data = data;
        this.feature = new LazyValue<>(() -> {
            OreConfiguration oreFeatureConfig = new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, block.defaultBlockState(), getMaxVeinSize());
            ConfiguredDecorator<?> configuredPlacement = FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(getHeight())).squared().count(getSpawnWeight());
            return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.ORE.configured(oreFeatureConfig).decorated(configuredPlacement));
        });
        this.alfheimFeature = new LazyValue<>(() -> {
            OreConfiguration oreFeatureConfig = new OreConfiguration(FeywildOreGen.ALFHEIM_STONE, alfheimBlock.defaultBlockState(), getMaxVeinSize());
            ConfiguredDecorator<?> configuredPlacement = FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(getHeight())).squared().count(getSpawnWeight());
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
        return this.data.get().size();
    }

    public int getMinHeight() {
        return this.data.get().min_height();
    }

    public int getMaxHeight() {
        return this.data.get().max_height();
    }

    public int getSpawnWeight() {
        return this.data.get().weight();
    }

    public PlacedFeature getFeature() {
        return this.feature.get();
    }
    
    public PlacedFeature getAlfheimFeature() {
        return this.alfheimFeature.get();
    }

    public HeightProvider getHeight() {
        return UniformHeight.of(VerticalAnchor.absolute(getMinHeight()), VerticalAnchor.absolute(getMaxHeight()));
    }
    
    public static void setupOres() {
        for (OreType ore : values()) {
            // Will trigger registration
            ore.getFeature();
        }
    }
}
