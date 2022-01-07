package com.feywild.feywild.world.gen;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.config.data.OreData;
import io.github.noeppi_noeppi.libx.util.LazyValue;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Objects;
import java.util.function.Supplier;

public enum OreType {
    FEY_GEM_ORE(ModBlocks.feyGemBlock, ModBlocks.feyGemBlockDeepSlate,
            ModBlocks.feyGemBlockLivingrock,
            () -> WorldGenConfig.ores.fey_gem
    );

    private final Block stoneBlock;
    private final Block deepSlateBlock;
    private final Block alfheimBlock;
    private final Supplier<OreData> data;

    private final LazyValue<PlacedFeature> stoneFeature;
    private final LazyValue<PlacedFeature> deepSlateFeature;
    private final LazyValue<PlacedFeature> alfheimFeature;

    @SuppressWarnings("deprecation")
    OreType(Block feyGemBlock, Block feyGemBlockDeepSlate, Block alfheimBlock, Supplier<OreData> data) {
        this.stoneBlock = feyGemBlock;
        this.deepSlateBlock = feyGemBlockDeepSlate;
        this.alfheimBlock = alfheimBlock;
        this.data = data;

        this.stoneFeature = new LazyValue<>(() -> {
            OreConfiguration oreFeatureConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, stoneBlock.defaultBlockState(), getMaxVeinSize());

            return Registry.register(BuiltinRegistries.PLACED_FEATURE, Objects.requireNonNull(stoneBlock.getRegistryName()),
                    Feature.ORE.configured(oreFeatureConfig).placed(OrePlacement.commonOrePlacement(getSpawnWeight(),
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(getMinHeight()), VerticalAnchor.absolute(getMaxHeight())))
                    ));
        });

        this.deepSlateFeature = new LazyValue<>(() -> {
            OreConfiguration oreFeatureConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepSlateBlock.defaultBlockState(), getMaxVeinSize());

            return Registry.register(BuiltinRegistries.PLACED_FEATURE, Objects.requireNonNull(deepSlateBlock.getRegistryName()),
                    Feature.ORE.configured(oreFeatureConfig).placed(OrePlacement.commonOrePlacement(getSpawnWeight(),
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(getMinHeight()), VerticalAnchor.absolute(getMaxHeight())))
                    ));
        });

        this.alfheimFeature = new LazyValue<>(() -> {
            OreConfiguration oreFeatureConfig = new OreConfiguration(FeywildOreGen.ALFHEIM_STONE, alfheimBlock.defaultBlockState(), getMaxVeinSize());
            return Registry.register(BuiltinRegistries.PLACED_FEATURE, Objects.requireNonNull(stoneBlock.getRegistryName()),
                    Feature.ORE.configured(oreFeatureConfig).placed(OrePlacement.commonOrePlacement(getSpawnWeight(),
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(getMinHeight()), VerticalAnchor.absolute(getMaxHeight())))
                    ));
        });
    }

    public static OreType get(Block block) {
        for (OreType ore : values()) {
            if (block == ore.stoneBlock) {
                return ore;
            }
        }
        return null;
    }

    public static void setupOres() {
        for (OreType ore : values()) {
            // Will trigger registration
            ore.getStoneFeature();
        }
    }

    public Block getStoneBlock() {
        return this.stoneBlock;
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

    public PlacedFeature getStoneFeature() {
        return this.stoneFeature.get();
    }

    public PlacedFeature getDeepSlateFeature() {
        return this.deepSlateFeature.get();
    }

    public PlacedFeature getAlfheimFeature() {
        return this.alfheimFeature.get();
    }

    public HeightProvider getHeight() {
        return UniformHeight.of(VerticalAnchor.absolute(getMinHeight()), VerticalAnchor.absolute(getMaxHeight()));
    }
}
