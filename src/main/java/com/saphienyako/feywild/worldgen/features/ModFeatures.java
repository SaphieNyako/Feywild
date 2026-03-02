package com.saphienyako.feywild.worldgen.features;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.processor.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, Feywild.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> AUTUMN_TREE =
            FEATURES.register("autumn_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, AutumnTreeProcessor.WORLDGEN));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPRING_TREE =
            FEATURES.register("spring_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, SpringTreeProcessor.WORLDGEN));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SUMMER_TREE =
            FEATURES.register("summer_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, SummerTreeProcessor.WORLDGEN));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> WINTER_TREE =
            FEATURES.register("winter_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, WinterTreeProcessor.WORLDGEN));
}
