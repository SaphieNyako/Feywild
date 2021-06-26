package com.feywild.feywild.world.gen;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.AutumnTree;
import com.feywild.feywild.block.trees.SpringTree;
import com.feywild.feywild.block.trees.SummerTree;
import com.feywild.feywild.block.trees.WinterTree;
import com.feywild.feywild.util.Config;
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

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID)
public class ModTreeGeneration {

    public final static double TREE_PATCHES_CHANCE = Config.TREE_PATCHES_CHANCE.get();
    public final static int TREE_PATCHES_SIZE = Config.TREE_PATCHES_SIZE.get();

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {

        SpringTree springTree = new SpringTree();
        AutumnTree autumnTree = new AutumnTree();
        WinterTree winterTree = new WinterTree();
        SummerTree summerTree = new SummerTree();

        Random random = new Random();

        String SpringBiome = "blossoming_wealds";
        String SummerBiome = "golden_seelie_fields";
        String AutumnBiome = "eternal_fall";
        String WinterBiome = "frozen_retreat";
        String biomeName = event.getName().toString();

        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        //SPRING TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.PLAINS) || types.contains(BiomeDictionary.Type.RIVER) || types.contains(BiomeDictionary.Type.FOREST))
                && !types.contains(BiomeDictionary.Type.MAGICAL))&& Config.SPRING_TREE_PATCH.get()) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(springTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));

            if (types.contains(BiomeDictionary.Type.FOREST)) {

                base.add(() -> Feature.TREE.configured(autumnTree.getConfiguredFeature(random, true).config())
                        .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                        .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));

            }

        }

        //SUMMER TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.HOT) || types.contains(BiomeDictionary.Type.LUSH))
                && !types.contains(BiomeDictionary.Type.MAGICAL))&& Config.SUMMER_TREE_PATCH.get()) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(summerTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));
        }

        //AUTUMN TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.SWAMP) || types.contains(BiomeDictionary.Type.MUSHROOM) || types.contains(BiomeDictionary.Type.SPOOKY))
                && !types.contains(BiomeDictionary.Type.MAGICAL)) && Config.AUTUMN_TREE_PATCH.get()) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(autumnTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));
        }

        //WINTER TREE GENERATION
        if (((types.contains(BiomeDictionary.Type.DEAD) || types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD))
                && !types.contains(BiomeDictionary.Type.MAGICAL))&& Config.WINTER_TREE_PATCH.get()) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.configured(winterTree.getConfiguredFeature(random, true).config())
                    .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                    .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, (float) TREE_PATCHES_CHANCE, TREE_PATCHES_SIZE))));
        }


        /* BIOME DECORATION GENERATION */

        if (biomeName.contains(SpringBiome)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.SPRING_TREES);
            base.add(() -> ModConfiguredFeatures.SPRING_DANDELION);
        }

        if (biomeName.contains(SummerBiome)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.SUMMER_TREES);
            base.add(() -> ModConfiguredFeatures.SUMMER_SUNFLOWER);
        }

        if (biomeName.contains(AutumnBiome)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.AUTUMN_TREES);
            base.add(() -> ModConfiguredFeatures.AUTUMN_PUMPKINS);
        }

        if (biomeName.contains(WinterBiome)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> ModConfiguredFeatures.WINTER_TREES);
            base.add(() -> ModConfiguredFeatures.WINTER_FLOWERS);
        }
    }
}
