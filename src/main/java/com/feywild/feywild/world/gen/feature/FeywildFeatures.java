package com.feywild.feywild.world.gen.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "FEATURE_REGISTRY")
public class FeywildFeatures {

    public static final Feature<GiantFlowerFeature.Configuration> giantFlowers = new GiantFlowerFeature();
    public static final Feature<NoneFeatureConfiguration> autumnPumpkins = new AutumnPumpkinsFeature();

    public static final Feature<NoneFeatureConfiguration> springTree = new SpringTreeFeature();
    public static final Feature<NoneFeatureConfiguration> summerTree = new SummerTreeFeature();
    public static final Feature<NoneFeatureConfiguration> winterTree = new WinterTreeFeature();
    public static final Feature<NoneFeatureConfiguration> autumnTree = new AutumnTreeFeature();
    public static final Feature<NoneFeatureConfiguration> blossomTree = new BlossomTreeFeature();
    public static final Feature<NoneFeatureConfiguration> hexenTree = new HexenTreeFeature();

    public static final Feature<NoneFeatureConfiguration> treeMushroom = new TreeMushroomFeature();
}
