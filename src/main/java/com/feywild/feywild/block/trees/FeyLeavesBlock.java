package com.feywild.feywild.block.trees;

import com.feywild.feywild.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;

public class FeyLeavesBlock extends LeavesBlockBase implements Registerable, IForgeShearable, IForgeBlock {

    public static final int MAX_PARTICLE_DISTANCE = 48;

    private final int chance;
    private final SimpleParticleType particle;

    public FeyLeavesBlock(ModX mod, int chance, SimpleParticleType particle) {
        super(mod, BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS)
                .noOcclusion().isValidSpawn((s, r, p, t) -> false).isSuffocating((s, r, p) -> false).isViewBlocking((s, r, p) -> false));
        this.chance = chance;
        this.particle = particle;
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        ctx.enqueue(() -> ComposterBlock.add(0.4f, this));
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (ClientConfig.tree_particles) {
            // Don't add particles if the blocks are far away
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.position().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < MAX_PARTICLE_DISTANCE * MAX_PARTICLE_DISTANCE) {
                animateLeaves(state, level, pos, rand);
            }
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
        if (rand.nextInt(chance) == 1 && level.isEmptyBlock(pos.below())) {
            level.addParticle(particle, pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextDouble(), 1, -0.1, 0);
        }
    }
}
