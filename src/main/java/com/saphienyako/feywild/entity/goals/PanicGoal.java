package com.saphienyako.feywild.entity.goals;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class PanicGoal extends Goal {

    private final LivingEntity entity;

    public PanicGoal(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public void start() {
        DamageSource src = this.entity.getLastDamageSource();
        Entity attacker = src == null ? null : src.getEntity();

        Vec3 dir;
        if (attacker != null) {
            dir = this.entity.position().subtract(attacker.position()).normalize();
        } else {
            dir = new Vec3(
                    this.entity.getRandom().nextDouble() - 0.5,
                    this.entity.getRandom().nextDouble() - 0.5,
                    this.entity.getRandom().nextDouble() - 0.5
            ).normalize();
        }

        double intensity = 0.8;
        this.entity.setDeltaMovement(dir.scale(intensity));
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
