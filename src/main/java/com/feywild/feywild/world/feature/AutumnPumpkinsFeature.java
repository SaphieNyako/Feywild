package com.feywild.feywild.world.feature;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnPumpkinsFeature extends Feature<NoneFeatureConfiguration> {

    public AutumnPumpkinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(@Nonnull WorldGenLevel level, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull NoneFeatureConfiguration config) {
        boolean success = false;

        for (int i = 0; i < 32; ++i) {
            BlockPos target = pos.offset(
                    random.nextInt(8) - random.nextInt(8),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(8) - random.nextInt(8)
            );
            //noinspection deprecation
            if (level.getBlockState(target).isAir(level, target) && Tags.Blocks.DIRT.contains(level.getBlockState(target.below()).getBlock())) {
                if (random.nextInt(3) == 0) {
                    level.setBlock(target, this.getPumpkinState(random), 3);
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
