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

public class SpringTree extends BaseTree {

    private final FeyLeavesBlock greenLeaves;
    private final FeyLeavesBlock limeLeaves;
    private final FeyLeavesBlock cyanLeaves;

    public SpringTree(ModX mod) {
        super(mod);
        this.greenLeaves = new FeyLeavesBlock(ModParticles.springLeafParticle, 14);
        this.limeLeaves = new FeyLeavesBlock(ModParticles.springLeafParticle, 14);
        this.cyanLeaves = new FeyLeavesBlock(ModParticles.springLeafParticle, 14);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        super.registerAdditional(ctx, builder);
        builder.registerNamed(Registries.BLOCK, "green_leaves", this.greenLeaves);
        builder.registerNamed(Registries.BLOCK, "lime_leaves", this.limeLeaves);
        builder.registerNamed(Registries.BLOCK, "cyan_leaves", this.cyanLeaves);
    }

    @Override
    public List<Block> getAllLeaves() {
        return List.of(this.greenLeaves, this.limeLeaves, this.cyanLeaves);
    }

    @Override
    public void decorateSaplingGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.3) {
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
