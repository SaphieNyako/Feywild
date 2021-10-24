package com.feywild.feywild.world.structure;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ModConfiguredStructures {

    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_SPRING_WORLD_TREE = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_spring_world_tree"), ModStructures.springWorldTree.configured(FeatureConfiguration.NONE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_SUMMER_WORLD_TREE = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_summer_world_tree"), ModStructures.summerWorldTree.configured(FeatureConfiguration.NONE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_AUTUMN_WORLD_TREE = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_autumn_world_tree"), ModStructures.autumnWorldTree.configured(FeatureConfiguration.NONE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_WINTER_WORLD_TREE = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_winter_world_tree"), ModStructures.winterWorldTree.configured(FeatureConfiguration.NONE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BLACKSMITH = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_blacksmith"), ModStructures.blacksmith.configured(FeatureConfiguration.NONE));
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_LIBRARY = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid, "configured_library"), ModStructures.library.configured(FeatureConfiguration.NONE));
    public static final ConfiguredStructureFeature<?,?> CONFIGURED_BEEKEEP = Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(FeywildMod.getInstance().modid,"configured_beekeep"),ModStructures.beekeep.configured(FeatureConfiguration.NONE));
}
