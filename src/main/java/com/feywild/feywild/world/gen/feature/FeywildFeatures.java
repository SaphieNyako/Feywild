package com.feywild.feywild.world.gen.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "FEATURE")
public class FeywildFeatures {

    public static final Feature<GiantFlowerFeature.Configuration> giantFlowers = new GiantFlowerFeature();
    public static final Feature<NoneFeatureConfiguration> autumnPumpkins = new AutumnPumpkinsFeature();
    public static final Feature<TemplateFeatureConfig> template = new TemplateFeature();
}
