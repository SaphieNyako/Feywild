package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;

public class ModConfiguredFeatures {

    /* SPRING */
    public static final ConfiguredFeature<?, ?> SPRING_TREES = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "spring_trees",
            ModTrees.springTree.getConfiguredFeature(new Random(), true).decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.5f, 3)))
    );

    public static final ConfiguredFeature<?, ?> SPRING_DANDELION = registerFeature("dandelion_feature",
            ModFeatures.dandelions.configured(IFeatureConfig.NONE)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
    );

    //Spring Flowers
    public static final BlockClusterFeatureConfig SPRING_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
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
            .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 1),
            SimpleBlockPlacer.INSTANCE
    )).tries(64).build();

    public static final ConfiguredFeature<?, ?> SPRING_FLOWERS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(SPRING_FLOWER_CONFIG)
                    .decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(100)
    );

    /* SUMMER */
    public static final ConfiguredFeature<?, ?> SUMMER_TREES = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "summer_trees",
            ModTrees.summerTree.getConfiguredFeature(new Random(), true)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.5f, 2)))
    );

    public static final ConfiguredFeature<?, ?> SUMMER_SUNFLOWER = registerFeature("sunflower_feature",
            ModFeatures.sunflowers.configured(IFeatureConfig.NONE)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
    );

    //WARM FLOWERS
    public static final BlockClusterFeatureConfig SUMMER_WARM_FLOWERS_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
            .add(Blocks.POPPY.defaultBlockState(), 2)
            .add(Blocks.DANDELION.defaultBlockState(), 1),
            SimpleBlockPlacer.INSTANCE
    )).tries(64).build();

    public static final ConfiguredFeature<?, ?> SUMMER_WARM_FLOWERS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(SUMMER_WARM_FLOWERS_CONFIG)
                    .decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4)
    );

    /* AUTUMN */
    public static final ConfiguredFeature<?, ?> AUTUMN_TREES = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "autumn_trees",
            ModTrees.autumnTree.getConfiguredFeature(new Random(), true)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.5f, 5)))
    );

    public static final ConfiguredFeature<?, ?> AUTUMN_PUMPKINS = registerFeature("autumn_pumpkins",
            ModFeatures.autumnPumpkins.configured(IFeatureConfig.NONE)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
    );

    //SWAMP FLOWERS

    public static final BlockClusterFeatureConfig AUTUMN_SWAMP_FLOWERS_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
            .add(Blocks.BLUE_ORCHID.defaultBlockState(), 2),
            SimpleBlockPlacer.INSTANCE
    )).tries(64).build();

    public static final ConfiguredFeature<?, ?> AUTUMN_SWAMP_FLOWERS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(AUTUMN_SWAMP_FLOWERS_CONFIG)
                    .decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE)
    );

    //SMALL MUSHROOMS

    public static final BlockClusterFeatureConfig AUTUMN_SMALL_MUSHROOMS_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
            .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 2)
            .add(Blocks.RED_MUSHROOM.defaultBlockState(), 1),
            SimpleBlockPlacer.INSTANCE
    )).tries(32).build();

    public static final ConfiguredFeature<?, ?> AUTUMN_SMALL_MUSHROOMS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(AUTUMN_SMALL_MUSHROOMS_CONFIG)
                    //.decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE));
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2)
    );

    /* WINTER */
    public static final ConfiguredFeature<?, ?> WINTER_TREES = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "winter_trees",
            ModTrees.winterTree.getConfiguredFeature(new Random(), true)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.3f, 2)))
    );

    //Winter Flowers
    public static final BlockClusterFeatureConfig WINTER_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
            .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 2)
            .add(Blocks.POPPY.defaultBlockState(), 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();

    public static final ConfiguredFeature<?, ?> WINTER_FLOWERS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "flower_default",
            Feature.FLOWER.configured(WINTER_FLOWER_CONFIG)
                    .decorated(Features.Placements.ADD_32)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2)
    );

    public static final ConfiguredFeature<?, ?> WINTER_CROCUS = registerFeature("crocus_feature",
            ModFeatures.crocus.configured(IFeatureConfig.NONE)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
    );

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerFeature(String key, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, key), feature);
    }

}
