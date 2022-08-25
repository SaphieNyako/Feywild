package com.feywild.feywild.world.gen.feature;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;

public class AutumnPumpkinsFeature extends Feature<NoneFeatureConfiguration> {

    public AutumnPumpkinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.level().getBlockState(context.origin().below()).canOcclude() && context.level().getBlockState(context.origin().above()).isAir()) {
            return context.level().setBlock(context.origin(), this.getPumpkinState(context.random()), 3);
        } else {
            return false;
        }
    }

    private BlockState getPumpkinState(RandomSource random) {
        return switch (random.nextInt(10)) {
            case 0 -> Blocks.JACK_O_LANTERN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            default -> Blocks.PUMPKIN.defaultBlockState();
        };
    }
}
