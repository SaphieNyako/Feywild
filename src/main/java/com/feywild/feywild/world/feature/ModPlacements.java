package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Random;

public class ModPlacements {

    /* SPRING */
    public static final PlacedFeature SPRING_TREES = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "spring_trees",
            ModTrees.springTree.getConfiguredFeature(new Random(), true).decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.5f, 3)))
    );

    public static final PlacedFeature SPRING_DANDELION = registerFeature("dandelion_feature",
            ModFeatures.dandelions.configured(FeatureConfiguration.NONE)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
    );

    //Spring Flowers
    public static final RandomPatchConfiguration SPRING_FLOWER_CONFIG = new RandomPatchConfiguration.GrassConfigurationBuilder(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.DANDELION.defaultBlockState(), 3)
                    .add(Blocks.POPPY.defaultBlockState(), 1)
                    .add(Blocks.ALLIUM.defaultBlockState(), 1)
                    .add(Blocks.AZURE_BLUET.defaultBlockState(), 1)
                    .add(Blocks.RED_TULIP.defaultBlockState(), 1)
                    .add(Blocks.ORANGE_TULIP.defaultBlockState(), 1)
                    .add(Blocks.WHITE_TULIP.defaultBlockState(), 1)
                    .add(Blocks.PINK_TULIP.defaultBlockState(), 1)
                    .add(Blocks.OXEYE_DAISY.defaultBlockState(), 1)
                    .add(Blocks.CORNFLOWER.defaultBlockState(), 1)
                    .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 1)
                    .build()),
            SimpleBlockPlacer.INSTANCE
    ).tries(64).build();

    public static final PlacedFeature SPRING_FLOWERS = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(SPRING_FLOWER_CONFIG)
                    .decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(100)
    );

    /* SUMMER */
    public static final PlacedFeature SUMMER_TREES = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "summer_trees",
            ModTrees.summerTree.getConfiguredFeature(new Random(), true)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.5f, 2)))
    );

    public static final PlacedFeature SUMMER_SUNFLOWER = registerFeature("sunflower_feature",
            ModFeatures.sunflowers.configured(FeatureConfiguration.NONE)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
    );

    //WARM FLOWERS
    public static final RandomPatchConfiguration SUMMER_WARM_FLOWERS_CONFIG = new RandomPatchConfiguration.GrassConfigurationBuilder(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.POPPY.defaultBlockState(), 2)
                    .add(Blocks.DANDELION.defaultBlockState(), 1)
                    .build()),
            SimpleBlockPlacer.INSTANCE
    ).tries(64).build();

    public static final PlacedFeature SUMMER_WARM_FLOWERS = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(SUMMER_WARM_FLOWERS_CONFIG)
                    .decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4)
    );

    /* AUTUMN */
    public static final PlacedFeature AUTUMN_TREES = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "autumn_trees",
            ModTrees.autumnTree.getConfiguredFeature(new Random(), true)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.5f, 5)))
    );

    public static final PlacedFeature AUTUMN_PUMPKINS = registerFeature("autumn_pumpkins",
            ModFeatures.autumnPumpkins.configured(FeatureConfiguration.NONE)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
    );

    //SWAMP FLOWERS

    public static final RandomPatchConfiguration AUTUMN_SWAMP_FLOWERS_CONFIG = new RandomPatchConfiguration.GrassConfigurationBuilder(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.BLUE_ORCHID.defaultBlockState(), 2)
                    .build()),
            SimpleBlockPlacer.INSTANCE
    ).tries(64).build();

    public static final PlacedFeature AUTUMN_SWAMP_FLOWERS = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(AUTUMN_SWAMP_FLOWERS_CONFIG)
                    .decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE)
    );

    //SMALL MUSHROOMS

    public static final RandomPatchConfiguration AUTUMN_SMALL_MUSHROOMS_CONFIG = new RandomPatchConfiguration.GrassConfigurationBuilder(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 2)
                    .add(Blocks.RED_MUSHROOM.defaultBlockState(), 1)
                    .build()),
            SimpleBlockPlacer.INSTANCE
    ).tries(32).build();

    public static final PlacedFeature AUTUMN_SMALL_MUSHROOMS = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(AUTUMN_SMALL_MUSHROOMS_CONFIG)
                    //.decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(2)
    );

    /* WINTER */
    public static final PlacedFeature WINTER_TREES = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "winter_trees",
            ModTrees.winterTree.getConfiguredFeature(new Random(), true)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.3f, 2)))
    );

    //Winter Flowers
    public static final RandomPatchConfiguration WINTER_FLOWER_CONFIG = new RandomPatchConfiguration.GrassConfigurationBuilder(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 2)
                    .add(Blocks.POPPY.defaultBlockState(), 1)
                    .build()),
            SimpleBlockPlacer.INSTANCE
    ).tries(64).build();

    public static final PlacedFeature WINTER_FLOWERS = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(WINTER_FLOWER_CONFIG)
                    .decorated(Features.Decorators.ADD_32)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(2)
    );

    public static final PlacedFeature WINTER_CROCUS = registerFeature("crocus_feature",
            ModFeatures.crocus.configured(FeatureConfiguration.NONE)
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
    );

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> registerFeature(String key, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, key), feature);
    }

}
