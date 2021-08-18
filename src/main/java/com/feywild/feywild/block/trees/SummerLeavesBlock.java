package com.feywild.feywild.block.trees;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.particles.ModParticles;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class SummerLeavesBlock extends FeyLeavesBlock {

    public SummerLeavesBlock(ModX mod) {
        super(mod);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        super.animateTick(state, world, pos, rand);
        if (world.isEmptyBlock(pos.below()) && rand.nextInt(20) == 1 && ClientConfig.tree_particles) {
            world.addParticle(ModParticles.summerLeafParticle, pos.getX() + rand.nextDouble(), pos.getY(),pos.getZ()+ rand.nextDouble(), 1, -0.1, 0 );
        }
    }
}
