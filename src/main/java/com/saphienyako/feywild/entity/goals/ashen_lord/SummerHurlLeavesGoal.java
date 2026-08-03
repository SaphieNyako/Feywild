package com.saphienyako.feywild.entity.goals.ashen_lord;

import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.entity.LeafProjectile;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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

    private final AshenLordEntity entity;

    private int ticksLeft;
    private int cooldownTicks;
    private boolean leavesReleased;

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
        leavesReleased = false;

        entity.getNavigation().stop();
        entity.setState(AshenLordEntity.State.CHANNEL);
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

        if (!leavesReleased && ticksLeft == RELEASE_TICK) {
            hurlLeaves(target);
            leavesReleased = true;
        }

        spawnChannelParticles();

        ticksLeft--;
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
            entity.setState(AshenLordEntity.State.IDLE);
        }

        ticksLeft = 0;
        leavesReleased = false;

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

        serverLevel.sendParticles(
                ModParticles.SUMMER_LEAF_PARTICLE.get(),
                entity.getX(),
                entity.getY() + entity.getBbHeight() * 0.65D,
                entity.getZ(),
                2,
                0.8D,
                0.6D,
                0.8D,
                0.02D);
    }
}
