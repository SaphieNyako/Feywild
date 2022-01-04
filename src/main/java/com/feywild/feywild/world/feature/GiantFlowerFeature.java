package com.feywild.feywild.world.feature;

import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.GiantFlowerSeedItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class GiantFlowerFeature extends Feature<NoneFeatureConfiguration> {

    private final GiantFlowerBlock block;

    public GiantFlowerFeature(GiantFlowerBlock block) {
        super(NoneFeatureConfiguration.CODEC);
        this.block = block;
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean success = false;
        for (int i = 0; i < 8; ++i) {
            BlockPos target = context.origin().offset(
                    context.random().nextInt(6) - context.random().nextInt(6),
                    context.random().nextInt(4) - context.random().nextInt(4),
                    context.random().nextInt(6) - context.random().nextInt(6)
            );
            if (this.trySpawnFlower(context.level(), target, context.random())) {
                success = true;
            }
        }

        return success;
    }

    public boolean trySpawnFlower(WorldGenLevel level, BlockPos pos, Random random) {
        if (!Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock())) {
            return false;
        }

        for (int i = 0; i < this.block.height; i++) {
            if (!level.getBlockState(pos.above(i)).isAir()) {
                return false;
            }
        }

        GiantFlowerSeedItem.placeFlower(this.block, level, pos, random, 3);
        return true;
    }
}
