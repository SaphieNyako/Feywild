package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Feywild.MOD_ID);


    public static final RegistryObject<PlacedFeature> FEY_GEM_ORE_PLACED = PLACED_FEATURES.register("fey_gem_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.FEY_GEM_ORE.getHolder().get(),
                    ModOrePlacement.commonOrePlacement(9,
                            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(32)))));




    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }

}
