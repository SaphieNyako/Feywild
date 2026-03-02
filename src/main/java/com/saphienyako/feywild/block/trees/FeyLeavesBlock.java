package com.saphienyako.feywild.block.trees;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class FeyLeavesBlock extends LeavesBlock {

    private final Supplier<SimpleParticleType> particle;
    private final int particleChance;
    public FeyLeavesBlock(Properties properties, Supplier<SimpleParticleType> particle, int particleChance) {
        super(properties);
        this.particle = particle;
        this.particleChance = particleChance;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {

            // Don't add particles if the blocks are far away
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.position().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 48 * 48) {
                animateLeaves(state, level, pos, rand);

        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void animateLeaves(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (rand.nextInt(15) == 0 && level.isRainingAt(pos.above())) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                double x = pos.getX() + rand.nextDouble();
                double y = pos.getY() - 0.05;
                double z = pos.getZ() + rand.nextDouble();
                level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
            }
        }
        if (rand.nextInt(particleChance) == 1 && level.isEmptyBlock(pos.below())) {
            level.addParticle(particle.get(), pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextDouble(), 1, -0.1, 0);
        }
    }
}
