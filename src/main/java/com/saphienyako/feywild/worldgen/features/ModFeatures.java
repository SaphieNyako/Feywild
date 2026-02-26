package com.saphienyako.feywild.worldgen.features;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.processor.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, Feywild.MOD_ID);

    public static final DeferredHolder<Feature<?>, StructureFeature> AUTUMN_TREE =
            FEATURES.register("autumn_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, AutumnTreeProcessor.INSTANCE));

    public static final DeferredHolder<Feature<?>, StructureFeature> SPRING_TREE =
            FEATURES.register("spring_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, SpringTreeProcessor.INSTANCE));

    public static final DeferredHolder<Feature<?>, StructureFeature> SUMMER_TREE =
            FEATURES.register("summer_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, SummerTreeProcessor.INSTANCE));

    public static final DeferredHolder<Feature<?>, StructureFeature> WINTER_TREE =
            FEATURES.register("winter_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, WinterTreeProcessor.INSTANCE));

}
