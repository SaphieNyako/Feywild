package com.feywild.feywild.world.dimension.feywild.features.specialfeatures;

import com.feywild.feywild.world.dimension.feywild.util.FeywildWorldGenUtil;
import com.feywild.feywild.world.dimension.feywild.util.HorizontalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnPumpkinsFeature extends Feature<NoneFeatureConfiguration> {

    public AutumnPumpkinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextInt(3) == 0) {
            return FeywildWorldGenUtil.generateTries(context, 9, this::tryGenerate);
        } else {
            return false;
        }
    }

    private boolean tryGenerate(FeaturePlaceContext<NoneFeatureConfiguration> context, HorizontalPos horizontalPos) {
        BlockPos target = FeywildWorldGenUtil.highestFreeBlock(context.level(), horizontalPos, FeywildWorldGenUtil::passReplaceableAndLeaves);
        if (context.level().getBlockState(target.below()).canOcclude() && context.level().getBlockState(target.above()).isAir()) {
            return context.level().setBlock(target, this.getPumpkinState(context.random()), 3);
        } else {
            return false;
        }
    }

    private BlockState getPumpkinState(Random random) {
        return switch (random.nextInt(10)) {
            case 0 -> Blocks.JACK_O_LANTERN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            default -> Blocks.PUMPKIN.defaultBlockState();
        };
    }
}
