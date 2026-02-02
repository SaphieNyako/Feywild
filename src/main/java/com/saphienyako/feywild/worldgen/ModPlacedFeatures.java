package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Feywild.MOD_ID);

    private static RegistryObject<PlacedFeature> mushroom(String name, RegistryObject<ConfiguredFeature<?, ?>> configured) {
        return PLACED_FEATURES.register(name,
                () -> new PlacedFeature(configured.getHolder().get(), List.of()));
    }

    public static final RegistryObject<PlacedFeature> ORANGE_MUSHROOM_PLACED =
            mushroom("orange_mushroom_placed", ModConfiguredFeatures.ORANGE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> YELLOW_MUSHROOM_PLACED =
            mushroom("yellow_mushroom_placed", ModConfiguredFeatures.YELLOW_MUSHROOM);

    public static final RegistryObject<PlacedFeature> GREEN_MUSHROOM_PLACED =
            mushroom("green_mushroom_placed", ModConfiguredFeatures.GREEN_MUSHROOM);

    public static final RegistryObject<PlacedFeature> LIGHT_BLUE_MUSHROOM_PLACED =
            mushroom("light_blue_mushroom_placed", ModConfiguredFeatures.LIGHT_BLUE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> BLUE_MUSHROOM_PLACED =
            mushroom("blue_mushroom_placed", ModConfiguredFeatures.BLUE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> PURPLE_MUSHROOM_PLACED =
            mushroom("purple_mushroom_placed", ModConfiguredFeatures.PURPLE_MUSHROOM);

    public static final RegistryObject<PlacedFeature> PINK_MUSHROOM_PLACED =
            mushroom("pink_mushroom_placed", ModConfiguredFeatures.PINK_MUSHROOM);

    // ===== ORE (already correct) =====

    public static final RegistryObject<PlacedFeature> FEY_GEM_ORE_PLACED =
            PLACED_FEATURES.register("fey_gem_ore_placed",
                    () -> new PlacedFeature(
                            ModConfiguredFeatures.FEY_GEM_ORE.getHolder().get(),
                            ModOrePlacement.commonOrePlacement(
                                    9,
                                    HeightRangePlacement.uniform(
                                            VerticalAnchor.aboveBottom(-64),
                                            VerticalAnchor.aboveBottom(32)
                                    )
                            )
                    ));

    public static void register(IEventBus bus) {
        PLACED_FEATURES.register(bus);
    }

}
