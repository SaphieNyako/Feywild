package com.feywild.feywild.world.structure;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModConfiguredStructures {

    public static StructureFeature<?, ?> CONFIGURED_SPRING_WORLD_TREE
            = ModStructures.springWorldTree.configured(IFeatureConfig.NONE);

    public static StructureFeature<?, ?> CONFIGURED_SUMMER_WORLD_TREE
            = ModStructures.summerWorldTree.configured(IFeatureConfig.NONE);

    public static StructureFeature<?, ?> CONFIGURED_AUTUMN_WORLD_TREE
            = ModStructures.autumnWorldTree.configured(IFeatureConfig.NONE);

    public static StructureFeature<?, ?> CONFIGURED_WINTER_WORLD_TREE
            = ModStructures.winterWorldTree.configured(IFeatureConfig.NONE);

    public static StructureFeature<?, ?> CONFIGURED_BLACKSMITH
            = ModStructures.blacksmith.configured(IFeatureConfig.NONE);

    public static StructureFeature<?, ?> CONFIGURED_LIBRARY
            = ModStructures.library.configured(IFeatureConfig.NONE);

    //The best time to register configured features by code is to do it in FMLCommonSetupEvent.
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(FeywildMod.getInstance().modid, "configured_spring_world_tree"), CONFIGURED_SPRING_WORLD_TREE);
        Registry.register(registry, new ResourceLocation(FeywildMod.getInstance().modid, "configured_summer_world_tree"), CONFIGURED_SUMMER_WORLD_TREE);
        Registry.register(registry, new ResourceLocation(FeywildMod.getInstance().modid, "configured_autumn_world_tree"), CONFIGURED_AUTUMN_WORLD_TREE);
        Registry.register(registry, new ResourceLocation(FeywildMod.getInstance().modid, "configured_winter_world_tree"), CONFIGURED_WINTER_WORLD_TREE);
        Registry.register(registry, new ResourceLocation(FeywildMod.getInstance().modid, "configured_blacksmith"), CONFIGURED_BLACKSMITH);
        Registry.register(registry, new ResourceLocation(FeywildMod.getInstance().modid, "configured_library"), CONFIGURED_LIBRARY);
    }

    //Requires AccessTransformer ( see resources/META-INF/accesstransformer.cfg )
    // FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.SPRING_WORLD_TREE.get(), CONFIGURED_SPRING_WORLD_TREE);

}
