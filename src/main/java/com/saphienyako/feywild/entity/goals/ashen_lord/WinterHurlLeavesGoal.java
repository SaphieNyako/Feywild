package com.saphienyako.feywild.entity.goals.ashen_lord;

import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.entity.LeafProjectile;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WinterHurlLeavesGoal extends Goal {

    private static final int WINDUP_DURATION = 20 * 2;

    private static final int WAVE_COUNT = 5;
    private static final int WAVE_INTERVAL = 8;

    private static final int RECOVERY_DURATION = 20;

    private static final int TOTAL_DURATION = WINDUP_DURATION + ((WAVE_COUNT - 1) * WAVE_INTERVAL) + RECOVERY_DURATION;

    private static final int MIN_COOLDOWN = 20 * 8;
    private static final int EXTRA_COOLDOWN = 20 * 6;

    private static final int LEAF_COUNT = 20;

    private static final float LEAF_SPEED = 1.2F;

    private static final double MAX_RANGE = 24.0D;

    private static final double START_ORBIT_RADIUS = 2.5D;
    private static final double END_ORBIT_RADIUS = 1.4D;

    private static final double START_ROTATION_SPEED = 0.12D;
    private static final double END_ROTATION_SPEED = 0.35D;

    private static final double WAVE_ROTATION = Math.toRadians(24.0D);

    private final AshenLordEntity entity;

    private int ticksElapsed;
    private int cooldownTicks;
    private int wavesReleased;

    private double baseAngle;

    public WinterHurlLeavesGoal(AshenLordEntity entity) {
        this.entity = entity;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        boolean forced = entity.isForcedAbility(AshenLordEntity.ForcedAbility.WINTER_LEAVES);

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
        if (entity.isForcedAbility(AshenLordEntity.ForcedAbility.WINTER_LEAVES)) {
            entity.clearForcedAbility();
        }
        ticksElapsed = 0;
        wavesReleased = 0;

        baseAngle = entity.getRandom().nextDouble() * Math.PI * 2.0D;

        entity.getNavigation().stop();
        entity.startChanneling(AshenLordEntity.ChannelType.WINTER);
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

        entity.setDeltaMovement(0.0D, movement.y, 0.0D);

        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());

        if (ticksElapsed < WINDUP_DURATION) {
            spawnOrbitingLeaves();
        } else {
            tryReleaseWave();
        }

        ticksElapsed++;
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
        wavesReleased = 0;

        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }

    private void tryReleaseWave() {
        if (wavesReleased >= WAVE_COUNT) {
            return;
        }

        int releaseTicks = ticksElapsed - WINDUP_DURATION;

        if (releaseTicks % WAVE_INTERVAL != 0) {
            return;
        }

        releaseLeafWave();

        wavesReleased++;

        baseAngle += WAVE_ROTATION;
    }

    private void releaseLeafWave() {
        if (!(entity.level instanceof ServerLevel serverLevel)) {
            return;
        }

        double angleStep = (Math.PI * 2.0D) / LEAF_COUNT;

        for (int i = 0; i < LEAF_COUNT; i++) {
            double angle = baseAngle + i * angleStep;

            Vec3 direction = createOutwardDirection(angle);

            Vec3 origin = new Vec3(
                    entity.getX() + direction.x * 1.5D,
                    entity.getY() + entity.getBbHeight() * 0.35D,
                    entity.getZ() + direction.z * 1.5D);

            LeafProjectile leaf = new LeafProjectile(ModEntities.LEAF_PROJECTILE.get(), serverLevel);
            leaf.setOwner(entity);
            leaf.setLeafType(LeafProjectile.LeafType.WINTER);
            leaf.setPos(origin.x, origin.y, origin.z);

            leaf.shoot(direction.x, direction.y, direction.z, LEAF_SPEED, 0.0F);

            serverLevel.addFreshEntity(leaf);
        }

        entity.playSound(SoundEvents.AZALEA_LEAVES_BREAK, 1.0F, 1.0F);
    }

    private Vec3 createOutwardDirection(double angle) {

        double verticalDirection = -0.03D + entity.getRandom().nextDouble() * 0.04D;

        return new Vec3(Math.cos(angle), verticalDirection, Math.sin(angle)).normalize();
    }

    private void spawnOrbitingLeaves() {
        if (!(entity.level instanceof ServerLevel serverLevel)) {
            return;
        }

        double progress = Mth.clamp(ticksElapsed / (double) WINDUP_DURATION, 0.0D, 1.0D);

        double radius = Mth.lerp(progress, START_ORBIT_RADIUS, END_ORBIT_RADIUS);

        double rotationSpeed = Mth.lerp(progress, START_ROTATION_SPEED, END_ROTATION_SPEED);

        double currentRotation = baseAngle + ticksElapsed * rotationSpeed;

        double angleStep = (Math.PI * 2.0D) / LEAF_COUNT;

        for (int i = 0; i < LEAF_COUNT; i++) {
            double angle = currentRotation + i * angleStep;

            double verticalWave = Math.sin(angle * 2.0D + i * 0.8D) * 0.3D;

            double x = entity.getX() + Math.cos(angle) * radius;
            double y = entity.getY() + entity.getBbHeight() * 0.35D + verticalWave;
            double z = entity.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(ModParticles.WINTER_LEAF_PARTICLE.get(), x, y, z, 1, 0.02D, 0.02D, 0.02D, 0.0D);
        }
    }
}
