package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ORANGE_MUSHROOM_PLACED_KEY = registerKey("orange_mushroom_placed");
    public static final ResourceKey<PlacedFeature> YELLOW_MUSHROOM_PLACED_KEY = registerKey("yellow_mushroom_placed");
    public static final ResourceKey<PlacedFeature> GREEN_MUSHROOM_PLACED_KEY = registerKey("green_mushroom_placed");
    public static final ResourceKey<PlacedFeature> LIGHT_BLUE_MUSHROOM_PLACED_KEY = registerKey("light_blue_mushroom_placed");
    public static final ResourceKey<PlacedFeature> BLUE_MUSHROOM_PLACED_KEY = registerKey("blue_mushroom_placed");
    public static final ResourceKey<PlacedFeature> PURPLE_MUSHROOM_PLACED_KEY = registerKey("purple_mushroom_placed");
    public static final ResourceKey<PlacedFeature> PINK_MUSHROOM_PLACED_KEY = registerKey("pink_mushroom_placed");

    public static final ResourceKey<PlacedFeature> FEY_GEM_ORE_PLACED_KEY = registerKey("fey_gem_ore_placed");
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        registerMushroomPlaced(context, ORANGE_MUSHROOM_PLACED_KEY, ModConfiguredFeatures.ORANGE_MUSHROOM_KEY);

        registerMushroomPlaced(context, YELLOW_MUSHROOM_PLACED_KEY, ModConfiguredFeatures.YELLOW_MUSHROOM_KEY);

        registerMushroomPlaced(context, GREEN_MUSHROOM_PLACED_KEY, ModConfiguredFeatures.GREEN_MUSHROOM_KEY);

        registerMushroomPlaced(context, LIGHT_BLUE_MUSHROOM_PLACED_KEY, ModConfiguredFeatures.LIGHT_BLUE_MUSHROOM_KEY);

        registerMushroomPlaced(context, BLUE_MUSHROOM_PLACED_KEY, ModConfiguredFeatures.BLUE_MUSHROOM_KEY);

        registerMushroomPlaced(context, PURPLE_MUSHROOM_PLACED_KEY, ModConfiguredFeatures.PURPLE_MUSHROOM_KEY);

        registerMushroomPlaced(context, PINK_MUSHROOM_PLACED_KEY, ModConfiguredFeatures.PINK_MUSHROOM_KEY);


        register(context, FEY_GEM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FEY_GEM_ORE_KEY),
                ModOrePlacement.commonOrePlacement(9, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(32))));
    }

    private static void registerMushroomPlaced(
            BootstapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> placedKey,
            ResourceKey<ConfiguredFeature<?, ?>> configuredKey
    ) {
        Holder<ConfiguredFeature<?, ?>> configured =
                context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredKey);

        context.register(
                placedKey,
                new PlacedFeature(configured, List.of())
        );
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Feywild.MOD_ID, name));
    }
    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    }
