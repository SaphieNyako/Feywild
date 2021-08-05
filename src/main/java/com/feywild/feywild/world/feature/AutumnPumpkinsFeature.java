package com.feywild.feywild.world.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnPumpkinsFeature extends Feature<NoFeatureConfig> {

    public AutumnPumpkinsFeature() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public boolean place(@Nonnull ISeedReader world, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        boolean success = false;

        for (int i = 0; i < 32; ++i) {
            BlockPos target = pos.offset(
                    random.nextInt(8) - random.nextInt(8),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(8) - random.nextInt(8)
            );
            //noinspection deprecation
            if (world.getBlockState(target).isAir(world, target) && Tags.Blocks.DIRT.contains(world.getBlockState(target.below()).getBlock())) {
                if (random.nextInt(3) == 0) {
                    world.setBlock(target, getPumpkinState(random), 3);
                    success = true;
                }
            }
        }

        return success;
    }

    public BlockState getPumpkinState(Random random) {
        switch (random.nextInt(10)) {
            case 0: return Blocks.JACK_O_LANTERN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            case 1: return Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.from2DDataValue(random.nextInt(4)));
            default: return Blocks.PUMPKIN.defaultBlockState();
        }
    }
}
