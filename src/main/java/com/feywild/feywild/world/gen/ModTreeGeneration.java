package com.feywild.feywild.world.gen;


import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.*;
import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.MobConfig;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID)
public class ModTreeGeneration {


    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event){

        SpringTree springTree = new SpringTree();
        AutumnTree autumnTree = new AutumnTree();
        WinterTree winterTree = new WinterTree();
        SummerTree summerTree = new SummerTree();

        Random random = new Random();

        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        //SPRING TREE GENERATION
        if(types.contains(BiomeDictionary.Type.PLAINS) || types.contains(BiomeDictionary.Type.RIVER) || types.contains(BiomeDictionary.Type.FOREST)) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);


            base.add(() -> Feature.TREE.withConfiguration(springTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01f, 3))));

            //If Forest also spawn Autumn Trees
            if(types.contains(BiomeDictionary.Type.FOREST)) {

                base.add(() -> Feature.TREE.withConfiguration(autumnTree.getTreeFeature(random, true).getConfig())
                        .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                        .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01f, 3))));

            }

        }

        //SUMMER TREE GENERATION
        if(types.contains(BiomeDictionary.Type.HOT) || types.contains(BiomeDictionary.Type.LUSH)) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);


            base.add(() -> Feature.TREE.withConfiguration(summerTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01f, 3))));
        }

        //AUTUMN TREE GENERATION
        if(types.contains(BiomeDictionary.Type.SWAMP) || types.contains(BiomeDictionary.Type.MUSHROOM) || types.contains(BiomeDictionary.Type.SPOOKY)) {

            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);


            base.add(() -> Feature.TREE.withConfiguration(autumnTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01f, 3))));
        }

        //WINTER TREE GENERATION
        if(types.contains(BiomeDictionary.Type.DEAD) || types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD)) {
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);


            base.add(() -> Feature.TREE.withConfiguration(winterTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01f, 3))));
        }

        //MAGICAL BIOMES
        if(types.contains(BiomeDictionary.Type.MAGICAL)){
            List<Supplier<ConfiguredFeature<?, ?>>> base =
                    event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

            base.add(() -> Feature.TREE.withConfiguration(springTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.005f, 3))));

            base.add(() -> Feature.TREE.withConfiguration(summerTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.005f, 3))));

            base.add(() -> Feature.TREE.withConfiguration(autumnTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.005f, 3))));

            base.add(() -> Feature.TREE.withConfiguration(winterTree.getTreeFeature(random, true).getConfig())
                    .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                    .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.005f, 3))));


        }

    }
}
