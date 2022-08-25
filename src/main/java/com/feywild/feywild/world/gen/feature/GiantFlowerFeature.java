package com.feywild.feywild.world.gen.feature;

import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.GiantFlowerSeedItem;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class GiantFlowerFeature extends Feature<GiantFlowerFeature.Configuration> {
    
    public GiantFlowerFeature() {
        super(Configuration.CODEC);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<GiantFlowerFeature.Configuration> context) {
        Block block = context.config().block();
        if (!(block instanceof GiantFlowerBlock flower)) return false;
        for (int i = 0; i < flower.height; i++) {
            if (!context.level().getBlockState(context.origin().above(i)).isAir()) {
                return false;
            }
        }

        GiantFlowerSeedItem.placeFlower(flower, context.level(), context.origin(), context.random(), 3);
        return true;
    }
    
    public record Configuration(Block block) implements FeatureConfiguration {

        public static final Codec<Configuration> CODEC = ForgeRegistries.BLOCKS.getCodec().fieldOf("block").codec().xmap(Configuration::new, Configuration::block);
    }
}
