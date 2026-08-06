package com.saphienyako.feywild.entity.goals.oberon;

import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class OberonRearingGoal extends Goal {

   // private static final int WINDUP_DURATION = 24;
    private static final int STOMP_TICK = 25;
    private static final int TOTAL_DURATION = 45;

    private static final int MIN_COOLDOWN = 20 * 5;
    private static final int EXTRA_COOLDOWN = 20 * 3;

    private static final double STOMP_RADIUS = 4.5D;
    private static final float STOMP_DAMAGE = 7.0F;
    private static final double STOMP_KNOCKBACK = 1.25D;

    private final OberonEntity entity;

    private int ticksElapsed;
    private int cooldownTicks;
    private boolean stomped;

    public OberonRearingGoal(OberonEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        LivingEntity target = entity.getTarget();

        return target != null
                && target.isAlive()
                && entity.getState() == OberonEntity.State.IDLE
                && entity.distanceToSqr(target)
                <= STOMP_RADIUS * STOMP_RADIUS;
    }

    @Override
    public void start() {
        ticksElapsed = 0;
        stomped = false;

        entity.getNavigation().stop();
        entity.setState(OberonEntity.State.REARING);
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();

        entity.getNavigation().stop();

        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(0.0D, movement.y, 0.0D);

        if (target != null && target.isAlive()) {
            entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
        }

        if (!stomped && ticksElapsed >= STOMP_TICK) {
            performStomp();
            stomped = true;
        }

        ticksElapsed++;
    }

    private void performStomp() {

        AABB area = entity.getBoundingBox().inflate(STOMP_RADIUS, 1.5D, STOMP_RADIUS);

        List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, area,
                target -> target.isAlive()
                        && target != entity);

        for (LivingEntity target : targets) {
            Vec3 difference = target.position().subtract(entity.position());

            Vec3 horizontal = new Vec3(difference.x, 0.0D, difference.z);

            if (horizontal.lengthSqr() > STOMP_RADIUS * STOMP_RADIUS) {
                continue;
            }

            boolean damaged = target.hurt(entity.damageSources().mobAttack(entity), STOMP_DAMAGE);

            if (!damaged) {
                continue;
            }

            if (horizontal.lengthSqr() > 1.0E-4D) {
                horizontal = horizontal.normalize();
            } else {
                horizontal = Vec3.ZERO;
            }

            target.push(horizontal.x * STOMP_KNOCKBACK, 0.45D, horizontal.z * STOMP_KNOCKBACK);

            target.hurtMarked = true;
        }

        if (entity.level() instanceof ServerLevel serverLevel) {
            spawnStompParticles(serverLevel);
        }

        entity.level().playSound(
                null,
                entity.blockPosition(),
                SoundEvents.RAVAGER_ROAR,
                SoundSource.HOSTILE,
                1.0F,
                1.0F
        );
    }

    @Override
    public boolean canContinueToUse() {
        return ticksElapsed < TOTAL_DURATION
                && entity.isAlive();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        entity.setState(OberonEntity.State.IDLE);

        ticksElapsed = 0;
        stomped = false;

        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }

    private void spawnStompParticles(ServerLevel serverLevel) {

        serverLevel.sendParticles(
                ParticleTypes.POOF,
                entity.getX(),
                entity.getY() + 0.15D,
                entity.getZ(),
                30,
                STOMP_RADIUS * 0.35D,
                0.12D,
                STOMP_RADIUS * 0.35D,
                0.08D
        );

        serverLevel.sendParticles(
                ModParticles.SPRING_SPARKLE_PARTICLE.get(),
                entity.getX(),
                entity.getY() + 0.2D,
                entity.getZ(),
                24,
                STOMP_RADIUS * 0.4D,
                0.15D,
                STOMP_RADIUS * 0.4D,
                0.06D
        );

        serverLevel.sendParticles(
                ParticleTypes.CHERRY_LEAVES,
                entity.getX(),
                entity.getY() + 0.25D,
                entity.getZ(),
                20,
                STOMP_RADIUS * 0.45D,
                0.25D,
                STOMP_RADIUS * 0.45D,
                0.05D
        );


        spawnStompRing(serverLevel, STOMP_RADIUS, 28);

        spawnStompRing(serverLevel, STOMP_RADIUS * 0.55D, 16);
    }
    private void spawnStompRing(ServerLevel serverLevel, double radius, int particleCount) {
        double angleStep = (Math.PI * 2.0D) / particleCount;

        for (int i = 0; i < particleCount; i++) {
            double angle = i * angleStep;

            double x = entity.getX() + Math.cos(angle) * radius;
            double y = entity.getY() + 0.12D;
            double z = entity.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(
                    ModParticles.SPRING_SPARKLE_PARTICLE.get(),
                    x,
                    y,
                    z,
                    1,
                    0.03D,
                    0.04D,
                    0.03D,
                    0.02D
            );

            if (i % 2 == 0) {
                serverLevel.sendParticles(
                        ParticleTypes.CHERRY_LEAVES,
                        x,
                        y + 0.15D,
                        z,
                        1,
                        0.05D,
                        0.08D,
                        0.05D,
                        0.03D
                );
            }
        }
    }

}
