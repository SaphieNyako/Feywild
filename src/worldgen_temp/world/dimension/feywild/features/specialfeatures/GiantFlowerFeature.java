package com.feywild.feywild.world.dimension.feywild.features.specialfeatures;

import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.flower.GiantFlowerSeedItem;
import com.feywild.feywild.world.dimension.feywild.util.FeywildWorldGenUtil;
import com.feywild.feywild.world.dimension.feywild.util.HorizontalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;

public class GiantFlowerFeature extends Feature<NoneFeatureConfiguration> {

    private final GiantFlowerBlock block;

    public GiantFlowerFeature(GiantFlowerBlock block) {
        super(NoneFeatureConfiguration.CODEC);
        this.block = block;
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextInt(3) == 0) {
            return FeywildWorldGenUtil.generateTries(context, 3, this::trySpawnFlower);
        } else {
            return false;
        }
    }

    public boolean trySpawnFlower(FeaturePlaceContext<NoneFeatureConfiguration> context, HorizontalPos horizontalPos) {
        BlockPos target = FeywildWorldGenUtil.highestFreeBlock(context.level(), horizontalPos, FeywildWorldGenUtil::passReplaceableAndLeaves);
        if (!context.level().getBlockState(target.below()).is(BlockTags.DIRT)) {
            return false;
        }

        for (int i = 0; i < this.block.height; i++) {
            if (!context.level().getBlockState(target.above(i)).isAir()) {
                return false;
            }
        }

        GiantFlowerSeedItem.placeFlower(this.block, context.level(), target, context.random(), 3);
        return true;
    }
}
