package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Feywild.MOD_ID);

    private static RegistryObject<PlacedFeature> mushroom(String name, RegistryObject<ConfiguredFeature<?, ?>> configured) {
        return PLACED_FEATURES.register(name,
                () -> new PlacedFeature(configured.getHolder().get(), List.of()));
    }

    public static final RegistryObject<PlacedFeature> ORANGE_MUSHROOM_PLACED =
            mushroom("orange_mushroom_placed", ModConfiguredFeatures.ORANGE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> YELLOW_MUSHROOM_PLACED =
            mushroom("yellow_mushroom_placed", ModConfiguredFeatures.YELLOW_MUSHROOM);

    public static final RegistryObject<PlacedFeature> GREEN_MUSHROOM_PLACED =
            mushroom("green_mushroom_placed", ModConfiguredFeatures.GREEN_MUSHROOM);

    public static final RegistryObject<PlacedFeature> LIGHT_BLUE_MUSHROOM_PLACED =
            mushroom("light_blue_mushroom_placed", ModConfiguredFeatures.LIGHT_BLUE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> BLUE_MUSHROOM_PLACED =
            mushroom("blue_mushroom_placed", ModConfiguredFeatures.BLUE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> PURPLE_MUSHROOM_PLACED =
            mushroom("purple_mushroom_placed", ModConfiguredFeatures.PURPLE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> PINK_MUSHROOM_PLACED =
            mushroom("pink_mushroom_placed", ModConfiguredFeatures.PINK_MUSHROOM);

    // ===== ORE (already correct) =====

    public static final RegistryObject<PlacedFeature> FEY_GEM_ORE_PLACED =
            PLACED_FEATURES.register("fey_gem_ore_placed",
                    () -> new PlacedFeature(
                            ModConfiguredFeatures.FEY_GEM_ORE.getHolder().get(),
                            ModOrePlacement.commonOrePlacement(
                                    9,
                                    HeightRangePlacement.uniform(
                                            VerticalAnchor.aboveBottom(-64),
                                            VerticalAnchor.aboveBottom(32)
                                    )
                            )
                    ));

    private static RegistryObject<PlacedFeature> treePlaced(String name, RegistryObject<ConfiguredFeature<?, ?>> configured, RegistryObject<Block> sapling) {
        return PLACED_FEATURES.register(name,
                () -> new PlacedFeature(
                        configured.getHolder().get(),
                        List.of(
                                RarityFilter.onAverageOnceEvery(12),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                                PlacementUtils.filteredByBlockSurvival(sapling.get()), // <-- safe now
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                                        BlockPredicate.solid(BlockPos.ZERO.below()),
                                        BlockPredicate.hasSturdyFace(BlockPos.ZERO.below(), Direction.UP)
                                )),
                                BiomeFilter.biome()
                        )
                )
        );
    }

    public static final RegistryObject<PlacedFeature> AUTUMN_TREE_PLACED =
            treePlaced("autumn_tree_placed", ModConfiguredFeatures.AUTUMN_TREE, ModBlocks.AUTUMN_TREE_SAPLING);

    public static final RegistryObject<PlacedFeature> SPRING_TREE_PLACED =
            treePlaced("spring_tree_placed", ModConfiguredFeatures.SPRING_TREE, ModBlocks.SPRING_TREE_SAPLING);

    public static final RegistryObject<PlacedFeature> SUMMER_TREE_PLACED =
            treePlaced("summer_tree_placed", ModConfiguredFeatures.SUMMER_TREE, ModBlocks.SUMMER_TREE_SAPLING);

    public static final RegistryObject<PlacedFeature> WINTER_TREE_PLACED =
            treePlaced("winter_tree_placed", ModConfiguredFeatures.WINTER_TREE, ModBlocks.WINTER_TREE_SAPLING);

    public static void register(IEventBus bus) {
        PLACED_FEATURES.register(bus);
    }

}
