package com.feywild.feywild.entity.goals;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class GoToSummoningPositionGoal extends MovementRestrictionGoal {

    private final MobEntity entity;
    private final Supplier<Boolean> shouldReturn;
    private final int triggerRangeSquared;

    public GoToSummoningPositionGoal(MobEntity entity, Supplier<BlockPos> pos, int maxMovementRange) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.shouldReturn = () -> true;
        this.triggerRangeSquared = (maxMovementRange * 2) * (maxMovementRange * 2);
    }

    public GoToSummoningPositionGoal(MobEntity entity, Supplier<BlockPos> pos, int maxMovementRange, Supplier<Boolean> shouldReturn) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.shouldReturn = shouldReturn;
        this.triggerRangeSquared = (maxMovementRange * 2) * (maxMovementRange * 2);
    }

    @Override
    public void start() {
        super.start();
        if (distanceFromSquared(entity.blockPosition(), targetPosition) > triggerRangeSquared) {
            entity.setPos(targetPosition.getX() + 0.5, targetPosition.getY() + 1, targetPosition.getZ() + 0.5);
        }
    }

    @Override
    public void tick() {
        if (targetPosition != null && distanceFromSquared(entity.blockPosition(), this.targetPosition) > maxMovementRangeSquared) {
            entity.getNavigation().moveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), 0.5); //1.5
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetPosition != null && distanceFromSquared(entity.blockPosition(), this.targetPosition) > maxMovementRangeSquared && shouldReturn.get();
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.02f && targetPosition != null && !this.isInRange(entity.blockPosition()) && shouldReturn.get();
    }
}
