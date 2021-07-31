package com.feywild.feywild.effects;

import com.feywild.feywild.particles.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WindWalkEffect extends Effect {
    
    protected WindWalkEffect() {
        super(EffectType.BENEFICIAL, 0x994C00);
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity living, int amplifier) {
        super.applyEffectTick(living, amplifier);
        World world = living.level;
        if (!world.isClientSide) {
            AxisAlignedBB box = new AxisAlignedBB(living.blockPosition()).inflate(amplifier);
            world.getEntities(null, box).forEach(entity -> {
                if (entity instanceof MonsterEntity || entity instanceof ProjectileEntity) {
                    entity.setDeltaMovement((entity.getX() - living.getX()) / 10, (entity.getY() - living.getY()) / 10, (entity.getZ() - living.getZ()) / 10);
                }
            });
        } else {
            world.addParticle(ModParticles.LEAF_PARTICLE.get(),living.getRandom().nextDouble() * 1.5 + living.getX() -1,living.getRandom().nextDouble() * 2 + living.getY() + 2,living.getRandom().nextDouble() * 1.5 + living.getZ() - 1,0,0,0);
        }
    }
}
