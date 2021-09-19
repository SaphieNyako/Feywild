package com.feywild.feywild.block.trees;

import com.feywild.feywild.particles.ModParticles;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.BlockState;
import net.minecraft.particles.BasicParticleType;
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
    public int getChance() {
        return 20;
    }

    @Override
    public BasicParticleType getParticle() {
        return ModParticles.summerLeafParticle;
    }
}

