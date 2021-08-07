package com.feywild.feywild.entity.goals;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Supplier;

public class GoToTargetPositionGoal extends MovementRestrictionGoal {

    private final MobEntity entity;
    private final Supplier<Boolean> shouldReturn;
    private final int triggerRangeSquared;
    private final float speed;

    public GoToTargetPositionGoal(MobEntity entity, Supplier<Vector3d> pos, int maxMovementRange, float speed) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.speed = speed;
        this.shouldReturn = () -> true;
        this.triggerRangeSquared = (maxMovementRange * 2) * (maxMovementRange * 2);
    }

    public GoToTargetPositionGoal(MobEntity entity, Supplier<Vector3d> pos, int maxMovementRange, float speed, Supplier<Boolean> shouldReturn) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.speed = speed;
        this.shouldReturn = shouldReturn;
        this.triggerRangeSquared = (maxMovementRange * 2) * (maxMovementRange * 2);
    }

    @Override
    public void tick() {
        Vector3d target = this.targetPosition.get();
        if (target != null && distanceFromSquared(this.entity.position(), target) > this.triggerRangeSquared) {
            this.entity.setPos(target.x, target.y, target.z);
        } else if (target != null && distanceFromSquared(this.entity.position(), target) > this.maxMovementRangeSquared) {
            this.entity.getNavigation().moveTo(target.x, target.y, target.z, this.speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        Vector3d target = this.targetPosition.get();
        return target != null && distanceFromSquared(this.entity.position(), target) > this.maxMovementRangeSquared && this.shouldReturn.get();
    }

    @Override
    public boolean canUse() {
        Vector3d target = this.targetPosition.get();
        return this.entity.level.random.nextFloat() < 0.25f && target != null && !this.isInRange(this.entity.position()) && this.shouldReturn.get();
    }
    
    public static GoToTargetPositionGoal byBlockPos(MobEntity entity, Supplier<BlockPos> pos, int maxMovementRange, float speed) {
        return new GoToTargetPositionGoal(entity, asVector(pos), maxMovementRange, speed);
    }
    
    public static GoToTargetPositionGoal byBlockPos(MobEntity entity, Supplier<BlockPos> pos, int maxMovementRange, float speed, Supplier<Boolean> shouldReturn) {
        return new GoToTargetPositionGoal(entity, asVector(pos), maxMovementRange, speed, shouldReturn);
    }
}
