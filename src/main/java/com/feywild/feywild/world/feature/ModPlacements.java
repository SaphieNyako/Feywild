package com.feywild.feywild.world.feature;

@SuppressWarnings("deprecation")
public class ModPlacements {

    /*
    public static final PlacedFeature SPRING_DANDELION = registerPlacement("spring_dandelions", ModFeatures.Configured.dandelions.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

    public static final PlacedFeature SUMMER_SUNFLOWER = registerPlacement("summer_sunflower", ModFeatures.Configured.sunflowers.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

    public static final PlacedFeature AUTUMN_PUMPKINS = registerPlacement("autumn_pumpkins", ModFeatures.Configured.autumnPumpkins.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

    public static final PlacedFeature WINTER_CROCUS = registerPlacement("winter_crocus", ModFeatures.Configured.crocus.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE)
    ));

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

     */
}
