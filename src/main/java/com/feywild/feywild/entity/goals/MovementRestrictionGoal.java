package com.feywild.feywild.entity.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class MovementRestrictionGoal extends Goal {

    public BlockPos targetPosition;
    public int maxMovementRangeSquared;

    public MovementRestrictionGoal(Supplier<BlockPos> pos, int maxMovementRange) {
        this.targetPosition = pos.get();
        this.maxMovementRangeSquared = maxMovementRange * maxMovementRange;
    }

    public static double distanceFromSquared(BlockPos start, BlockPos end) {
        return ((start.getX() - end.getX()) * (start.getX() - end.getX())) + ((start.getY() - end.getY()) * (start.getY() - end.getY())) + ((start.getZ() - end.getZ()) * (start.getZ() - end.getZ()));
    }

    public boolean isInRange(BlockPos pos) {
        return distanceFromSquared(pos, targetPosition) <= maxMovementRangeSquared;
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
