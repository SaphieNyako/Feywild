package com.saphienyako.feywild.entity.goals.oberon;

import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class OberonChargingGoal extends Goal {

    private static final int WINDUP_DURATION = 35;
    private static final int CHARGE_DURATION = 35;
    private static final int RECOVERY_DURATION = 15;

    private static final int TOTAL_DURATION = WINDUP_DURATION + CHARGE_DURATION + RECOVERY_DURATION;

    private static final int MIN_COOLDOWN = 20 * 6;
    private static final int EXTRA_COOLDOWN = 20 * 4;

    private static final double MIN_RANGE = 5.0D;
    private static final double MAX_RANGE = 22.0D;

    private static final double CHARGE_SPEED = 1.15D;
    private static final float CHARGE_DAMAGE = 10.0F;
    private static final double KNOCKBACK = 1.8D;

    private Vec3 startingPosition = Vec3.ZERO;
    private float startingYRot;
    private float startingXRot;
    private final OberonEntity entity;

    private int ticksElapsed;
    private int cooldownTicks;

    private Vec3 chargeDirection = Vec3.ZERO;
    private final Set<UUID> hitEntities = new HashSet<>();

    public OberonChargingGoal(OberonEntity entity) {
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

        if (target == null || !target.isAlive()) {
            return false;
        }

        double distanceSqr = entity.distanceToSqr(target);

        return entity.getState() == OberonEntity.State.IDLE && distanceSqr >= MIN_RANGE * MIN_RANGE && distanceSqr <= MAX_RANGE * MAX_RANGE;
    }

    @Override
    public void start() {
        ticksElapsed = 0;
        chargeDirection = Vec3.ZERO;
        hitEntities.clear();

        startingPosition = entity.position();
        startingYRot = entity.getYRot();
        startingXRot = entity.getXRot();

        entity.getNavigation().stop();
        entity.setState(OberonEntity.State.REARING);
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();

        if (target == null || !target.isAlive()) {
            ticksElapsed = TOTAL_DURATION;
            return;
        }

        entity.getNavigation().stop();

        if (ticksElapsed < WINDUP_DURATION) {
            entity.setState(OberonEntity.State.REARING);

            entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());

            Vec3 movement = entity.getDeltaMovement();
            entity.setDeltaMovement(0.0D, movement.y, 0.0D);

            if (ticksElapsed == WINDUP_DURATION - 1) {
                chargeDirection = target.position().subtract(entity.position());

                chargeDirection = new Vec3(chargeDirection.x, 0.0D, chargeDirection.z).normalize();

                entity.setYRot((float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * Mth.RAD_TO_DEG - 90.0D));
            }

        } else if (ticksElapsed < WINDUP_DURATION + CHARGE_DURATION) {
            entity.setState(OberonEntity.State.CHARGING);

            entity.setDeltaMovement(chargeDirection.x * CHARGE_SPEED, entity.getDeltaMovement().y, chargeDirection.z * CHARGE_SPEED);

            spawnChargeTrailParticles();
            damageEntitiesDuringCharge();

            if (entity.horizontalCollision) {
                ticksElapsed = WINDUP_DURATION + CHARGE_DURATION;
                entity.level().playSound(
                        null,
                        entity.blockPosition(),
                        SoundEvents.RAVAGER_STUNNED,
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                );
            }

        } else {
            entity.setState(OberonEntity.State.IDLE);
            Vec3 movement = entity.getDeltaMovement();
            entity.setDeltaMovement(movement.x * 0.4D, movement.y, movement.z * 0.4D);
        }

        ticksElapsed++;
    }

    private void damageEntitiesDuringCharge() {
        AABB area = entity.getBoundingBox().inflate(0.7D);

        List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, area, target -> target.isAlive() && target != entity && !hitEntities.contains(target.getUUID()));

        for (LivingEntity target : targets) {
            boolean damaged = target.hurt(entity.damageSources().mobAttack(entity), CHARGE_DAMAGE);

            if (!damaged) {
                continue;
            }

            hitEntities.add(target.getUUID());

            target.push(chargeDirection.x * KNOCKBACK, 0.35D, chargeDirection.z * KNOCKBACK);

            target.hurtMarked = true;

            entity.level().playSound(
                    null,
                    target.blockPosition(),
                    SoundEvents.RAVAGER_ATTACK,
                    SoundSource.HOSTILE,
                    1.0F,
                    1.0F);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return ticksElapsed < TOTAL_DURATION && entity.isAlive();
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

        entity.setDeltaMovement(0.0D, entity.getDeltaMovement().y, 0.0D);

        returnToStartingPosition();

        ticksElapsed = 0;
        chargeDirection = Vec3.ZERO;
        hitEntities.clear();

        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }

    private void spawnChargeTrailParticles() {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 backward = chargeDirection.scale(-1.0D);

        double behindDistance = 1.0D + entity.getRandom().nextDouble() * 2.0D;

        double sideways = (entity.getRandom().nextDouble() - 0.5D) * entity.getBbWidth();

        Vec3 sideDirection = new Vec3(-chargeDirection.z, 0.0D, chargeDirection.x);

        double x = entity.getX() + backward.x * behindDistance + sideDirection.x * sideways;

        double y = entity.getY() + 0.45D + entity.getRandom().nextDouble() * 0.5D;

        double z = entity.getZ() + backward.z * behindDistance + sideDirection.z * sideways;

        serverLevel.sendParticles(
                ParticleTypes.CHERRY_LEAVES,
                x,
                y,
                z,
                2,
                0.15D,
                0.10D,
                0.15D,
                0.02D
        );

        serverLevel.sendParticles(
                ModParticles.SPRING_SPARKLE_PARTICLE.get(),
                x,
                y + 0.2D,
                z,
                1,
                0.10D,
                0.10D,
                0.10D,
                0.01D
        );
    }

    private void returnToStartingPosition() {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        spawnTeleportPoof(serverLevel, entity.getX(), entity.getY(), entity.getZ());

        serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);

        entity.teleportTo(startingPosition.x, startingPosition.y, startingPosition.z);

        entity.setYRot(startingYRot);
        entity.setXRot(startingXRot);
        entity.setYHeadRot(startingYRot);
        entity.setYBodyRot(startingYRot);

        entity.setDeltaMovement(Vec3.ZERO);
        entity.hasImpulse = true;

        spawnTeleportPoof(serverLevel, startingPosition.x, startingPosition.y, startingPosition.z);

        serverLevel.playSound(null, startingPosition.x, startingPosition.y, startingPosition.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    private void spawnTeleportPoof(ServerLevel serverLevel, double x, double y, double z) {
        BlockPos position = BlockPos.containing(x, y, z);
        PacketDistributor.sendToPlayersTrackingEntity(entity, new ParticleMessage(ParticleMessage.Particles.DANDELION_FLUFF, position));

        serverLevel.sendParticles(
                ModParticles.SPRING_SPARKLE_PARTICLE.get(),
                x,
                y + entity.getBbHeight() * 0.5D,
                z,
                20,
                entity.getBbWidth() * 0.5D,
                entity.getBbHeight() * 0.4D,
                entity.getBbWidth() * 0.5D,
                0.05D
        );

        serverLevel.sendParticles(
                ParticleTypes.CHERRY_LEAVES,
                x,
                y + entity.getBbHeight() * 0.6D,
                z,
                12,
                entity.getBbWidth() * 0.45D,
                entity.getBbHeight() * 0.3D,
                entity.getBbWidth() * 0.45D,
                0.03D
        );
    }
}
