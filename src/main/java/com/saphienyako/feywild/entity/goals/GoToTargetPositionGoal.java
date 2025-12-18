package com.saphienyako.feywild.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class GoToTargetPositionGoal extends MovementRestrictionGoal {

    private final Mob entity;
    private final int triggerRangeSquared;
    private final float speed;

    public GoToTargetPositionGoal(Mob entity, Supplier<Vec3> pos, int maxMovementRange, float speed) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.speed = speed;
        this.triggerRangeSquared = (maxMovementRange * 2) * (maxMovementRange * 2);
    }
    @SuppressWarnings("unused")
    public GoToTargetPositionGoal(Mob entity, Supplier<Vec3> pos, int maxMovementRange, float speed, Supplier<Boolean> shouldReturn) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.speed = speed;
        this.triggerRangeSquared = (maxMovementRange * 2) * (maxMovementRange * 2);
    }

    @Override
    public void tick() {
        Vec3 target = this.targetPosition.get();
        if (target != null && distanceFromSquared(this.entity.position(), target) > this.triggerRangeSquared) {
            this.entity.setPos(target.x, target.y + 1, target.z);
        } else if (target != null && distanceFromSquared(this.entity.position(), target) > this.maxMovementRangeSquared) {
            this.entity.getNavigation().moveTo(target.x, target.y + 1, target.z, this.speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        Vec3 target = this.targetPosition.get();
        return target != null && distanceFromSquared(this.entity.position(), target) > this.maxMovementRangeSquared / 2.0;
    }

    @SuppressWarnings("resource")
    @Override
    public boolean canUse() {
        Vec3 target = this.targetPosition.get();
        return this.entity.level().random.nextFloat() < 0.25f && target != null && !this.isInRange(this.entity.position());
    }
    @SuppressWarnings("unused")
    public static GoToTargetPositionGoal byBlockPos(Mob entity, Supplier<BlockPos> pos, int maxMovementRange, float speed) {
        return new GoToTargetPositionGoal(entity, asVector(pos), maxMovementRange, speed);
    }
    @SuppressWarnings("unused")
    public static GoToTargetPositionGoal byBlockPos(Mob entity, Supplier<BlockPos> pos, int maxMovementRange, float speed, Supplier<Boolean> shouldReturn) {
        return new GoToTargetPositionGoal(entity, asVector(pos), maxMovementRange, speed, shouldReturn);
    }
}
