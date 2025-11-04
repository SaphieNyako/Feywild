package com.saphienyako.feywild.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;

public class SummerBlessingEffect extends MobEffect {
    
    protected SummerBlessingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xff8c00);
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
                if (entity instanceof Monster) {
                    entity.setSecondsOnFire(60);
                    entity.hurt(living.level().damageSources().onFire(), 2);
                }
            });
        } else {
            if (living.getRandom().nextInt(5) < 1) {
                living.level().addParticle(ParticleTypes.LAVA, living.getRandom().nextDouble() * 1.5 + living.getX() - 1, living.getRandom().nextDouble() * 2 + living.getY() + 2, living.getRandom().nextDouble() * 1.5 + living.getZ() - 1, 0, -0.05, 0);
            }
        }
    }
}
