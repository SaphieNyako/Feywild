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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.RegistrationContext;

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
    }

    @Override
    public List<Block> getAllLeaves() {
        return List.of(this.blueLeaves, this.lightBlueLeaves);
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
}
