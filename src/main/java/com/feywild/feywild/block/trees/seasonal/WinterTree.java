package com.feywild.feywild.block.trees.seasonal;

import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.DecoratingBlobFoliagePlacer;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

public class WinterTree extends BaseTree {

    private final FeyLeavesBlock blueLeaves;
    private final FeyLeavesBlock lightBlueLeaves;

    public WinterTree(ModX mod) {
        super(mod);
        this.blueLeaves = new FeyLeavesBlock(ModParticles.winterLeafParticle, 14);
        this.lightBlueLeaves = new FeyLeavesBlock(ModParticles.winterLeafParticle, 14);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.registerNamed(Registries.BLOCK, "blue_leaves", this.blueLeaves);
        builder.registerNamed(Registries.BLOCK, "light_blue_leaves", this.lightBlueLeaves);
        builder.register(Registries.FOLIAGE_PLACER_TYPE, LeavesPlacer.TYPE);
    }

    @Override
    protected List<Block> getAllLeaves() {
        return List.of(this.blueLeaves, this.lightBlueLeaves);
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
        if (level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
            level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, 1 + random.nextInt(2)));
        }
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.winterSparkleParticle;
    }

    private static class LeavesPlacer extends DecoratingBlobFoliagePlacer {

        public static final FoliagePlacerType<LeavesPlacer> TYPE = makeType(LeavesPlacer::new);

        public LeavesPlacer(IntProvider radiusSpread, IntProvider heightSpread, int height) {
            super(radiusSpread, heightSpread, height);
        }

        @Nonnull
        @Override
        protected FoliagePlacerType<?> type() {
            return TYPE;
        }

        @Override
        protected void decorateLeaves(BlockState state, WorldGenLevel level, BlockPos pos, RandomSource random) {
            BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
            for (int i = 0; i < 30; i++) {
                if (level.getBlockState(mutableBlockPos).isAir() && level.getBlockState(mutableBlockPos.below()).is(Blocks.GRASS_BLOCK)) {
                    level.setBlock(mutableBlockPos, Blocks.SNOW.defaultBlockState(), 19);
                    if (level.getBlockState(mutableBlockPos.below()).hasProperty(GrassBlock.SNOWY)) {
                        level.setBlock(mutableBlockPos.below(), level.getBlockState(mutableBlockPos.below()).setValue(GrassBlock.SNOWY, true), 19);
                    }
                    break;
                }
                mutableBlockPos.move(Direction.DOWN);
            }
        }
    }
}
