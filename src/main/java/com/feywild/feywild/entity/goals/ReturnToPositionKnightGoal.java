package com.feywild.feywild.entity.goals;


import com.feywild.feywild.entity.base.IAngry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class ReturnToPositionKnightGoal extends  GoToTargetPositionGoal{
    private Mob entity;
    public ReturnToPositionKnightGoal(Mob entity, Supplier<Vec3> pos, int maxMovementRange, float speed) {
        super(entity, pos, maxMovementRange, speed);
        this.entity = entity;
    }


    @Override
    public boolean canUse() {
        if(entity instanceof IAngry){
            return !((IAngry) entity).isAngry() && super.canUse();
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if(entity instanceof IAngry){
            return !((IAngry) entity).isAngry() && super.canContinueToUse();
        }
        return super.canContinueToUse();
    }
}