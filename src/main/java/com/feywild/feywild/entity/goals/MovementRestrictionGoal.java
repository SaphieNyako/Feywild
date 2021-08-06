package com.feywild.feywild.entity.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Supplier;

public class MovementRestrictionGoal extends Goal {

    public Supplier<Vector3d> targetPosition;
    public int maxMovementRangeSquared;
    
    public MovementRestrictionGoal(Supplier<Vector3d> pos, int maxMovementRange) {
        this.targetPosition = pos;
        this.maxMovementRangeSquared = maxMovementRange * maxMovementRange;
    }

    public static double distanceFromSquared(Vector3d start, Vector3d end) {
        return ((start.x - end.x) * (start.x - end.x)) + ((start.y - end.y) * (start.y - end.y)) + ((start.z - end.z) * (start.z - end.z));
    }

    public boolean isInRange(Vector3d pos) {
        Vector3d target = this.targetPosition.get();
        return target != null && distanceFromSquared(pos, target) <= this.maxMovementRangeSquared;
    }

    @Override
    public boolean canUse() {
        return false;
    }
    
    protected static Supplier<Vector3d> asVector(Supplier<BlockPos> pos) {
        return () -> {
            BlockPos block = pos.get();
            return block == null ? null : new Vector3d(block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5);
        };
    }
}
