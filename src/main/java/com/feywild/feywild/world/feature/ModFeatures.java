package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@RegisterClass
public class ModFeatures {

    public static final Feature<NoneFeatureConfiguration> autumnPumpkins = new AutumnPumpkinsFeature();
    public static final Feature<NoneFeatureConfiguration> sunflowers = new GiantFlowerFeature(ModBlocks.sunflower);
    public static final Feature<NoneFeatureConfiguration> dandelions = new GiantFlowerFeature(ModBlocks.dandelion);
    public static final Feature<NoneFeatureConfiguration> crocus = new GiantFlowerFeature(ModBlocks.crocus);
    
    public static class Configured {
        
        public static final ConfiguredFeature<?, ?> autumnPumpkins = ModFeatures.autumnPumpkins.configured(NoneFeatureConfiguration.INSTANCE);
        public static final ConfiguredFeature<?, ?> sunflowers = ModFeatures.sunflowers.configured(NoneFeatureConfiguration.INSTANCE);
        public static final ConfiguredFeature<?, ?> dandelions = ModFeatures.dandelions.configured(NoneFeatureConfiguration.INSTANCE);
        public static final ConfiguredFeature<?, ?> crocus = ModFeatures.crocus.configured(NoneFeatureConfiguration.INSTANCE);

        private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> registerFeature(String key, ConfiguredFeature<FC, ?> feature) {
            return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, key), feature);
        }
    }
}

