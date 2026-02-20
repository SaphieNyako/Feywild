package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, Feywild.MOD_ID);

    public static final DeferredHolder<Feature<?>, StructureFeature> AUTUMN_TREE =
            FEATURES.register("autumn_tree",
                    () -> new StructureFeature(
                            ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "autumn_tree"),
                            NoneFeatureConfiguration.CODEC
                    )
            );
}
