package com.saphienyako.feywild.worldgen;

import com.google.common.base.Suppliers;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Feywild.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORANGE_MUSHROOM =
            CONFIGURED_FEATURES.register("orange_mushroom",
                    () -> new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM,
                            colouredMushroomConfig(ModBlocks.ORANGE_MUSHROOM_BLOCK.get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> YELLOW_MUSHROOM =
            CONFIGURED_FEATURES.register("yellow_mushroom",
                    () -> new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM,
                            colouredMushroomConfig(ModBlocks.YELLOW_MUSHROOM_BLOCK.get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GREEN_MUSHROOM =
            CONFIGURED_FEATURES.register("green_mushroom",
                    () -> new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM,
                            colouredMushroomConfig(ModBlocks.GREEN_MUSHROOM_BLOCK.get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> LIGHT_BLUE_MUSHROOM =
            CONFIGURED_FEATURES.register("light_blue_mushroom",
                    () -> new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM,
                            colouredMushroomConfig(ModBlocks.LIGHT_BLUE_MUSHROOM_BLOCK.get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> BLUE_MUSHROOM =
            CONFIGURED_FEATURES.register("blue_mushroom",
                    () -> new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM,
                            colouredMushroomConfig(ModBlocks.BLUE_MUSHROOM_BLOCK.get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PURPLE_MUSHROOM =
            CONFIGURED_FEATURES.register("purple_mushroom",
                    () -> new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM,
                            colouredMushroomConfig(ModBlocks.PURPLE_MUSHROOM_BLOCK.get())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PINK_MUSHROOM =
            CONFIGURED_FEATURES.register("pink_mushroom",
                    () -> new ConfiguredFeature<>(Feature.HUGE_RED_MUSHROOM,
                            colouredMushroomConfig(ModBlocks.PINK_MUSHROOM_BLOCK.get())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> FEY_GEM_ORES =
            Suppliers.memoize(() -> List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                            ModBlocks.FEY_GEM_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                            ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get().defaultBlockState())
            ));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FEY_GEM_ORE =
            CONFIGURED_FEATURES.register("fey_gem_ore",
                    () -> new ConfiguredFeature<>(Feature.ORE,
                            new OreConfiguration(FEY_GEM_ORES.get(), 5)));

    private static HugeMushroomFeatureConfiguration colouredMushroomConfig(Block mushroomBlock) {
        return new HugeMushroomFeatureConfiguration(
                BlockStateProvider.simple(
                        mushroomBlock.defaultBlockState()
                                .setValue(HugeMushroomBlock.UP, true)
                                .setValue(HugeMushroomBlock.DOWN, false)
                                .setValue(HugeMushroomBlock.NORTH, true)
                                .setValue(HugeMushroomBlock.SOUTH, true)
                                .setValue(HugeMushroomBlock.EAST, true)
                                .setValue(HugeMushroomBlock.WEST, true)
                ),
                BlockStateProvider.simple(
                        Blocks.MUSHROOM_STEM.defaultBlockState()
                                .setValue(HugeMushroomBlock.UP, false)
                                .setValue(HugeMushroomBlock.DOWN, false)
                ),
                2
        );
    }


    public static void register(IEventBus bus) {
        CONFIGURED_FEATURES.register(bus);
    }
}
