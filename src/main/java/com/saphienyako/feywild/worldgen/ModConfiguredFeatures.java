package com.saphienyako.feywild.worldgen;

import com.google.common.base.Suppliers;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {


    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Feywild.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> FEY_GEM_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.FEY_GEM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FEY_GEM_ORE = CONFIGURED_FEATURES.register("fey_gem_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(FEY_GEM_ORES.get(),5)));

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}
