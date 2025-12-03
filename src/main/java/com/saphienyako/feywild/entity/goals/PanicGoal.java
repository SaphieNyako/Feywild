package com.saphienyako.feywild.entity.goals;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class PanicGoal extends Goal {

    private final LivingEntity entity;
    private final int range;
    private final double speed;

    public PanicGoal(LivingEntity entity, double speed, int range) {
        this.entity = entity;
        this.speed = speed;
        this.range = range;
    }

    @Override
    public void start() {
        super.start();
        Vec3 targetPos = null;
        for (int i = 0; i < 30; i++) {
            targetPos = new Vec3(this.entity.getX() - this.range + this.entity.getRandom().nextInt(this.range * 2), this.entity.getY() - this.range + this.entity.getRandom().nextInt(this.range * 2), this.entity.getZ() - this.range + this.entity.getRandom().nextInt(this.range * 2));
            if (this.entity.level.getBlockState(new BlockPos((int) Math.floor(targetPos.x()), (int) Math.floor(targetPos.y()), (int) Math.floor(targetPos.z()))).isAir()) {
                break;
            }
        }
        this.entity.setDeltaMovement((targetPos.x() - this.entity.getX()) * this.speed * 5, (targetPos.y() - this.entity.getY()) * this.speed * 5, (targetPos.z() - this.entity.getZ()) * this.speed * 5);
        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, targetPos);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public boolean canUse() {
        return (this.entity.getLastDamageSource() != null || this.entity.getLastDamageSource() == DamageSource.IN_WALL);
        //TODO WHY WAS TAMABLE ADDED HERE BEFORE? CAUSE THE PLAYER SHOULDNT CAUSE THE FAIRY TO FLEE WHEN HIT.
        //&& (!(entity instanceof ITameable tameable) || !tameable.isTamed())
    }
}
