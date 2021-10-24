package com.feywild.feywild.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnPumpkinsFeature extends Feature<NoneFeatureConfiguration> {

    public AutumnPumpkinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean success = false;

        for (int i = 0; i < 32; ++i) {
            BlockPos target = context.origin().offset(
                    context.random().nextInt(8) - context.random().nextInt(8),
                    context.random().nextInt(4) - context.random().nextInt(4),
                    context.random().nextInt(8) - context.random().nextInt(8)
            );
            if (context.level().getBlockState(target).isAir() && Tags.Blocks.DIRT.contains(context.level().getBlockState(target.below()).getBlock())) {
                if (context.random().nextInt(3) == 0) {
                    context.level().setBlock(target, this.getPumpkinState(context.random()), 3);
                    success = true;
                }
            }
        }

        return success;
    }

    public BlockState getPumpkinState(Random random) {
        return switch (random.nextInt(10)) {
            case 0 -> Blocks.JACK_O_LANTERN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            default -> Blocks.PUMPKIN.defaultBlockState();
        };
    }
}
