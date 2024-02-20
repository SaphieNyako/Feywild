package com.feywild.feywild.effects;

import com.feywild.feywild.particles.ModParticles;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;

public class WindWalkEffect extends MobEffect {

    protected WindWalkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x994C00);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity living, int amplifier) {
        super.applyEffectTick(living, amplifier);
        if (!living.level().isClientSide) {
            AABB box = new AABB(living.blockPosition()).inflate(amplifier);
            living.level().getEntities(null, box).forEach(entity -> {
                if (entity instanceof Monster || entity instanceof Projectile) {
                    entity.setDeltaMovement((entity.getX() - living.getX()) / 10, (entity.getY() - living.getY()) / 10, (entity.getZ() - living.getZ()) / 10);
                }
            });
        } else {
            living.level().addParticle(ModParticles.autumnLeafParticle, living.getRandom().nextDouble() * 1.5 + living.getX() - 1, living.getRandom().nextDouble() * 2 + living.getY() + 2, living.getRandom().nextDouble() * 1.5 + living.getZ() - 1, 0, -0.05, 0);
        }
    }
}
