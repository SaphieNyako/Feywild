package com.feywild.feywild.world.feature;

import com.feywild.feywild.block.flower.GiantFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class GiantFlowerFeature extends Feature<NoFeatureConfig> {

    private final GiantFlowerBlock block;
    
    public GiantFlowerFeature(GiantFlowerBlock block) {
        super(NoFeatureConfig.CODEC);
        this.block = block;
    }

    @Override
    public boolean place(@Nonnull ISeedReader world, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull NoFeatureConfig config) {
        boolean success = false;
        for (int i = 0; i < 8; ++i) {
            BlockPos target = pos.offset(
                    random.nextInt(6) - random.nextInt(6),
                    random.nextInt(4) - random.nextInt(4),
                    random.nextInt(6) - random.nextInt(6)
            );
            if (trySpawnFlower(world, target, random)) {
                success = true;
            }
        }

        return success;
    }

    public boolean trySpawnFlower(ISeedReader world, BlockPos pos, Random random) {
        if (!Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock())) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            //noinspection deprecation
            if (!world.getBlockState(pos.above(i)).isAir()) {
                return false;
            }
        }

        world.setBlock(pos, this.block.defaultBlockState().setValue(GiantFlowerBlock.PART, 0), 3);
        world.setBlock(pos.above(1), this.block.defaultBlockState().setValue(GiantFlowerBlock.PART, 1), 3);
        world.setBlock(pos.above(2), this.block.defaultBlockState().setValue(GiantFlowerBlock.PART, 2), 3);
        world.setBlock(pos.above(3), this.block.flowerState(world, pos.above(3), random).setValue(GiantFlowerBlock.PART, 3), 3);
        return true;
    }
}
