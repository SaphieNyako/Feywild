package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.AutumnTree;
import com.feywild.feywild.block.trees.SpringTree;
import com.feywild.feywild.block.trees.SummerTree;
import com.feywild.feywild.block.trees.WinterTree;
import com.feywild.feywild.util.Registration;
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
import net.minecraftforge.fml.RegistryObject;

import java.util.Random;
import java.util.Set;


public class ModConfiguredFeatures {


    protected static final BlockState PODZOL_STATE = Blocks.PODZOL.defaultBlockState();
    protected static final BlockState GRASS_BLOCK_STATE = Blocks.GRASS_BLOCK.defaultBlockState();

    /* SPRING */
    public static final ConfiguredFeature<?,?> SPRING_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "spring_trees",
                    Feature.TREE.configured(new SpringTree().getConfiguredFeature(new Random(), true).config())
                            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.5f, 3))));

    /* SUMMER */
    public static final ConfiguredFeature<?,?> SUMMER_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "summer_trees",
                    Feature.TREE.configured(new SummerTree().getConfiguredFeature(new Random(), true).config())
                            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.5f, 2))));

    /* AUTUMN */
    public static final ConfiguredFeature<?,?> AUTUMN_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "autumn_trees",
                    Feature.TREE.configured(new AutumnTree().getConfiguredFeature(new Random(), true).config())
                            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.5f, 5))));


    public static final ConfiguredFeature<?, ?> AUTUMN_PUMPKINS = register("autumn_pumpkins",
            ModFeatures.AUTUMN_PUMPKINS.configured(IFeatureConfig.NONE)
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE));





    /*
           // THIS WORKS BUT NOT WHAT I WANTED
           public static final BlockClusterFeatureConfig AUTUMN_PUMPKIN_CONFIG =
                   (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                           .addWeightedBlockstate(Blocks.PUMPKIN.getDefaultState(), 19)
                           .addWeightedBlockstate(Blocks.JACK_O_LANTERN.getDefaultState(), 1)
                           .addWeightedBlockstate(Blocks.CARVED_PUMPKIN.getDefaultState(),1), SimpleBlockPlacer.PLACER)).tries(32)
                           .whitelist(ImmutableSet.of(GRASS_BLOCK_STATE.getBlock())).noProjection().build();


           public static final ConfiguredFeature<?, ?> AUTUMN_PUMPKINS =
                   Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,"autumn_pumpkins",
                           Feature.RANDOM_PATCH.withConfiguration(AUTUMN_PUMPKIN_CONFIG)
                                   .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                                   .withPlacement(Features.Placements.PATCH_PLACEMENT).chance(32));
    */

           /* WINTER */
    public static final ConfiguredFeature<?,?> WINTER_TREES =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "winter_trees",
                    Feature.TREE.configured(new WinterTree().getConfiguredFeature(new Random(), true).config())
                            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                            .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.3f, 2))));

    //Winter Flowers
    public static final BlockClusterFeatureConfig WINTER_FLOWER_CONFIG =
            (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                    .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 2)
                    .add(Blocks.POPPY.defaultBlockState(), 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();


    public static final ConfiguredFeature<?, ?> WINTER_FLOWERS =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,"flower_default",
                    Feature.FLOWER.configured(WINTER_FLOWER_CONFIG)
                            .decorated(Features.Placements.ADD_32)
                            .decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));


    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(FeywildMod.MOD_ID, key), feature);
    }

}
