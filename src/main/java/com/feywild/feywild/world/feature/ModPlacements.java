package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Random;

public class ModPlacements {
    
    public static final PlacedFeature SPRING_TREES = registerPlacement("spring_trees", ModTrees.springTree.getConfiguredFeature(new Random(), true).placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            CountPlacement.of(ClampedNormalInt.of(1, 0.5f, 1, 4))
    ));

    public static final PlacedFeature SPRING_DANDELION = registerPlacement("spring_dandelions", ModFeatures.Configured.dandelions.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)
    ));
    

    public static final PlacedFeature SPRING_FLOWERS = registerPlacement("sping_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.PLAIN_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    CountPlacement.of(ConstantInt.of(100))
            )
    );

    public static final PlacedFeature SUMMER_TREES = registerPlacement("summer_trees", ModTrees.summerTree.getConfiguredFeature(new Random(), true).placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            CountPlacement.of(ClampedNormalInt.of(1, 0.5f, 1, 4))
    ));

    public static final PlacedFeature SUMMER_SUNFLOWER = registerPlacement("summer_sunflower", ModFeatures.Configured.sunflowers.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)
    ));
    
    public static final PlacedFeature SUMMER_FLOWERS = registerPlacement("summer_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.WARM_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    CountPlacement.of(ConstantInt.of(4))
            )
    );

    public static final PlacedFeature AUTUMN_TREES = registerPlacement("autumn_trees", ModTrees.autumnTree.getConfiguredFeature(new Random(), true).placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            CountPlacement.of(ClampedNormalInt.of(1, 0.5f, 1, 4))
    ));

    public static final PlacedFeature AUTUMN_PUMPKINS = registerPlacement("autumn_pumpkins", ModFeatures.Configured.autumnPumpkins.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)
    ));

    public static final PlacedFeature AUTUMN_FLOWERS = registerPlacement("autumn_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.SWAMP_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    CountPlacement.of(ConstantInt.of(4))
            )
    );

    public static final PlacedFeature AUTUMN_MUSHROOMS = registerPlacement("autumn_mushrooms", Feature.FLOWER
            .configured(ModFeatureProperties.SMALL_MUSHROOMS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    CountPlacement.of(ConstantInt.of(2))
            )
    );

    public static final PlacedFeature WINTER_TREES = registerPlacement("winter_trees", ModTrees.winterTree.getConfiguredFeature(new Random(), true).placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            CountPlacement.of(ClampedNormalInt.of(1, 0.5f, 1, 4))
    ));

    public static final PlacedFeature WINTER_CROCUS = registerPlacement("winter_crocus", ModFeatures.Configured.crocus.placed(
            HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)
    ));

    public static final PlacedFeature WINTER_FLOWERS = registerPlacement("winter_flowers", Feature.FLOWER
            .configured(ModFeatureProperties.COLD_FLOWERS)
            .placed(
                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                    CountPlacement.of(ConstantInt.of(2))
            )
    );
    
    private static PlacedFeature registerPlacement(String key, PlacedFeature placement) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, key), placement);
    }
}
