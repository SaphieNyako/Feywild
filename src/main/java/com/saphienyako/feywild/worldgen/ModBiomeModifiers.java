package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_FEY_GEM_ORE = registerKey("add_fey_gem_ore");
   // public static final ResourceKey<BiomeModifier> ADD_AUTUMN_TREE = registerKey("add_autumn_tree");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_FEY_GEM_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FEY_GEM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
/*
        context.register(ADD_AUTUMN_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AUTUMN_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        /*
        context.register(ADD_AUTUMN_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AUTUMN_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        )); */

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, name));
    }

}
