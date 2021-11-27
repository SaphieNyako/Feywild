package com.feywild.feywild.entity.goals;


import com.feywild.feywild.entity.BeeKnight;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class ReturnToPositionKnightGoal extends  GoToTargetPositionGoal{
    private BeeKnight entity;
    public ReturnToPositionKnightGoal(BeeKnight entity, Supplier<Vec3> pos, int maxMovementRange, float speed) {
        super(entity, pos, maxMovementRange, speed);
        this.entity = entity;
    }


    @Override
    public boolean canUse() {
        return !entity.isAngry() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !entity.isAngry()  && super.canContinueToUse();
    }
}