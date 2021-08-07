package com.feywild.feywild.world.feature;

import com.feywild.feywild.block.ModBlocks;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

@RegisterClass
public class ModFeatures {

    public static final Feature<NoFeatureConfig> autumnPumpkins = new AutumnPumpkinsFeature();
    public static final Feature<NoFeatureConfig> sunflowers = new GiantFlowerFeature(ModBlocks.sunflower);
    public static final Feature<NoFeatureConfig> dandelions = new GiantFlowerFeature(ModBlocks.dandelion);
    public static final Feature<NoFeatureConfig> crocus = new GiantFlowerFeature(ModBlocks.crocus);
}

