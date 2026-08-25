package com.saphienyako.feywild.entity.goals.ashen_lord;

import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.entity.LeafProjectile;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class SpringLeafWhirlwindGoal extends Goal {

    private static final int CHANNEL_DURATION = 20 * 3;
    private static final int WHIRLWIND_DURATION = 20 * 4;

    private static final int WARNING_DURATION = 20;
    private static final int RECOVERY_DURATION = 20;

    private static final int TOTAL_DURATION = CHANNEL_DURATION +WARNING_DURATION +  WHIRLWIND_DURATION + RECOVERY_DURATION;

    private static final int MIN_COOLDOWN = 20 * 8;
    private static final int EXTRA_COOLDOWN = 20 * 6;

    private static final double MAX_RANGE = 24.0D;

    private static final double WHIRLWIND_RADIUS = 3.5D;
    private static final double WHIRLWIND_HEIGHT = 8.0D;

    private static final double UPWARD_FORCE = 0.35D; //Cap is 0.45
    private static final double INWARD_FORCE = 0.020D;

    private static final int SPRING_RING_PARTICLES = 12;

    private static final double SPRING_START_RADIUS = 2.6D;
    private static final double SPRING_END_RADIUS = 1.3D;

    private static final double SPRING_START_SPEED = 0.10D;
    private static final double SPRING_END_SPEED = 0.32D;

    private double baseAngle;

    private final AshenLordEntity entity;

    private int ticksElapsed;
    private int cooldownTicks;

    private Vec3 whirlwindCenter;
    private boolean whirlwindStarted;

    public SpringLeafWhirlwindGoal(AshenLordEntity entity) {
        this.entity = entity;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        boolean forced = entity.isForcedAbility(AshenLordEntity.ForcedAbility.SPRING_WHIRLWIND);

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
                && entity.getState() != AshenLordEntity.State.CHANNEL
                && entity.distanceToSqr(target) <= MAX_RANGE * MAX_RANGE;
    }

    @Override
    public void start() {
        if (entity.isForcedAbility(AshenLordEntity.ForcedAbility.SPRING_WHIRLWIND)) {
            entity.clearForcedAbility();
        }
        ticksElapsed = 0;
        whirlwindCenter = null;
        whirlwindStarted = false;

        baseAngle = entity.getRandom().nextDouble() * Math.PI * 2.0D;

        entity.getNavigation().stop();
        entity.startChanneling(AshenLordEntity.ChannelType.SPRING);
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();

        if (target == null || !target.isAlive()) {
            ticksElapsed = TOTAL_DURATION;
            return;
        }

        entity.getNavigation().stop();

        Vec3 movement = entity.getDeltaMovement();

        entity.setDeltaMovement(
                0.0D,
                movement.y,
                0.0D
        );

        entity.lookAt(
                EntityAnchorArgument.Anchor.EYES,
                target.getEyePosition()
        );

        if (ticksElapsed < CHANNEL_DURATION) {
            spawnChannelParticles();
            spawnTargetWarningParticles(target);

        } else if (ticksElapsed
                < CHANNEL_DURATION + WARNING_DURATION) {

            if (whirlwindCenter == null) {
                lockWhirlwindPosition(target);
            }

            spawnLockedWarningParticles();

        } else if (ticksElapsed
                < CHANNEL_DURATION
                + WARNING_DURATION
                + WHIRLWIND_DURATION) {

            if (!whirlwindStarted) {
                startWhirlwind();
            }

            tickWhirlwind();
        }

        ticksElapsed++;
    }

    private void lockWhirlwindPosition(LivingEntity target) {
        whirlwindCenter = new Vec3(
                target.getX(),
                target.getY(),
                target.getZ()
        );
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = entity.getTarget();

        return ticksElapsed < TOTAL_DURATION
                && entity.isAlive()
                && target != null
                && target.isAlive();
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
        reset();
    }

    private void reset() {
        if (entity.getState() == AshenLordEntity.State.CHANNEL) {
            entity.stopChanneling();
        }

        ticksElapsed = 0;
        whirlwindCenter = null;
        whirlwindStarted = false;

        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }

    private void startWhirlwind() {
        if (whirlwindCenter == null) {
            return;
        }

        whirlwindStarted = true;

        entity.level().playSound(
                null,
                whirlwindCenter.x,
                whirlwindCenter.y,
                whirlwindCenter.z,
                SoundEvents.PHANTOM_FLAP,
                SoundSource.HOSTILE,
                1.0F,
                1.0F
        );
    }

    private void tickWhirlwind() {
        if (whirlwindCenter == null) {
            return;
        }

        spawnWhirlwindParticles();
        affectEntitiesInsideWhirlwind();
    }

    private void affectEntitiesInsideWhirlwind() {
        AABB area = new AABB(
                whirlwindCenter.x - WHIRLWIND_RADIUS,
                whirlwindCenter.y,
                whirlwindCenter.z - WHIRLWIND_RADIUS,

                whirlwindCenter.x + WHIRLWIND_RADIUS,
                whirlwindCenter.y + WHIRLWIND_HEIGHT,
                whirlwindCenter.z + WHIRLWIND_RADIUS
        );

        List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, area, target -> target.isAlive() && target != entity);

        for (LivingEntity target : targets) {
            Vec3 horizontalDifference = new Vec3(whirlwindCenter.x - target.getX(), 0.0D, whirlwindCenter.z - target.getZ());

            double horizontalDistanceSqr = horizontalDifference.lengthSqr();

            if (horizontalDistanceSqr > WHIRLWIND_RADIUS * WHIRLWIND_RADIUS) {
                continue;
            }

            Vec3 inwardDirection;

            if (horizontalDistanceSqr > 1.0E-4D) {
                inwardDirection = horizontalDifference.normalize();
            } else {
                inwardDirection = Vec3.ZERO;
            }

            Vec3 currentMovement = target.getDeltaMovement();

            target.setDeltaMovement(currentMovement.x + inwardDirection.x * INWARD_FORCE,
                    Math.min(currentMovement.y + UPWARD_FORCE, 0.45D), currentMovement.z + inwardDirection.z * INWARD_FORCE);

            target.fallDistance = 0.0F;
            target.hurtMarked = true;
        }
    }

    private void spawnChannelParticles() {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        double progress = Mth.clamp(ticksElapsed / (double) CHANNEL_DURATION, 0.0D, 1.0D);

        double radius = Mth.lerp(progress, SPRING_START_RADIUS, SPRING_END_RADIUS);

        double rotationSpeed = Mth.lerp(progress, SPRING_START_SPEED, SPRING_END_SPEED);


         //Lower clockwise ring.

        spawnSpringChannelRing(
                serverLevel,
                SPRING_RING_PARTICLES,
                radius,
                entity.getBbHeight() * 0.28D,
                baseAngle + ticksElapsed * rotationSpeed,
                0.22D
        );

        spawnSpringChannelRing(
                serverLevel,
                SPRING_RING_PARTICLES,
                radius * 0.78D,
                entity.getBbHeight() * 0.58D,
                baseAngle - ticksElapsed * rotationSpeed * 0.85D,
                0.28D
        );

        spawnRisingSpringLeaves(
                serverLevel,
                progress
        );
    }

    private void spawnTargetWarningParticles(
            LivingEntity target
    ) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        double progress = Mth.clamp(ticksElapsed / (double) CHANNEL_DURATION, 0.0D, 1.0D);

        double radius = Mth.lerp(progress, WHIRLWIND_RADIUS, 1.0D);

        int particleCount = 12;

        double rotation = ticksElapsed * 0.25D;

        for (int i = 0; i < particleCount; i++) {
            double angle = rotation + i * (Math.PI * 2.0D / particleCount);

            double x = target.getX() + Math.cos(angle) * radius;

            double z = target.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(ModParticles.SPRING_LEAF_PARTICLE.get(), x, target.getY() + 0.15D, z, 1, 0.02D, 0.02D, 0.02D, 0.01D);
        }
    }

    private void spawnWhirlwindParticles() {
        if (!(entity.level() instanceof ServerLevel serverLevel)
                || whirlwindCenter == null) {
            return;
        }

        int spiralCount = 4;
        int particlesPerSpiral = 10;

        double time = (ticksElapsed - CHANNEL_DURATION) * 0.28D;

        for (int spiral = 0; spiral < spiralCount; spiral++) {
            double spiralOffset = spiral * (Math.PI * 2.0D / spiralCount);

            for (int i = 0; i < particlesPerSpiral; i++) {
                double heightProgress = i / (double) particlesPerSpiral;
                double height = heightProgress * WHIRLWIND_HEIGHT;
                double radius = Mth.lerp(heightProgress, WHIRLWIND_RADIUS, 0.6D);
                double angle = time + spiralOffset + heightProgress * Math.PI * 4.0D;
                double x = whirlwindCenter.x + Math.cos(angle) * radius;
                double y = whirlwindCenter.y + height;
                double z = whirlwindCenter.z + Math.sin(angle) * radius;

                serverLevel.sendParticles(
                        ModParticles.SPRING_LEAF_PARTICLE.get(),
                        x,
                        y,
                        z,
                        1,
                        0.03D,
                        0.07D,
                        0.03D,
                        0.02D
                );
            }
        }

        for (int i = 0; i < 12; i++) {
            double angle = time + i * (Math.PI * 2.0D / 12.0D);

            double x = whirlwindCenter.x + Math.cos(angle) * WHIRLWIND_RADIUS;
            double z = whirlwindCenter.z + Math.sin(angle) * WHIRLWIND_RADIUS;

            serverLevel.sendParticles(
                    ModParticles.SPRING_LEAF_PARTICLE.get(),
                    x,
                    whirlwindCenter.y + 0.15D,
                    z,
                    1,
                    0.02D,
                    0.04D,
                    0.02D,
                    0.01D
            );
        }
    }

    private void spawnSpringChannelRing(ServerLevel serverLevel, int particleCount, double radius, double height, double rotation, double verticalWaveSize) {
        double angleStep = (Math.PI * 2.0D) / particleCount;

        for (int i = 0; i < particleCount; i++) {
            double angle = rotation + i * angleStep;
            double verticalWave = Math.sin(angle * 2.0D + ticksElapsed * 0.08D + i * 0.4D) * verticalWaveSize;
            double x = entity.getX() + Math.cos(angle) * radius;
            double y = entity.getY() + height + verticalWave;
            double z = entity.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(
                    ModParticles.SPRING_LEAF_PARTICLE.get(),
                    x,
                    y,
                    z,
                    1,
                    0.02D,
                    0.05D,
                    0.02D,
                    0.01D
            );
        }
    }

    private void spawnRisingSpringLeaves(ServerLevel serverLevel, double progress) {
        int particleCount = 2 + Mth.floor(progress * 4.0D);

        for (int i = 0; i < particleCount; i++) {
            double angle = baseAngle + ticksElapsed * 0.18D + i * (Math.PI * 2.0D / particleCount);
            double radius = 0.5D + entity.getRandom().nextDouble() * 0.7D;
            double x = entity.getX() + Math.cos(angle) * radius;
            double y = entity.getY() + entity.getRandom().nextDouble() * entity.getBbHeight();
            double z = entity.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(
                    ModParticles.SPRING_LEAF_PARTICLE.get(),
                    x,
                    y,
                    z,
                    1,
                    0.03D,
                    0.12D,
                    0.03D,
                    0.08D
            );
        }
    }

    private void spawnLockedWarningParticles() {
        if (!(entity.level() instanceof ServerLevel serverLevel)
                || whirlwindCenter == null) {
            return;
        }

        double warningProgress = (ticksElapsed - CHANNEL_DURATION) / (double) WARNING_DURATION;

        double rotation = ticksElapsed * 0.35D;

        int particleCount = 20;

        for (int i = 0; i < particleCount; i++) {
            double angle = rotation + i * (Math.PI * 2.0D / particleCount);
            double x = whirlwindCenter.x + Math.cos(angle) * WHIRLWIND_RADIUS;
            double z = whirlwindCenter.z + Math.sin(angle) * WHIRLWIND_RADIUS;

            serverLevel.sendParticles(
                    ModParticles.SPRING_LEAF_PARTICLE.get(),
                    x,
                    whirlwindCenter.y + 0.15D,
                    z,
                    1,
                    0.02D,
                    0.03D,
                    0.02D,
                    0.01D
            );
        }

        int risingParticles =
               2 + Mth.floor(warningProgress * 8.0D);

        for (int i = 0; i < risingParticles; i++) {
            double angle = entity.getRandom().nextDouble() * Math.PI * 2.0D;
            double radius = Math.sqrt(entity.getRandom().nextDouble()) * WHIRLWIND_RADIUS;
            double x = whirlwindCenter.x + Math.cos(angle) * radius;
            double y = whirlwindCenter.y + entity.getRandom().nextDouble() * 1.5D;
            double z = whirlwindCenter.z + Math.sin(angle) * radius;

            serverLevel.sendParticles(
                    ModParticles.SPRING_LEAF_PARTICLE.get(),
                    x,
                    y,
                    z,
                    1,
                    0.02D,
                    0.08D,
                    0.02D,
                    0.04D
            );
        }
    }
}