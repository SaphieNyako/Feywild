package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.worldgen.features.ModFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FEY_GEM_ORE_KEY = registerKey("fey_gem_ore_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MUSHROOM_KEY = registerKey("orange_mushroom_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MUSHROOM_KEY = registerKey("yellow_mushroom_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_MUSHROOM_KEY = registerKey("green_mushroom_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHT_BLUE_MUSHROOM_KEY = registerKey("light_blue_mushroom_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MUSHROOM_KEY = registerKey("blue_mushroom_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MUSHROOM_KEY = registerKey("purple_mushroom_key");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_MUSHROOM_KEY = registerKey("pink_mushroom_key");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AUTUMN_TREE_KEY = registerKey("autumn_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRING_TREE_KEY = registerKey("spring_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SUMMER_TREE_KEY = registerKey("summer_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WINTER_TREE_KEY = registerKey("winter_tree");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, ORANGE_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                colouredMushroomConfig(ModBlocks.ORANGE_MUSHROOM_BLOCK.get()));

        register(context, YELLOW_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                colouredMushroomConfig(ModBlocks.YELLOW_MUSHROOM_BLOCK.get()));

        register(context, GREEN_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                colouredMushroomConfig(ModBlocks.GREEN_MUSHROOM_BLOCK.get()));

        register(context, LIGHT_BLUE_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                colouredMushroomConfig(ModBlocks.LIGHT_BLUE_MUSHROOM_BLOCK.get()));

        register(context,BLUE_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                colouredMushroomConfig(ModBlocks.BLUE_MUSHROOM_BLOCK.get()));

        register(context, PURPLE_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                colouredMushroomConfig(ModBlocks.PURPLE_MUSHROOM_BLOCK.get()));

        register(context, PINK_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                colouredMushroomConfig(ModBlocks.PINK_MUSHROOM_BLOCK.get()));

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        // RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
        // RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> feyGemOres = List.of(OreConfiguration.target(stoneReplaceable, ModBlocks.FEY_GEM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.FEY_GEM_ORE_DEEP_SLATE.get().defaultBlockState()));

        register(context,FEY_GEM_ORE_KEY, Feature.ORE, new OreConfiguration(feyGemOres, 5));

        register(context, AUTUMN_TREE_KEY, ModFeatures.AUTUMN_TREE.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, SPRING_TREE_KEY, ModFeatures.SPRING_TREE.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, SUMMER_TREE_KEY, ModFeatures.SUMMER_TREE.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, WINTER_TREE_KEY, ModFeatures.WINTER_TREE.get(), NoneFeatureConfiguration.INSTANCE);
    }

    private static HugeMushroomFeatureConfiguration colouredMushroomConfig(Block mushroomBlock) {
        return new HugeMushroomFeatureConfiguration(
                BlockStateProvider.simple(
                        mushroomBlock.defaultBlockState()
                                .setValue(HugeMushroomBlock.UP, true)
                                .setValue(HugeMushroomBlock.DOWN, false)
                                .setValue(HugeMushroomBlock.NORTH, true)
                                .setValue(HugeMushroomBlock.SOUTH, true)
                                .setValue(HugeMushroomBlock.EAST, true)
                                .setValue(HugeMushroomBlock.WEST, true)
                ),
                BlockStateProvider.simple(
                        Blocks.MUSHROOM_STEM.defaultBlockState()
                                .setValue(HugeMushroomBlock.UP, false)
                                .setValue(HugeMushroomBlock.DOWN, false)
                ),
                2
        );
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Feywild.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
