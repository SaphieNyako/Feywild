package com.feywild.feywild.world.structure;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ModConfiguredStructures {

    public static final StructureFeature<?, ?> CONFIGURED_SPRING_WORLD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_spring_world_tree"), ModStructures.springWorldTree.configured(IFeatureConfig.NONE));
    public static final StructureFeature<?, ?> CONFIGURED_SUMMER_WORLD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_summer_world_tree"), ModStructures.summerWorldTree.configured(IFeatureConfig.NONE));
    public static final StructureFeature<?, ?> CONFIGURED_AUTUMN_WORLD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_autumn_world_tree"), ModStructures.autumnWorldTree.configured(IFeatureConfig.NONE));
    public static final StructureFeature<?, ?> CONFIGURED_WINTER_WORLD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_winter_world_tree"), ModStructures.winterWorldTree.configured(IFeatureConfig.NONE));
    public static final StructureFeature<?, ?> CONFIGURED_BLACKSMITH = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_blacksmith"), ModStructures.blacksmith.configured(IFeatureConfig.NONE));
    public static final StructureFeature<?, ?> CONFIGURED_LIBRARY = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_library"), ModStructures.library.configured(IFeatureConfig.NONE));
}
