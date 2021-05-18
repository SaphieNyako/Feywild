package com.feywild.feywild.entity.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class MovementRestrictionGoal extends Goal {

    public BlockPos summoningPosition;
    public int maxMovementRange;

  public MovementRestrictionGoal(Supplier<BlockPos> pos, int maxMovementRange){
      this.summoningPosition = pos.get();
      this.maxMovementRange = maxMovementRange;
  }

    public boolean isInRange(BlockPos pos){
        return distanceFrom(pos, summoningPosition) <= maxMovementRange;
    }

    public static double distanceFrom(BlockPos start, BlockPos end){
        return Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2) + Math.pow(start.getZ() - end.getZ(), 2));
    }

    @Override
    public boolean canUse() {
        return false;
    }

}
