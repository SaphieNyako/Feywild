package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.world.gen.feature.FeywildFeatures;
import com.feywild.feywild.world.gen.feature.GiantFlowerFeature;
import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.FeatureData;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
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
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Arrays;
import java.util.List;

public class FeyFeatures extends FeatureData {

    public final Holder<ConfiguredFeature<?, ?>> autumnPumpkins = this.feature(FeywildFeatures.autumnPumpkins);
    public final Holder<ConfiguredFeature<?, ?>> sunflowers = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.sunflower));
    public final Holder<ConfiguredFeature<?, ?>> dandelions = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.dandelion));
    public final Holder<ConfiguredFeature<?, ?>> crocus = this.feature(FeywildFeatures.giantFlowers, new GiantFlowerFeature.Configuration(ModBlocks.crocus));
    
    public final Holder<ConfiguredFeature<?, ?>> springFlowerBlocks = flowerBlocks(
            Blocks.DANDELION, Blocks.POPPY, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP,
            Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY
    );
    
    public final Holder<ConfiguredFeature<?, ?>> summerFlowerBlocks = flowerBlocks(Blocks.BLUE_ORCHID);
    public final Holder<ConfiguredFeature<?, ?>> autumnFlowerBlocks = flowerBlocks(Blocks.DANDELION, Blocks.POPPY);
    public final Holder<ConfiguredFeature<?, ?>> winterFlowerBlocks = flowerBlocks(Blocks.LILY_OF_THE_VALLEY);
    
    public final Holder<PlacedFeature> springFlowerCheck = this.placement(springFlowerBlocks).inAir().build();
    public final Holder<PlacedFeature> summerFlowerCheck = this.placement(summerFlowerBlocks).inAir().build();
    public final Holder<PlacedFeature> autumnFlowerCheck = this.placement(autumnFlowerBlocks).inAir().build();
    public final Holder<PlacedFeature> winterFlowerCheck = this.placement(winterFlowerBlocks).inAir().build();
    
    public final Holder<ConfiguredFeature<?, ?>> springFlowers = this.flowers(springFlowerCheck);
    public final Holder<ConfiguredFeature<?, ?>> summerFlowers = this.flowers(summerFlowerCheck);
    public final Holder<ConfiguredFeature<?, ?>> autumnFlowers = this.flowers(autumnFlowerCheck);
    public final Holder<ConfiguredFeature<?, ?>> winterFlowers = this.flowers(winterFlowerCheck);
    
    public final Holder<ConfiguredFeature<?, ?>> feyGemOre = this.feature(Feature.ORE, new OreConfiguration(List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.feyGemOre.defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.feyGemOreDeepSlate.defaultBlockState())
    ), 5));
    
    public FeyFeatures(Properties properties) {
        super(properties);
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
}
