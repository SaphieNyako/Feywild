package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.AutumnTree;
import com.feywild.feywild.block.trees.SpringTree;
import com.feywild.feywild.block.trees.SummerTree;
import com.feywild.feywild.block.trees.WinterTree;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.Set;


public class ModConfiguredFeatures {


    protected static final BlockState PODZOL_STATE = Blocks.PODZOL.getDefaultState();
    protected static final BlockState GRASS_BLOCK_STATE = Blocks.GRASS_BLOCK.getDefaultState();


    /* SPRING */
    public static final ConfiguredFeature<?,?> SPRING_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "spring_trees",
                    Feature.TREE.withConfiguration(new SpringTree().getTreeFeature(new Random(), true).getConfig())
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.5f, 3))));

    /* SUMMER */
    public static final ConfiguredFeature<?,?> SUMMER_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "summer_trees",
                    Feature.TREE.withConfiguration(new SummerTree().getTreeFeature(new Random(), true).getConfig())
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.5f, 2))));

    /* AUTUMN */
    public static final ConfiguredFeature<?,?> AUTUMN_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "autumn_trees",
                    Feature.TREE.withConfiguration(new AutumnTree().getTreeFeature(new Random(), true).getConfig())
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.5f, 5))));




/*
    public static final ConfiguredFeature<?, ?> AUTUMN_PUMPKINS = register("autumn_pumpkins",
            ModFeatures.AUTUMN_PUMPKINS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT));

*/

    // THIS WORKS BUT NOT WHAT I WANTED
    public static final BlockClusterFeatureConfig AUTUMN_PUMPKIN_CONFIG =
            (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .addWeightedBlockstate(Blocks.PUMPKIN.getDefaultState(), 19)
                    .addWeightedBlockstate(Blocks.JACK_O_LANTERN.getDefaultState(), 1)
                    .addWeightedBlockstate(Blocks.CARVED_PUMPKIN.getDefaultState(),1), SimpleBlockPlacer.PLACER)).tries(16)
                    .whitelist(ImmutableSet.of(GRASS_BLOCK_STATE.getBlock())).func_227317_b_().build();


    public static final ConfiguredFeature<?, ?> AUTUMN_PUMPKINS =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,"autumn_pumpkins",
                    Feature.RANDOM_PATCH.withConfiguration(AUTUMN_PUMPKIN_CONFIG)
                            .withPlacement(Features.Placements.PATCH_PLACEMENT).chance(32));


    /* WINTER */
    public static final ConfiguredFeature<?,?> WINTER_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "winter_trees",
                    Feature.TREE.withConfiguration(new WinterTree().getTreeFeature(new Random(), true).getConfig())
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.3f, 2))));

    //Winter Flowers
    public static final BlockClusterFeatureConfig WINTER_FLOWER_CONFIG =
            (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .addWeightedBlockstate(Blocks.LILY_OF_THE_VALLEY.getDefaultState(), 2)
                    .addWeightedBlockstate(Blocks.POPPY.getDefaultState(), 1), SimpleBlockPlacer.PLACER)).tries(64).build();


    public static final ConfiguredFeature<?, ?> WINTER_FLOWERS =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,"flower_default",
                    Feature.FLOWER.withConfiguration(WINTER_FLOWER_CONFIG)
                            .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
                            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));

/*
    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(FeywildMod.MOD_ID, key), feature);
    }
    */
}
