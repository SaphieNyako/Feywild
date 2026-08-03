package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WinterTreeEntProtectionEffect extends MobEffect {

    private static final double BASE_RADIUS = 1.4D;
    private static final double PUSH_STRENGTH = 1.5D;

    public WinterTreeEntProtectionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x4A90E2);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity living, int amplifier) {
        if (living.level().isClientSide) {
            return true;
        }

        double shieldRadius = BASE_RADIUS + amplifier * 0.75D;

        pushEntitiesAway(living, shieldRadius);

        if (living.level() instanceof ServerLevel serverLevel) {
            spawnShieldParticles(living, serverLevel, shieldRadius);
        }

        return true;
    }

    private void pushEntitiesAway(LivingEntity living, double shieldRadius) {
        AABB area = living.getBoundingBox().inflate(shieldRadius);

        List<Entity> entities = living.level().getEntities(living, area, entity -> entity.isAlive() && (entity instanceof Monster || entity instanceof Projectile));

        for (Entity entity : entities) {
            Vec3 direction = entity.position().subtract(living.position());

            direction = new Vec3(direction.x, 0.0D, direction.z);

            if (direction.lengthSqr() < 1.0E-4D) {
                direction = new Vec3(living.getRandom().nextDouble() - 0.5D, 0.0D, living.getRandom().nextDouble() - 0.5D);
            }

            direction = direction.normalize();

            entity.setDeltaMovement(direction.x * PUSH_STRENGTH, entity.getDeltaMovement().y + 0.1D, direction.z * PUSH_STRENGTH);

            entity.hurtMarked = true;
        }
    }

    private void spawnShieldParticles(LivingEntity living, ServerLevel serverLevel, double shieldRadius) {

        if (living.tickCount % 2 != 0) {
            return;
        }

        spawnParticleRing(
                living,
                serverLevel,
                8,
                shieldRadius * 0.9D,
                living.getBbHeight() * 0.25D,
                0.22D,
                0.0D
        );

        spawnParticleRing(
                living,
                serverLevel,
                4,
                shieldRadius * 0.72D,
                living.getBbHeight() * 0.58D,
                -0.17D,
                Math.PI / 12.0D
        );

        spawnParticleRing(
                living,
                serverLevel,
                2,
                shieldRadius * 0.45D,
                living.getBbHeight() * 0.95D,
                0.08D,
                Math.PI / 8.0D
        );
    }

    private void spawnParticleRing(LivingEntity living, ServerLevel serverLevel, int particleCount, double radius, double height, double rotationSpeed, double angleOffset) {
        double rotation = living.tickCount * rotationSpeed + angleOffset;

        double angleStep = (Math.PI * 2.0D) / particleCount;

        for (int i = 0; i < particleCount; i++) {
            double angle = rotation + i * angleStep;

            double verticalWave = Math.sin(angle * 2.0D + living.tickCount * 0.08D) * 0.12D;

            double x = living.getX() + Math.cos(angle) * radius;

            double y = living.getY() + height + verticalWave;
            double z = living.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(
                    ModParticles.WINTER_LEAF_PARTICLE.get(),
                    x,
                    y,
                    z,
                    1,
                    0.02D,
                    0.02D,
                    0.02D,
                    0.0D
            );
        }
    }
}

