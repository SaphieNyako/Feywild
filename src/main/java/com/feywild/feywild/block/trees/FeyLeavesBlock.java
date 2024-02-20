package com.feywild.feywild.block.trees;

import com.feywild.feywild.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class FeyLeavesBlock extends LeavesBlock implements Registerable, IForgeShearable, IForgeBlock {
    
    @Nullable
    private final Item item;
    private final SimpleParticleType particle;
    private final int particleChance;

    public FeyLeavesBlock(SimpleParticleType particle, int particleChance) {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((s, r, p, t) -> false).isSuffocating((s, r, p) -> false).isViewBlocking((s, r, p) -> false));
        this.item = new BlockItem(this, new Item.Properties());
        this.particle = particle;
        this.particleChance = particleChance;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        builder.register(Registries.ITEM, this.item);
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
            if (mc.player != null && mc.player.position().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 48 * 48) {
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
        if (rand.nextInt(particleChance) == 1 && level.isEmptyBlock(pos.below())) {
            level.addParticle(particle, pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextDouble(), 1, -0.1, 0);
        }
    }
}
