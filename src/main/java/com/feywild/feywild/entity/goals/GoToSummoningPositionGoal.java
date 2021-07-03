package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.util.FeyEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class GoToSummoningPositionGoal extends MovementRestrictionGoal {

    MobEntity entity;
    Supplier<Boolean> shouldReturn;
    int maxMovementRange;

    public GoToSummoningPositionGoal(MobEntity entity, Supplier<BlockPos> pos, int maxMovementRange) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.shouldReturn = () -> true;
        this.maxMovementRange = maxMovementRange;
    }

    public GoToSummoningPositionGoal(MobEntity entity, Supplier<BlockPos> pos, int maxMovementRange, Supplier<Boolean> shouldReturn) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.shouldReturn = shouldReturn;

    }

    @Override
    public void start() {
        super.start();
        if(distanceFrom(entity.blockPosition(),summoningPosition) > maxMovementRange * 2 ){
            entity.setPos(summoningPosition.getX()+0.5,summoningPosition.getY()+1,summoningPosition.getZ()+0.5);
        }
    }

    @Override
    public void tick() {
        if (summoningPosition != null && distanceFrom(entity.blockPosition(), this.summoningPosition) > maxMovementRange) {
            entity.getNavigation().moveTo(this.summoningPosition.getX(), this.summoningPosition.getY(), this.summoningPosition.getZ(), 0.5); //1.5
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
