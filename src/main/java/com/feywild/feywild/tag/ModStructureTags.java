package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class ModStructureTags {

    public static final TagKey<ConfiguredStructureFeature<?, ?>> PORTAL_STRUCTURE = tag();

    private static TagKey<ConfiguredStructureFeature<?, ?>> tag() {
        return TagKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(FeywildMod.getInstance().modid, "portal_structures"));
    }
}
