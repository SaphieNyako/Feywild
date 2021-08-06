package com.feywild.feywild.block.trees;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnLeavesBlock extends FeyLeavesBlock {

    public AutumnLeavesBlock(ModX mod) {
        super(mod);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        super.animateTick(state, world, pos, rand);
        if (world.isEmptyBlock(pos.below()) && rand.nextInt(2) == 1) {
            double windStrength = 5 + Math.cos((double) world.getGameTime() / 2000) * 2;
            double windX = Math.cos((double) world.getGameTime() / 1200) * windStrength;
            double windZ = Math.sin((double) world.getGameTime() / 1000) * windStrength;
            world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, windX, -1.0, windZ);
        }
    }
}

