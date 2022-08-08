package com.feywild.feywild.world.feature.trees;

import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.DecoratingBlobFoliagePlacer;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.util.data.TagAccess;

// UPDATE_TODO snow under the trees
public class WinterTree extends BaseTree {

    public WinterTree(ModX mod) {
        super(mod, () -> new FeyLeavesBlock(mod, 15, ModParticles.winterLeafParticle));
    }

    @Override
    protected FoliagePlacer getFoliagePlacer() {
        return new LeavesPlacer(
                UniformInt.of(this.getLeavesRadius(), this.getLeavesRadius()),
                UniformInt.of(this.getLeavesOffset(), this.getLeavesOffset()),
                this.getLeavesHeight()
        );
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (TagAccess.ROOT.has(BlockTags.DIRT, level.getBlockState(pos.below()).getBlock())) {
            level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, 1 + random.nextInt(2)));
        }
    }

    private static class LeavesPlacer extends DecoratingBlobFoliagePlacer {

        public LeavesPlacer(UniformInt radiusSpread, UniformInt heightSpread, int height) {
            super(radiusSpread, heightSpread, height);
        }

        @Override
        protected void decorateLeaves(BlockState state, WorldGenLevel level, BlockPos pos, RandomSource random) {
            BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
            for (int i = 0; i < 30; i++) {
                if (level.getBlockState(mutableBlockPos).isAir() && level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK)) {
                    level.setBlock(mutableBlockPos, Blocks.SNOW.defaultBlockState(), 19);
                    if (level.getBlockState(mutableBlockPos.below()).hasProperty(GrassBlock.SNOWY))
                        level.setBlock(mutableBlockPos.below(), level.getBlockState(mutableBlockPos.below()).setValue(GrassBlock.SNOWY, true), 19);
                }
                mutableBlockPos.move(Direction.DOWN);
            }
        }
    }
}
