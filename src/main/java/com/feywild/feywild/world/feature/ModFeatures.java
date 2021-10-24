package com.feywild.feywild.world.feature;

import com.feywild.feywild.block.ModBlocks;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@RegisterClass
public class ModFeatures {

    public static final Feature<NoneFeatureConfiguration> autumnPumpkins = new AutumnPumpkinsFeature();
    public static final Feature<NoneFeatureConfiguration> sunflowers = new GiantFlowerFeature(ModBlocks.sunflower);
    public static final Feature<NoneFeatureConfiguration> dandelions = new GiantFlowerFeature(ModBlocks.dandelion);
    public static final Feature<NoneFeatureConfiguration> crocus = new GiantFlowerFeature(ModBlocks.crocus);
}

