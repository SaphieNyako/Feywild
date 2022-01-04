package com.feywild.feywild.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class MovementRestrictionGoal extends Goal {

    public final Supplier<Vec3> targetPosition;
    public final int maxMovementRangeSquared;

    public MovementRestrictionGoal(Supplier<Vec3> pos, int maxMovementRange) {
        this.targetPosition = pos;
        this.maxMovementRangeSquared = maxMovementRange * maxMovementRange;
    }

    public static double distanceFromSquared(Vec3 start, Vec3 end) {
        return ((start.x - end.x) * (start.x - end.x)) + ((start.y - end.y) * (start.y - end.y)) + ((start.z - end.z) * (start.z - end.z));
    }

    public boolean isInRange(Vec3 pos) {
        Vec3 target = this.targetPosition.get();
        return target != null && distanceFromSquared(pos, target) <= this.maxMovementRangeSquared;
    }

    @Override
    public boolean canUse() {
        return false;
    }

    protected static Supplier<Vec3> asVector(Supplier<BlockPos> pos) {
        return () -> {
            BlockPos block = pos.get();
            return block == null ? null : new Vec3(block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5);
        };
    }
}
