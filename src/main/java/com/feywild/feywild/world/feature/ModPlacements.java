package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("deprecation")
public class ModPlacements {

    public static final PlacedFeature SPRING_DANDELION = registerPlacement("spring_dandelions", ModFeatures.Configured.dandelions.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

    public static final PlacedFeature SPRING_FLOWERS = registerPlacement("sping_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.PLAIN_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
                    CountOnEveryLayerPlacement.of(ConstantInt.of(100))
            )
    );

    public static final PlacedFeature SUMMER_SUNFLOWER = registerPlacement("summer_sunflower", ModFeatures.Configured.sunflowers.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

    public static final PlacedFeature SUMMER_FLOWERS = registerPlacement("summer_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.WARM_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
                    CountOnEveryLayerPlacement.of(ConstantInt.of(4))
            )
    );

    public static final PlacedFeature AUTUMN_PUMPKINS = registerPlacement("autumn_pumpkins", ModFeatures.Configured.autumnPumpkins.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

    public static final PlacedFeature AUTUMN_FLOWERS = registerPlacement("autumn_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.SWAMP_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
                    CountOnEveryLayerPlacement.of(ConstantInt.of(4))
            )
    );

    public static final PlacedFeature AUTUMN_MUSHROOMS = registerPlacement("autumn_mushrooms", Feature.FLOWER
            .configured(ModFeatureProperties.SMALL_MUSHROOMS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
                    CountOnEveryLayerPlacement.of(ConstantInt.of(2))
            )
    );

    public static final PlacedFeature WINTER_CROCUS = registerPlacement("winter_crocus", ModFeatures.Configured.crocus.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

    public static final PlacedFeature WINTER_FLOWERS = registerPlacement("winter_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.COLD_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
                    CountOnEveryLayerPlacement.of(ConstantInt.of(2))
            )
    );

    public static PlacementModifier onGroundFor(Block block) {
        return onGroundFor(block.defaultBlockState());
    }

    public static PlacementModifier onGroundFor(BlockState state) {
        return BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                BlockPredicate.wouldSurvive(state, BlockPos.ZERO),
                BlockPredicate.matchesFluid(Fluids.EMPTY, BlockPos.ZERO)
        ));

    }

    public static PlacementModifier onGround(BlockState state) {
        return BlockPredicateFilter.forPredicate(BlockPredicate.allOf());
    }

    private static PlacedFeature registerPlacement(String key, PlacedFeature placement) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, key), placement);
    }
}
