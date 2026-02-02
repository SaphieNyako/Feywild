package com.saphienyako.feywild.entity.goals;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PanicGoal extends Goal {

    private final LivingEntity entity;
    private Vec3 panicDirection = Vec3.ZERO;
    private int panicTicks;

    public PanicGoal(LivingEntity entity) {
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
            panicDirection = entity.position().subtract(attacker.position()).normalize();
        } else {
            panicDirection = new Vec3(
                    entity.getRandom().nextDouble() - 0.5,
                    entity.getRandom().nextDouble() - 0.5,
                    entity.getRandom().nextDouble() - 0.5
            ).normalize();
        }

        double intensity = 0.8;
        entity.setDeltaMovement(panicDirection.scale(intensity));
    }

    @Override
    public void tick() {
        panicTicks--;

        if (panicDirection == Vec3.ZERO) return;

        double intensity = 0.8;
        entity.setDeltaMovement(panicDirection.scale(intensity));

      /*  float yaw = (float) (Math.atan2(panicDirection.z, panicDirection.x) * (180.0 / Math.PI)) - 90f;
        float pitch = (float) (-Math.asin(panicDirection.y) * (180.0 / Math.PI));

        entity.setYRot(yaw);
        entity.yRotO = yaw;
        entity.setXRot(pitch);
        entity.xRotO = pitch;
        entity.setYHeadRot(yaw);
        entity.yHeadRotO = yaw; */
    }

    @Override
    public void stop() {
        entity.setDeltaMovement(Vec3.ZERO);
        panicDirection = Vec3.ZERO;
    }
}
