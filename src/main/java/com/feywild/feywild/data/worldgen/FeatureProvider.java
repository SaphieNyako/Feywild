package com.feywild.feywild.data.worldgen;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.world.gen.feature.FeywildFeatures;
import com.feywild.feywild.world.gen.feature.GiantFlowerFeature;
import com.feywild.feywild.world.gen.feature.TemplateFeatureConfig;
import com.feywild.feywild.world.gen.template.TemplatePlacementAction;
import com.feywild.feywild.world.gen.template.TemplatePlacementActions;
import com.feywild.feywild.world.gen.tree.TreeProcessor;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.FeatureProviderBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FeatureProvider extends FeatureProviderBase {

    public final Holder<ConfiguredFeature<?, ?>> autumnPumpkins = this.feature(FeywildFeatures.autumnPumpkins);
    public final Holder<ConfiguredFeature<?, ?>> sunflowers = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.sunflower));
    public final Holder<ConfiguredFeature<?, ?>> dandelions = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.dandelion));
    public final Holder<ConfiguredFeature<?, ?>> crocus = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.crocus));

    public final Holder<ConfiguredFeature<?, ?>> springTree = this.makeTreeFeature(ModTrees.springTree);
    public final Holder<ConfiguredFeature<?, ?>> summerTree = this.makeTreeFeature(ModTrees.summerTree);
    public final Holder<ConfiguredFeature<?, ?>> autumnTree = this.makeTreeFeature(ModTrees.autumnTree, TemplatePlacementActions.mushroomAction);
    public final Holder<ConfiguredFeature<?, ?>> winterTree = this.makeTreeFeature(ModTrees.winterTree);
    public final Holder<ConfiguredFeature<?, ?>> blossomTree = this.makeTreeFeature(ModTrees.blossomTree);
    public final Holder<ConfiguredFeature<?, ?>> hexenTree = this.makeTreeFeature(ModTrees.hexenTree);
    
    public final Holder<ConfiguredFeature<?, ?>> springFlowerBlocks = flowerBlocks(
            Blocks.DANDELION, Blocks.POPPY, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP,
            Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY
    );

    public final Holder<ConfiguredFeature<?, ?>> summerFlowerBlocks = flowerBlocks(Blocks.BLUE_ORCHID);
    public final Holder<ConfiguredFeature<?, ?>> autumnFlowerBlocks = flowerBlocks(Blocks.DANDELION, Blocks.POPPY);
    public final Holder<ConfiguredFeature<?, ?>> winterFlowerBlocks = flowerBlocks(Blocks.LILY_OF_THE_VALLEY);

    public final Holder<PlacedFeature> springFlowerCheck = this.placement(springFlowerBlocks).inAir().build();
    public final Holder<ConfiguredFeature<?, ?>> springFlowers = this.flowers(springFlowerCheck);
    public final Holder<PlacedFeature> summerFlowerCheck = this.placement(summerFlowerBlocks).inAir().build();
    public final Holder<ConfiguredFeature<?, ?>> summerFlowers = this.flowers(summerFlowerCheck);
    public final Holder<PlacedFeature> autumnFlowerCheck = this.placement(autumnFlowerBlocks).inAir().build();
    public final Holder<ConfiguredFeature<?, ?>> autumnFlowers = this.flowers(autumnFlowerCheck);
    public final Holder<PlacedFeature> winterFlowerCheck = this.placement(winterFlowerBlocks).inAir().build();
    public final Holder<ConfiguredFeature<?, ?>> winterFlowers = this.flowers(winterFlowerCheck);

    public final Holder<ConfiguredFeature<?, ?>> feyGemOre = this.feature(Feature.ORE, new OreConfiguration(List.of(
            OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ModBlocks.feyGemOre.defaultBlockState()),
            OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), ModBlocks.feyGemOreDeepSlate.defaultBlockState())
    ), 5));

    public FeatureProvider(DatagenContext ctx) {
        super(ctx);
    }

    private Holder<ConfiguredFeature<?, ?>> flowers(Holder<PlacedFeature> featureCheck) {
        return this.feature(Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 6, 2, featureCheck));
    }

    private Holder<ConfiguredFeature<?, ?>> flowerBlocks(Block... blocks) {
        if (blocks.length == 1) {
            return this.feature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(blocks[0])));
        } else {
            return this.feature(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseProvider(
                    Math.abs("SomeRandomString".hashCode()),
                    new NormalNoise.NoiseParameters(0, 1.0D),
                    1 / 48f,
                    Arrays.stream(blocks).map(Block::defaultBlockState).toList()
            )));
        }
    }
    
    private Holder<ConfiguredFeature<?, ?>> makeTreeFeature(BaseTree tree, TemplatePlacementAction... actions) {
        List<Holder<PlacedFeature>> subPlacements = new ArrayList<>();
        WeightedRandomList<WeightedEntry.Wrapper<Block>> logs = WeightedRandomList.create(
                WeightedEntry.wrap(tree.getLogBlock(), 19),
                WeightedEntry.wrap(tree.getCrackedLogBlock(), 1)
        );
        WeightedRandomList<WeightedEntry.Wrapper<Block>> wood = WeightedRandomList.create(
                WeightedEntry.wrap(tree.getWoodBlock(), 1)
        );
        for (Block leaf : tree.getAllLeaves()) {
            ResourceLocation id = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(leaf));
            WeightedRandomList<WeightedEntry.Wrapper<Block>> leaves = WeightedRandomList.create(WeightedEntry.wrap(leaf, 1));
            StructureProcessorList list = new StructureProcessorList(List.of(new TreeProcessor(logs, wood, leaves)));
            Holder<StructureProcessorList> listHolder = this.registries.writableRegistry(Registries.PROCESSOR_LIST).register(ResourceKey.create(Registries.PROCESSOR_LIST, id), list, Lifecycle.stable());
            TemplateFeatureConfig.Builder cfgBuilder = TemplateFeatureConfig.builder();
            // TODO set templates and offset
            cfgBuilder.processors(listHolder);
            for (TemplatePlacementAction action : actions) {
                cfgBuilder.action(action);
            }
            ConfiguredFeature<?, ?> subFeature = new ConfiguredFeature<>(FeywildFeatures.template, cfgBuilder.build());
            Holder<ConfiguredFeature<?, ?>> subFeatureHolder = this.registries.writableRegistry(Registries.CONFIGURED_FEATURE).register(ResourceKey.create(Registries.CONFIGURED_FEATURE, id), subFeature, Lifecycle.stable());
            Holder<PlacedFeature> subPlacementHolder = this.registries.writableRegistry(Registries.PLACED_FEATURE).register(ResourceKey.create(Registries.PLACED_FEATURE, id), new PlacedFeature(subFeatureHolder, List.of()), Lifecycle.stable());
            subPlacements.add(subPlacementHolder);
        }
        
        if (subPlacements.isEmpty()) return this.feature(Feature.NO_OP);
        // Entries are checked one after another and the first one where random < chance is taken.
        // The probabilities assigned here make an even spread.
        
        List<WeightedPlacedFeature> featureList = new ArrayList<>();
        for (int i = 0; i < subPlacements.size() - 1; i++) {
            featureList.add(new WeightedPlacedFeature(subPlacements.get(i), 1f / (subPlacements.size() - i)));
        }
        RandomFeatureConfiguration cfg = new RandomFeatureConfiguration(List.copyOf(featureList), subPlacements.get(subPlacements.size() - 1));
        return this.feature(Feature.RANDOM_SELECTOR, cfg);
    }
}
