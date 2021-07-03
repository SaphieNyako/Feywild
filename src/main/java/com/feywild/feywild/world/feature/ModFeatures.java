package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {

    public static final Feature<NoFeatureConfig> AUTUMN_PUMPKINS =
            register("autumn_pumpkins", new AutumnPumpkinsFeature());

    public static final Feature<NoFeatureConfig> SUNFLOWER_FEATURE =
            register("sunflower_feature", new SunflowerFeature());

    public static final Feature<NoFeatureConfig> DANDELION_FEATURE =
            register("dandelion_feature", new DandelionFeature());

    public static void register() {}

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        value.setRegistryName(new ResourceLocation(FeywildMod.MOD_ID, key));
        ForgeRegistries.FEATURES.register(value);
        return value;
    }

}

