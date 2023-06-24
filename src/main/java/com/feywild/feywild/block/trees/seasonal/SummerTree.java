package com.feywild.feywild.block.trees.seasonal;

import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

public class SummerTree extends BaseTree {

    private final FeyLeavesBlock orangeLeaves;
    private final FeyLeavesBlock yellowLeaves;

    public SummerTree(ModX mod) {
        super(mod);
        this.orangeLeaves = new FeyLeavesBlock(ModParticles.summerLeafParticle, 14);
        this.yellowLeaves = new FeyLeavesBlock(ModParticles.summerLeafParticle, 14);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.registerNamed(Registries.BLOCK, "orange_leaves", this.orangeLeaves);
        builder.registerNamed(Registries.BLOCK, "yellow_leaves", this.yellowLeaves);
    }

    @Override
    public List<Block> getAllLeaves() {
        return List.of(this.orangeLeaves, this.yellowLeaves);
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.summerSparkleParticle;
    }

    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(30)) {
            case 0 -> Blocks.OXEYE_DAISY.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.POPPY.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.CORNFLOWER.defaultBlockState();
            default -> Blocks.GRASS.defaultBlockState();
        };
    }
}
