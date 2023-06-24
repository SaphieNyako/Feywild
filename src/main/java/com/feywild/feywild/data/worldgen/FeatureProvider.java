package com.feywild.feywild.data.worldgen;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.world.gen.feature.FeywildFeatures;
import com.feywild.feywild.world.gen.feature.GiantFlowerFeature;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.FeatureProviderBase;

import java.util.Arrays;
import java.util.List;

public class FeatureProvider extends FeatureProviderBase {

    public final Holder<ConfiguredFeature<?, ?>> autumnPumpkins = this.feature(FeywildFeatures.autumnPumpkins);
    public final Holder<ConfiguredFeature<?, ?>> sunflowers = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.sunflower));
    public final Holder<ConfiguredFeature<?, ?>> dandelions = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.dandelion));
    public final Holder<ConfiguredFeature<?, ?>> crocus = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.crocus));

    public final Holder<ConfiguredFeature<?, ?>> springTree = this.feature(FeywildFeatures.springTree);
    public final Holder<ConfiguredFeature<?, ?>> summerTree = this.feature(FeywildFeatures.summerTree);
    public final Holder<ConfiguredFeature<?, ?>> autumnTree = this.feature(FeywildFeatures.autumnTree);
    public final Holder<ConfiguredFeature<?, ?>> winterTree = this.feature(FeywildFeatures.winterTree);
    public final Holder<ConfiguredFeature<?, ?>> blossomTree = this.feature(FeywildFeatures.blossomTree);
    public final Holder<ConfiguredFeature<?, ?>> hexenTree = this.feature(FeywildFeatures.hexenTree);

    public final Holder<ConfiguredFeature<?, ?>> treeMushrooms = this.feature(FeywildFeatures.treeMushroom);

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


    //  public final Holder<PlacedFeature> springTreeCheck = this.placement(ModTrees.springTree.getConfiguredFeature()).build();
    //   public final Holder<ConfiguredFeature<?,?>> springTree = this.trees(springTreeCheck);

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

  /*  private Holder<ConfiguredFeature<?, ?>> trees(Holder<PlacedFeature> featureCheck) {
        return this.feature(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(featureCheck, 0.5F)), featureCheck));
    } */

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


}
