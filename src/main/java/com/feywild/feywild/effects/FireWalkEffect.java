package com.feywild.feywild.effects;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import java.util.Random;

public class FireWalkEffect extends MobEffect {


    protected FireWalkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xff8c00);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity living, int amplifier) {
        super.applyEffectTick(living, amplifier);
        Level level = living.level;
        if (!level.isClientSide) {
            AABB box = new AABB(living.blockPosition()).inflate(amplifier);
            level.getEntities(null, box).forEach(entity -> {
                if (entity instanceof Monster) {
                    entity.setSecondsOnFire(60);
                    entity.hurt(DamageSource.GENERIC, 2);
                    FeywildMod.getNetwork().sendParticles(living.level, ParticleMessage.Type.MONSTER_FIRE, living.getX(), living.getY(), living.getZ(), entity.getX(), entity.getY(), entity.getZ());
                }
            });
        } else {
            Random random = new Random();
            if (random.nextInt(5) < 1) {
                level.addParticle(ParticleTypes.LAVA, living.getRandom().nextDouble() * 1.5 + living.getX() - 1, living.getRandom().nextDouble() * 2 + living.getY() + 2, living.getRandom().nextDouble() * 1.5 + living.getZ() - 1, 0, -0.05, 0);
            }
        }
    }

}
