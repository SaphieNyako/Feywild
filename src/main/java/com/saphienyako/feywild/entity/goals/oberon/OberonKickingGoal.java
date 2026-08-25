package com.saphienyako.feywild.entity.goals.oberon;

import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class OberonKickingGoal extends Goal {

  //  private static final int WINDUP_DURATION = 12;
  private static final int RETALIATION_COOLDOWN = 5;
    private static final int ATTACK_TICK = 9;
    private static final int TOTAL_DURATION = 28;

    private static final int MIN_COOLDOWN = 20 * 3;
    private static final int EXTRA_COOLDOWN = 20 * 2;

    private static final double KICK_RANGE = 4.5D;
    private static final float KICK_DAMAGE = 8.0F;
    private static final double KICK_KNOCKBACK = 2.2D;

    private final OberonEntity entity;

    private int ticksElapsed;
    private int cooldownTicks;
    private boolean kicked;

    public OberonKickingGoal(OberonEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        Player attacker = entity.getRecentAttacker();

        boolean rearAttack = attacker != null && entity.distanceToSqr(attacker) <= KICK_RANGE * KICK_RANGE && isBehindOberon(attacker);

        if (cooldownTicks > 0) {
            if (rearAttack) {
                cooldownTicks = Math.min(cooldownTicks, RETALIATION_COOLDOWN);
            }

            cooldownTicks--;
            return false;
        }

        LivingEntity target = rearAttack ? attacker : entity.getTarget();

        return target != null
                && target.isAlive()
                && entity.getState() == OberonEntity.State.IDLE
                && entity.distanceToSqr(target) <= KICK_RANGE * KICK_RANGE
                && isBehindOberon(target);
    }

    private boolean isBehindOberon(LivingEntity target) {
        Vec3 look = entity.getLookAngle();

        Vec3 directionToTarget = target.position().subtract(entity.position());

        directionToTarget = new Vec3(directionToTarget.x, 0.0D, directionToTarget.z);

        if (directionToTarget.lengthSqr() < 1.0E-4D) {
            return false;
        }

        directionToTarget = directionToTarget.normalize();

        Vec3 horizontalLook = new Vec3(look.x, 0.0D, look.z).normalize();

        return horizontalLook.dot(directionToTarget) < -0.35D;
    }

    @Override
    public void start() {
        ticksElapsed = 0;
        kicked = false;

        entity.getNavigation().stop();
        entity.setState(OberonEntity.State.KICKING);
    }

    @Override
    public void tick() {
        entity.getNavigation().stop();

        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(0.0D, movement.y, 0.0D);

        if (!kicked && ticksElapsed >= ATTACK_TICK) {
            performKick();
            kicked = true;
        }

        ticksElapsed++;
    }

    private void performKick() {
        Vec3 backward = entity.getLookAngle().multiply(-1.0D, 0.0D, -1.0D).normalize();

        Vec3 kickCenter = entity.position().add(backward.scale(1.8D)).add(0.0D, entity.getBbHeight() * 0.35D, 0.0D);

        AABB area = new AABB(
                kickCenter.x - 1.8D,
                kickCenter.y - 1.2D,
                kickCenter.z - 1.8D,
                kickCenter.x + 1.8D,
                kickCenter.y + 1.2D,
                kickCenter.z + 1.8D
        );

        List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, area,
                target -> target.isAlive()
                        && target != entity
                        && isBehindOberon(target));

        for (LivingEntity target : targets) {
            boolean damaged = target.hurt(entity.damageSources().mobAttack(entity), KICK_DAMAGE);

            if (damaged) {
                target.push(backward.x * KICK_KNOCKBACK, 0.6D, backward.z * KICK_KNOCKBACK);
                this.entity.playSound(ModSounds.OBERON_KICKING.get(), 1, 1);
                target.hurtMarked = true;
            }
        }

        entity.level().playSound(
                null,
                entity.blockPosition(),
                SoundEvents.HORSE_STEP_WOOD,
                SoundSource.HOSTILE,
                1.0F,
                1.0F
        );
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
    public void stop() {
        entity.setState(OberonEntity.State.IDLE);

        ticksElapsed = 0;
        kicked = false;

        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }
}
