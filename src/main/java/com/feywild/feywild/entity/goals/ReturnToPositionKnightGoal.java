package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.BeeKnightEntity;
import com.feywild.feywild.entity.base.IAnger;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Supplier;

public class ReturnToPositionKnightGoal extends  GoToTargetPositionGoal{
    private MobEntity entity;
    public ReturnToPositionKnightGoal(MobEntity entity, Supplier<Vector3d> pos, int maxMovementRange, float speed) {
        super(entity, pos, maxMovementRange, speed);
        this.entity = entity;
    }


    @Override
    public boolean canUse() {
        if(entity instanceof IAnger){
            return !((IAnger) entity).isAngry() && super.canUse();
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if(entity instanceof IAnger){
            return !((IAnger) entity).isAngry() && super.canContinueToUse();
        }
        return super.canContinueToUse();
    }
}
