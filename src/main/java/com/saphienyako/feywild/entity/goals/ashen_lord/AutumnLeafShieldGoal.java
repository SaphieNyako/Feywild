package com.saphienyako.feywild.entity.goals.ashen_lord;

import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class AutumnLeafShieldGoal extends Goal {

    private static final int SHIELD_DURATION = 20 * 15;
    private static final int MIN_COOLDOWN = 20 * 7;
    private static final int EXTRA_COOLDOWN = 20 * 5;

    private static final double SHIELD_RADIUS = 4.0D;

    private static final float SHIELD_DAMAGE = 7.0F;

    private final AshenLordEntity entity;

    private int ticksLeft;
    private int cooldownTicks;

    public AutumnLeafShieldGoal(AshenLordEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(
                Flag.MOVE,
                Flag.LOOK
        ));
    }

    @Override
    public boolean canUse() {
        boolean forced = entity.isForcedAbility(AshenLordEntity.ForcedAbility.AUTUMN_SHIELD);

        if (entity.getForcedAbility() != null && !forced) {
            return false;
        }

        if (!forced && cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        LivingEntity target = entity.getTarget();

        return target != null
                && target.isAlive()
                && entity.getState() != AshenLordEntity.State.CHANNEL;
    }

    @Override
    public void start() {
        if (entity.isForcedAbility(AshenLordEntity.ForcedAbility.AUTUMN_SHIELD)) {
            entity.clearForcedAbility();
        }
        this.ticksLeft = SHIELD_DURATION;

        entity.getNavigation().stop();
        entity.startChanneling(AshenLordEntity.ChannelType.AUTUMN);
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();

        if (target == null || !target.isAlive()) {
            reset();
            return;
        }

        ticksLeft--;

        entity.getNavigation().stop();

        Vec3 movement = entity.getDeltaMovement();

        entity.setDeltaMovement(
                0.0D,
                movement.y,
                0.0D
        );

        entity.lookAt(
                EntityAnchorArgument.Anchor.EYES,
                target.position()
        );

        pushPlayersAway();
        reflectProjectiles();
        spawnParticles();
    }

    @Override
    public boolean canContinueToUse() {
        return ticksLeft > 0
                && entity.isAlive()
                && entity.getTarget() != null
                && entity.getTarget().isAlive();
    }

    @Override
    public void stop() {
        reset();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    private void reset() {
        if (entity.getState() == AshenLordEntity.State.CHANNEL) {
            entity.stopChanneling();
        }
        ticksLeft = 0;
        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private void pushPlayersAway() {
        AABB area = entity.getBoundingBox().inflate(SHIELD_RADIUS);

        List<Player> players = entity.level().getEntitiesOfClass(
                Player.class,
                area,
                player -> player.isAlive() && !player.isSpectator()
        );

        for (Player player : players) {
            Vec3 direction = player.position()
                    .subtract(entity.position());

            direction = new Vec3(
                    direction.x,
                    0.0D,
                    direction.z
            );

            if (direction.lengthSqr() < 1.0E-4D) {
                direction = new Vec3(
                        entity.getRandom().nextDouble() - 0.5D,
                        0.0D,
                        entity.getRandom().nextDouble() - 0.5D
                );
            }

            direction = direction.normalize();

            player.hurt(entity.damageSources().mobAttack(entity), SHIELD_DAMAGE);
            entity.level().playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.AZALEA_LEAVES_BREAK,
                    SoundSource.HOSTILE,
                    1.0F, 1.0F);
            player.push(direction.x * 0.8D, 0.15D, direction.z * 0.8D);

            player.hurtMarked = true;
        }
    }

    private void reflectProjectiles() {
        AABB area = entity.getBoundingBox().inflate(SHIELD_RADIUS);

        List<Projectile> projectiles = entity.level().getEntitiesOfClass(
                Projectile.class,
                area,
                projectile -> projectile.isAlive()
                        && projectile.getOwner() != entity
        );

        for (Projectile projectile : projectiles) {
            Entity originalOwner = projectile.getOwner();

            Vec3 direction;

            if (originalOwner != null && originalOwner.isAlive()) {
                direction = originalOwner.getBoundingBox()
                        .getCenter()
                        .subtract(projectile.position())
                        .normalize();
            } else {
                direction = projectile.getDeltaMovement()
                        .scale(-1.0D)
                        .normalize();
            }

            double speed = Math.max(
                    0.5D,
                    projectile.getDeltaMovement().length()
            );

            projectile.setOwner(entity);
            projectile.setDeltaMovement(direction.scale(speed));
            projectile.hasImpulse = true;

            if (projectile instanceof AbstractArrow arrow) {
                arrow.setBaseDamage(arrow.getBaseDamage() * SHIELD_DAMAGE);
            }

            projectile.playSound(SoundEvents.AZALEA_LEAVES_BREAK, 1.0F, 1.0F);
        }
    }

    private void spawnParticles() {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        spawnParticleRing(
                serverLevel,
                14,                          // Particle count
                SHIELD_RADIUS * 0.9D,               // Radius 90%
                entity.getBbHeight() * 0.35D,
                0.22D,                       // Clockwise speed
                0.0D
        );

        spawnParticleRing(
                serverLevel,
                10,
                SHIELD_RADIUS * 0.78D, // Radius 90%
                entity.getBbHeight() * 0.62D,
                -0.17D,                      // Counterclockwise
                Math.PI / 12.0D
        );

        spawnParticleRing(
                serverLevel,
                6,
                SHIELD_RADIUS * 0.48D,
                entity.getBbHeight() * 0.92D,
                0.08D,                       // Slow clockwise spin
                Math.PI / 8.0D
        );
    }

    private void spawnParticleRing(ServerLevel serverLevel, int particleCount, double radius, double height, double rotationSpeed, double angleOffset) {
        double rotation = entity.tickCount * rotationSpeed + angleOffset;

        double angleStep = (Math.PI * 2.0D) / particleCount;

        for (int i = 0; i < particleCount; i++) {
            double angle = rotation + i * angleStep;

            double verticalWave = Math.sin(angle * 2.0D + entity.tickCount * 0.08D) * 0.18D;

            double x = entity.getX() + Math.cos(angle) * radius;

            double y = entity.getY() + height + verticalWave;

            double z = entity.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(
                    ModParticles.AUTUMN_LEAF_PARTICLE.get(),
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