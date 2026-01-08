package com.saphienyako.feywild.entity.goals;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

public class PanicGoal extends Goal {

    private final LivingEntity entity;

    public PanicGoal(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public void start() {
        DamageSource source = this.entity.getLastDamageSource();
        Entity attacker = source == null ? null : source.getEntity();

        Vector3d direction;
        if (attacker != null) {
            direction = new Vector3d(
                    this.entity.getX() - attacker.getX(),
                    this.entity.getY() - attacker.getY(),
                    this.entity.getZ() - attacker.getZ()
            ).normalize();
        } else {
            Random rand = this.entity.getRandom();
            direction = new Vector3d(
                    rand.nextDouble() - 0.5,
                    rand.nextDouble() - 0.5,
                    rand.nextDouble() - 0.5
            ).normalize();
        }

        double intensity = 0.8;
        this.entity.setDeltaMovement(direction.scale(intensity));
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public boolean canUse() {
        return this.entity.hurtTime > 0;
    }
}
