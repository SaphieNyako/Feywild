package com.feywild.feywild.world.gen;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.AutumnTree;
import com.feywild.feywild.block.trees.SpringTree;
import com.feywild.feywild.block.trees.SummerTree;
import com.feywild.feywild.block.trees.WinterTree;
import com.feywild.feywild.util.configs.Config;
import com.feywild.feywild.world.feature.ModConfiguredFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

// TODO
@Mod.EventBusSubscriber(modid = "feywild")
public class ModTreeGeneration {

    public final static double TREE_PATCHES_CHANCE = Config.TREE_PATCH_CONFIG.getCachedSpawnChance();
    public final static int TREE_PATCHES_SIZE = Config.TREE_PATCH_CONFIG.getCachedSpawnSize();

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {

        Random random = new Random();

        String SpringBiome = "blossoming_wealds";
        String SummerBiome = "golden_seelie_fields";
        String AutumnBiome = "eternal_fall";
        String WinterBiome = "frozen_retreat";
        String AlfHeimPlains = "alfheim_plains";
        String GoldenFields = "golden_fields";
        String AlfHeimHills = "alfheim_hills";
        String AlfHeimForest = "dreamwood_forest";
        String biomeName = event.getName().toString();

        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        //SPRING TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.PLAINS) || types.contains(BiomeDictionary.Type.RIVER) || types.contains(BiomeDictionary.Type.FOREST))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && Config.TREE_PATCH_CONFIG.getCachedSpawnSpring()) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(ModTrees.springTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));

            if (types.contains(BiomeDictionary.Type.FOREST)) {

                base.add(() -> Feature.TREE.configured(ModTrees.autumnTree.getConfiguredFeature(random, true).config())
                        .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                        .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));

            }

        }

        //SUMMER TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.HOT) || types.contains(BiomeDictionary.Type.LUSH))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && Config.TREE_PATCH_CONFIG.getCachedSpawnSummer()) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(ModTrees.summerTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));
        }

        //AUTUMN TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.SWAMP) || types.contains(BiomeDictionary.Type.MUSHROOM) || types.contains(BiomeDictionary.Type.SPOOKY))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && Config.TREE_PATCH_CONFIG.getCachedSpawnAutumn()) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(ModTrees.autumnTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));
        }

        //WINTER TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.DEAD) || types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && Config.TREE_PATCH_CONFIG.getCachedSpawnWinter()) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(ModTrees.winterTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));
        }


        /* BIOME DECORATION GENERATION */

        if (biomeName.contains(SpringBiome) || (biomeName.contains(AlfHeimPlains) && Config.MYTHIC.get() != 0)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.SPRING_TREES);
            base.add(() -> ModConfiguredFeatures.SPRING_DANDELION);
            base.add(() -> ModConfiguredFeatures.SPRING_FLOWERS);
        }

        if (biomeName.contains(SummerBiome) || (biomeName.contains(GoldenFields) && Config.MYTHIC.get() != 0)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.SUMMER_TREES);
            base.add(() -> ModConfiguredFeatures.SUMMER_SUNFLOWER);
            base.add(() -> ModConfiguredFeatures.SUMMER_WARM_FLOWERS);

        }

        if (biomeName.contains(AutumnBiome) || (biomeName.contains(AlfHeimHills) && Config.MYTHIC.get() != 0)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.AUTUMN_TREES);
            base.add(() -> ModConfiguredFeatures.AUTUMN_PUMPKINS);
            base.add(() -> ModConfiguredFeatures.AUTUMN_SWAMP_FLOWERS);
            base.add(() -> ModConfiguredFeatures.AUTUMN_SMALL_MUSHROOMS);

        }

        if (biomeName.contains(WinterBiome) || (biomeName.contains(AlfHeimForest) && Config.MYTHIC.get() != 0)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.WINTER_TREES);
            base.add(() -> ModConfiguredFeatures.WINTER_CROCUS);
            base.add(() -> ModConfiguredFeatures.WINTER_FLOWERS);

        }
    }
}
