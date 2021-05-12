package com.feywild.feywild.world.structure;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Map;

public class ModConfiguredStructures {

    //Feature of our structure so we can reference it and add it to biomes easily.
    public static StructureFeature<?, ?> CONFIGURED_SPRING_WORLD_TREE
            = ModStructures.SPRING_WORLD_TREE.get().configured(IFeatureConfig.NONE);

    public static StructureFeature<?, ?> CONFIGURED_BLACKSMITH
            = ModStructures.BLACKSMITH.get().configured(IFeatureConfig.NONE);


    //The best time to register configured features by code is to do it in FMLCommonSetupEvent.
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(FeywildMod.MOD_ID, "configured_spring_world_tree"), CONFIGURED_SPRING_WORLD_TREE);
        Registry.register(registry, new ResourceLocation(FeywildMod.MOD_ID, "configured_blacksmith"), CONFIGURED_BLACKSMITH);
    }






    //Requires AccessTransformer ( see resources/META-INF/accesstransformer.cfg )
    //FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.SPRING_WORLD_TREE.get(), CONFIGURED_SPRING_WORLD_TREE);

}
