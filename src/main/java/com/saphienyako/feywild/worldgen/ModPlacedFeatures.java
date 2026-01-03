package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> FEY_GEM_ORE_PLACED = PlacementUtils.register("fey_gem_ore_placed",
            ModConfiguredFeatures.FEY_GEM_ORE, ModOrePlacement.commonOrePlacement(9,
                            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(32))));

}
