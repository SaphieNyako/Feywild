package com.feywild.feywild.block.trees.seasonal;

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

public class AutumnTree extends BaseTree {

    private final FeyLeavesBlock brownLeaves;
    private final FeyLeavesBlock darkGrayLeaves;
    private final FeyLeavesBlock lightGrayLeaves;
    private final FeyLeavesBlock redLeaves;
    
    public AutumnTree(ModX mod) {
        super(mod);
        this.brownLeaves = new FeyLeavesBlock(ModParticles.autumnLeafParticle, 14);
        this.darkGrayLeaves = new FeyLeavesBlock(ModParticles.autumnLeafParticle, 14);
        this.lightGrayLeaves = new FeyLeavesBlock(ModParticles.autumnLeafParticle, 14);
        this.redLeaves = new FeyLeavesBlock(ModParticles.autumnLeafParticle, 14);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.registerNamed(Registries.BLOCK, "brown_leaves", this.brownLeaves);
        builder.registerNamed(Registries.BLOCK, "dark_gray_leaves", this.darkGrayLeaves);
        builder.registerNamed(Registries.BLOCK, "light_gray_leaves", this.lightGrayLeaves);
        builder.registerNamed(Registries.BLOCK, "red_leaves", this.redLeaves);
    }

    @Override
    public List<Block> getAllLeaves() {
        return List.of(this.brownLeaves, this.darkGrayLeaves, this.lightGrayLeaves, this.redLeaves);
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.2) {
            level.setBlockAndUpdate(pos, getDecorationBlock(random));
        }
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.autumnSparkleParticle;
    }
    
    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.PUMPKIN.defaultBlockState();
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2 -> Blocks.RED_MUSHROOM.defaultBlockState();
            case 3 -> Blocks.BROWN_MUSHROOM.defaultBlockState();
            default -> Blocks.FERN.defaultBlockState();
        };
    }
}
