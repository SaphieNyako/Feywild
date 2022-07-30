package com.feywild.feywild.world.dimension.feywild.features;

import org.moddingx.libx.annotation.registration.RegisterClass;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

@RegisterClass(priority = -3)
public class FeywildDimensionPlacements {

    public static final Holder<PlacedFeature> autumnPumpkins = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.autumnPumpkins, List.of()));

    public static final Holder<PlacedFeature> springDandelions = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.dandelions, List.of()));

    public static final Holder<PlacedFeature> summerSunflowers = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.sunflowers, List.of()));

    public static final Holder<PlacedFeature> winterCrocus = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.crocus, List.of()));

    public static final Holder<PlacedFeature> springTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.springTrees, FeywildPlacementUtils.springTrees));

    public static final Holder<PlacedFeature> summerTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.summerTrees, FeywildPlacementUtils.summerTrees));

    public static final Holder<PlacedFeature> autumnTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.autumnTrees, FeywildPlacementUtils.autumnTrees));

    public static final Holder<PlacedFeature> winterTrees = new NoneFeatureHolder<>(Registry.PLACED_FEATURE_REGISTRY,
            new PlacedFeature(FeywildDimensionConfiguredFeatures.winterTrees, FeywildPlacementUtils.winterTrees));

    public static final Holder<StructureSet> beekeep = new NoneFeatureHolder<>(Registry.STRUCTURE_SET_REGISTRY,
            new StructureSet(FeywildDimensionConfiguredFeatures.beekeep,
                    new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, Math.abs("beekeep".hashCode())))); //distance, spread, seednumber DOES THIS MATTER?

    public static final Holder<StructureSet> springWorldTree = new NoneFeatureHolder<>(Registry.STRUCTURE_SET_REGISTRY,
            new StructureSet(FeywildDimensionConfiguredFeatures.springWorldTree,
                    new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.TRIANGULAR, Math.abs("spring_world_tree".hashCode()))));

    public static final Holder<StructureSet> summerWorldTree = new NoneFeatureHolder<>(Registry.STRUCTURE_SET_REGISTRY,
            new StructureSet(FeywildDimensionConfiguredFeatures.summerWorldTree,
                    new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, Math.abs("summer_world_tree".hashCode()))));

    public static final Holder<StructureSet> autumnWorldTree = new NoneFeatureHolder<>(Registry.STRUCTURE_SET_REGISTRY,
            new StructureSet(FeywildDimensionConfiguredFeatures.autumnWorldTree,
                    new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, Math.abs("autumn_world_tree".hashCode()))));

    public static final Holder<StructureSet> winterWorldTree = new NoneFeatureHolder<>(Registry.STRUCTURE_SET_REGISTRY,
            new StructureSet(FeywildDimensionConfiguredFeatures.winterWorldTree,
                    new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, Math.abs("winter_world_tree".hashCode()))));

    public static final Holder<StructureSet> fey_circle = new NoneFeatureHolder<>(Registry.STRUCTURE_SET_REGISTRY,
            new StructureSet(FeywildDimensionConfiguredFeatures.feyCircle,
                    new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, Math.abs("fey_circle".hashCode()))));
}
