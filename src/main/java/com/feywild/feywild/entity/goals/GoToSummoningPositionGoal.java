package com.feywild.feywild.entity.goals;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class GoToSummoningPositionGoal extends MovementRestrictionGoal{

    MobEntity entity;
    Supplier<Boolean> shouldReturn;
    int maxMovementRange;

        //TODO: Check if GoToSummoningPositionGoal works as intended. Goal should go on until position is reached, or set a timer.

    public GoToSummoningPositionGoal(MobEntity entity,Supplier<BlockPos> pos, int maxMovementRange) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.shouldReturn = () -> true;
        this.maxMovementRange = maxMovementRange;
    }

    public GoToSummoningPositionGoal(MobEntity entity,Supplier<BlockPos> pos, int maxMovementRange, Supplier<Boolean> shouldReturn) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.shouldReturn = shouldReturn;

    }

    @Override
    public void tick() {
        if(summoningPosition != null && distanceFrom(entity.blockPosition(), this.summoningPosition) > maxMovementRange){
            entity.getNavigation().moveTo(this.summoningPosition.getX(), this.summoningPosition.getY(), this.summoningPosition.getZ(), 1.5); //1.5
        }
    }

    @Override
    public boolean canContinueToUse() {
        return summoningPosition != null && distanceFrom(entity.blockPosition(), this.summoningPosition) > maxMovementRange && shouldReturn.get();
    }


    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.02f && summoningPosition != null && !this.isInRange(entity.blockPosition()) && shouldReturn.get();
    }
}
