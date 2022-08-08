package com.feywild.feywild.world.dimension.feywild.features;

import com.feywild.feywild.block.ModTrees;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class FeywildPlacementUtils {

    public static final List<PlacementModifier> getSpringTrees = List.of(
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
            onGroundFor(ModTrees.springTree.getSapling()),
            BiomeFilter.biome()
    );

    public static final List<PlacementModifier> getSummerTrees = List.of(
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
            onGroundFor(ModTrees.summerTree.getSapling()),
            BiomeFilter.biome()
    );

    public static final List<PlacementModifier> getAutumnTrees = List.of(
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
            onGroundFor(ModTrees.autumnTree.getSapling()),
            BiomeFilter.biome()
    );

    public static final List<PlacementModifier> getWinterTrees = List.of(
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
            onGroundFor(ModTrees.winterTree.getSapling()),
            BiomeFilter.biome()
    );

    public static final List<PlacementModifier> springTrees = ImmutableList.<PlacementModifier>builder()
            .add(PlacementUtils.countExtra(1, 0.2f, 1)).addAll(getSpringTrees).build();

    public static final List<PlacementModifier> summerTrees = ImmutableList.<PlacementModifier>builder()
            .add(PlacementUtils.countExtra(1, 0.1f, 1)).addAll(getSummerTrees).build();

    public static final List<PlacementModifier> autumnTrees = ImmutableList.<PlacementModifier>builder()
            .add(PlacementUtils.countExtra(2, 0.1f, 1)).addAll(getAutumnTrees).build();

    public static final List<PlacementModifier> winterTrees = ImmutableList.<PlacementModifier>builder()
            .add(PlacementUtils.countExtra(1, 0.1f, 1)).addAll(getWinterTrees).build();

    @SuppressWarnings("SameParameterValue")
    private static PlacementModifier onGroundFor(Block block) {
        return onGroundFor(block.defaultBlockState());
    }

    private static PlacementModifier onGroundFor(BlockState state) {
        return BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                BlockPredicate.wouldSurvive(state, BlockPos.ZERO),
                BlockPredicate.matchesFluid(Fluids.EMPTY, BlockPos.ZERO)
        ));
    }

}
