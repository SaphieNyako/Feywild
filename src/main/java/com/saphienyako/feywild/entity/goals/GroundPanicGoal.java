package com.saphienyako.feywild.entity.goals;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GroundPanicGoal extends Goal {

    private final LivingEntity entity;
    private Vec3 panicDirection = Vec3.ZERO;
    private int panicTicks;

    public GroundPanicGoal(LivingEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return entity.hurtTime > 0;
    }

    @Override
    public boolean canContinueToUse() {
        return panicTicks > 0;
    }

    @Override
    public void start() {
        panicTicks = 40;

        DamageSource src = entity.getLastDamageSource();
        Entity attacker = src == null ? null : src.getEntity();

        if (attacker != null) {
            panicDirection = entity.position().subtract(attacker.position());
        } else {
            panicDirection = new Vec3(
                    entity.getRandom().nextDouble() - 0.5,
                    0.0,
                    entity.getRandom().nextDouble() - 0.5
            );
        }

        panicDirection = new Vec3(panicDirection.x, 0, panicDirection.z).normalize();
    }

    @Override
    public void tick() {
        panicTicks--;

        double speed = 0.15;
        Vec3 motion = new Vec3(
                panicDirection.x * speed,
                entity.getDeltaMovement().y,
                panicDirection.z * speed
        );

        entity.setDeltaMovement(motion);

        float yaw = (float) (Math.atan2(panicDirection.z, panicDirection.x) * (180.0 / Math.PI)) - 90f;
        entity.setYRot(yaw);
        entity.yRotO = yaw;
        entity.setYHeadRot(yaw);
        entity.yHeadRotO = yaw;
    }

    @Override
    public void stop() {
        entity.setDeltaMovement(Vec3.ZERO);
        panicDirection = Vec3.ZERO;
    }
}