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
        BlockPos target = targetPosition.get();
        if (target != null && distanceFromSquared(entity.blockPosition(), target) > triggerRangeSquared) {
            entity.setPos(target.getX() + 0.5, target.getY() + 1, target.getZ() + 0.5);
        }
    }

    @Override
    public void tick() {
        BlockPos target = targetPosition.get();
        if (target != null && distanceFromSquared(entity.blockPosition(), target) > maxMovementRangeSquared) {
            entity.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 0.5); //1.5
        }
    }

    @Override
    public boolean canContinueToUse() {
        BlockPos target = targetPosition.get();
        return target != null && distanceFromSquared(entity.blockPosition(), target) > maxMovementRangeSquared && shouldReturn.get();
    }

    @Override
    public boolean canUse() {
        BlockPos target = targetPosition.get();
        return entity.level.random.nextFloat() < 0.02f && target != null && !this.isInRange(entity.blockPosition()) && shouldReturn.get();
    }
}
