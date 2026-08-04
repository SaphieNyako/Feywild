package com.saphienyako.feywild.entity.goals.ashen_lord;

import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.entity.LeafProjectile;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SummerHurlLeavesGoal extends Goal {

    private static final int CHANNEL_DURATION = 60;
    private static final int RELEASE_TICK = 35;

    private static final int MIN_COOLDOWN = 20 * 6;
    private static final int EXTRA_COOLDOWN = 20 * 5;

    private static final int LEAF_COUNT = 5;
    private static final float LEAF_SPEED = 1.3F;
    private static final float LEAF_INACCURACY = 1.0F;

    private static final double MAX_RANGE = 24.0D;

    private static final int CHANNEL_PARTICLE_COUNT = 20;

    private static final double START_ORBIT_RADIUS = 2.5D;
    private static final double END_ORBIT_RADIUS = 1.2D;

    private static final double START_ROTATION_SPEED = 0.12D;
    private static final double END_ROTATION_SPEED = 0.40D;

    private static final int VOLLEY_COUNT = 3;
    private static final int VOLLEY_INTERVAL = 10;

    private int volleysReleased;
    private double baseAngle;

    private final AshenLordEntity entity;

    private int ticksLeft;
    private int cooldownTicks;


    public SummerHurlLeavesGoal(AshenLordEntity entity) {
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
                && entity.getState() != AshenLordEntity.State.CHANNEL
                && entity.distanceToSqr(target) <= MAX_RANGE * MAX_RANGE;
    }

    @Override
    public void start() {
        ticksLeft = CHANNEL_DURATION;
        volleysReleased = 0;

        baseAngle = entity.getRandom().nextDouble() * Math.PI * 2.0D;

        entity.getNavigation().stop();
        entity.startChanneling(AshenLordEntity.ChannelType.SUMMER);
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();

        if (target == null || !target.isAlive()) {
            ticksLeft = 0;
            return;
        }

        entity.getNavigation().stop();

        Vec3 movement = entity.getDeltaMovement();

        entity.setDeltaMovement(0.0D, movement.y, 0.0D);

        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());

        tryReleaseVolley(target);

        if (volleysReleased == 0) {
            spawnChannelParticles();
        }

        ticksLeft--;
    }

    private void tryReleaseVolley(LivingEntity target) {
        if (volleysReleased >= VOLLEY_COUNT) {
            return;
        }

        int ticksSinceFirstRelease =
                RELEASE_TICK - ticksLeft;

        if (ticksSinceFirstRelease < 0) {
            return;
        }

        if (ticksSinceFirstRelease % VOLLEY_INTERVAL != 0) {
            return;
        }

        hurlLeaves(target);
        volleysReleased++;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = entity.getTarget();

        return ticksLeft > 0
                && entity.isAlive()
                && target != null
                && target.isAlive();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void stop() {
        reset();
    }

    private void reset() {
        if (entity.getState() == AshenLordEntity.State.CHANNEL) {
            entity.stopChanneling();
        }

        ticksLeft = 0;
        volleysReleased = 0;

        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }

    private void hurlLeaves(LivingEntity target) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        for (int i = 0; i < LEAF_COUNT; i++) {
            LeafProjectile leaf = new LeafProjectile(ModEntities.LEAF_PROJECTILE.get(), serverLevel);
            leaf.setLeafType(LeafProjectile.LeafType.SUMMER);
            Vec3 lookDirection = entity.getLookAngle().normalize();
            leaf.setOwner(entity);

            leaf.setPos(entity.getX() + lookDirection.x * 1.2D, entity.getEyeY() - 0.25D, entity.getZ() + lookDirection.z * 1.2D);

            double targetX = target.getX() - entity.getX();
            double targetY = target.getEyeY() - leaf.getY();
            double targetZ = target.getZ() - entity.getZ();

            leaf.shoot(targetX, targetY, targetZ, LEAF_SPEED, LEAF_INACCURACY);

            serverLevel.addFreshEntity(leaf);
        }

        entity.playSound(SoundEvents.AZALEA_LEAVES_BREAK, 1.0F, 1.0F);
    }

    private void spawnChannelParticles() {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        int ticksElapsed = CHANNEL_DURATION - ticksLeft;

        double progress = Mth.clamp(ticksElapsed / (double) (CHANNEL_DURATION - RELEASE_TICK), 0.0D, 1.0D);
        double radius = Mth.lerp(progress, START_ORBIT_RADIUS, END_ORBIT_RADIUS);
        double rotationSpeed = Mth.lerp(progress, START_ROTATION_SPEED, END_ROTATION_SPEED);
        double currentRotation = baseAngle + ticksElapsed * rotationSpeed;
        double angleStep = (Math.PI * 2.0D) / CHANNEL_PARTICLE_COUNT;

        for (int i = 0; i < CHANNEL_PARTICLE_COUNT; i++) {
            double angle = currentRotation + i * angleStep;

            double verticalWave = Math.sin(angle * 2.0D + i * 0.45D) * 0.3D;
            double x = entity.getX() + Math.cos(angle) * radius;
            double y = entity.getY() + entity.getBbHeight() * 0.42D + verticalWave;
            double z = entity.getZ() + Math.sin(angle) * radius;

            serverLevel.sendParticles(
                    ModParticles.SUMMER_LEAF_PARTICLE.get(),
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
