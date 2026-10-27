package com.saphienyako.feywild.worldgen.features;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.processor.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, Feywild.MOD_ID);

    public static final DeferredHolder<Feature<?>, @NotNull StructureFeature> AUTUMN_TREE =
            FEATURES.register("autumn_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, AutumnTreeProcessor.WORLD_GEN));

    public static final DeferredHolder<Feature<?>, @NotNull StructureFeature> SPRING_TREE =
            FEATURES.register("spring_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, SpringTreeProcessor.WORLD_GEN));

    public static final DeferredHolder<Feature<?>, @NotNull StructureFeature> SUMMER_TREE =
            FEATURES.register("summer_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, SummerTreeProcessor.WORLD_GEN));

    public static final DeferredHolder<Feature<?>, @NotNull StructureFeature> WINTER_TREE =
            FEATURES.register("winter_tree",
                    () -> new StructureFeature(NoneFeatureConfiguration.CODEC, WinterTreeProcessor.WORLD_GEN));

}
