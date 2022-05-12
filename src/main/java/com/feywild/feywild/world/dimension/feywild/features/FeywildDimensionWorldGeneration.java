package com.feywild.feywild.world.dimension.feywild.features;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.world.dimension.feywild.features.specialfeatures.AutumnPumpkinsFeature;
import com.feywild.feywild.world.dimension.feywild.features.specialfeatures.GiantFlowerFeature;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@RegisterClass
public class FeywildDimensionWorldGeneration {

    public static final Feature<NoneFeatureConfiguration> autumnPumpkins = new AutumnPumpkinsFeature();
    public static final Feature<NoneFeatureConfiguration> sunflowers = new GiantFlowerFeature(ModBlocks.sunflower);
    public static final Feature<NoneFeatureConfiguration> dandelions = new GiantFlowerFeature(ModBlocks.dandelion);
    public static final Feature<NoneFeatureConfiguration> crocus = new GiantFlowerFeature(ModBlocks.crocus);

}
