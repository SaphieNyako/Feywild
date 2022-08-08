package com.feywild.feywild.world.feature.structure;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.feature.structure.structures.BlacksmithStructure;
import com.feywild.feywild.world.feature.structure.structures.LibraryStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

    public static final DeferredRegister<StructureFeature<?>> STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, FeywildMod.getInstance().modid);

    public static final RegistryObject<StructureFeature<?>> BLACKSMITH =
            STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY.register("blacksmith", BlacksmithStructure::new);

    public static final RegistryObject<StructureFeature<?>> LIBRARY =
            STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY.register("library", LibraryStructure::new);

    public static void register(IEventBus eventBus) {
        STRUCTURE_FEATURE_DEFERRED_REGISTERD_REGISTRY.register(eventBus);
    }

}
