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

public class BlossomTree extends BaseTree {

    private final FeyLeavesBlock pinkLeaves;
    private final FeyLeavesBlock magentaLeaves;
    private final FeyLeavesBlock whiteLeaves;

    public BlossomTree(ModX mod) {
        super(mod);
        this.pinkLeaves = new FeyLeavesBlock(ModParticles.blossomLeafParticle, 14);
        this.magentaLeaves = new FeyLeavesBlock(ModParticles.blossomLeafParticle, 14);
        this.whiteLeaves = new FeyLeavesBlock(ModParticles.blossomLeafParticle, 14);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.registerNamed(Registries.BLOCK, "pink_leaves", this.pinkLeaves);
        builder.registerNamed(Registries.BLOCK, "magenta_leaves", this.magentaLeaves);
        builder.registerNamed(Registries.BLOCK, "white_leaves", this.whiteLeaves);
    }

    @Override
    protected List<Block> getAllLeaves() {
        return List.of(this.pinkLeaves, this.magentaLeaves, this.whiteLeaves);
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.2) {
            if (level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
                level.setBlockAndUpdate(pos, getDecorationBlock(random));
            }
        }
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.springSparkleParticle;
    }

    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(10)) {
            case 0 -> Blocks.RED_TULIP.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.ORANGE_TULIP.defaultBlockState();
            case 3 -> Blocks.BLUE_ORCHID.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.AZURE_BLUET.defaultBlockState();
            case 6 -> Blocks.WHITE_TULIP.defaultBlockState();
            case 7 -> Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            default -> Blocks.GRASS.defaultBlockState();
        };
    }
}
