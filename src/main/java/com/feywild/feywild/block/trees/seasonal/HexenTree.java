package com.feywild.feywild.block.trees.seasonal;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

public class HexenTree extends BaseTree {

    private final FeyLeavesBlock blackLeaves;
    private final FeyLeavesBlock purpleLeaves;

    public HexenTree(ModX mod) {
        super(mod);
        this.blackLeaves = new FeyLeavesBlock(ModParticles.hexenLeafParticle, 14);
        this.purpleLeaves = new FeyLeavesBlock(ModParticles.hexenLeafParticle, 14);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.registerNamed(Registries.BLOCK, "black_leaves", this.blackLeaves);
        builder.registerNamed(Registries.BLOCK, "purple_leaves", this.purpleLeaves);
    }

    @Override
    protected List<Block> getAllLeaves() {
        return List.of(this.blackLeaves, this.purpleLeaves);
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.2) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.winterSparkleParticle;
    }

    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.PUMPKIN.defaultBlockState();
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2 -> ModBlocks.feyMushroom.defaultBlockState();
            default -> Blocks.FERN.defaultBlockState();
        };
    }
}
