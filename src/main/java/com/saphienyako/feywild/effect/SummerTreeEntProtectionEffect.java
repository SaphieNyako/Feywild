package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;

public class SummerTreeEntProtectionEffect extends MobEffect {
    
    protected SummerTreeEntProtectionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFD700);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity living, int amplifier) {
        if (!living.level().isClientSide) {
            AABB box = new AABB(living.blockPosition()).inflate(amplifier + 1);

            living.level().getEntities(null, box).forEach(entity -> {
                if (entity instanceof Monster || entity instanceof Projectile) {

                    double dx = entity.getX() - living.getX();
                    double dy = entity.getY() - living.getY();
                    double dz = entity.getZ() - living.getZ();

                    entity.setDeltaMovement(
                            dx * 0.8,
                            dy * 0.8,
                            dz * 0.8
                    );

                    entity.playSound(SoundEvents.AZALEA_LEAVES_BREAK);
                }
            });
        } else {
            living.level().addParticle(
                    ModParticles.SUMMER_LEAF_PARTICLE.get(),
                    living.getX() + living.getRandom().nextDouble() * 1.5 - 0.75,
                    living.getY() + living.getRandom().nextDouble() * 2.0 + 2.0,
                    living.getZ() + living.getRandom().nextDouble() * 1.5 - 0.75,
                    0.0,
                    -0.05,
                    0.0
            );
        }

    }
}
