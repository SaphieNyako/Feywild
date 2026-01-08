package com.saphienyako.feywild.entity.base.intereface;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public interface GroundEntity {
    
    default void registerGroundGoals(CreatureEntity entity) {
        entity.goalSelector.addGoal(5, new MoveTowardsTargetGoal(entity, 0.1f, 8));
        entity.goalSelector.addGoal(8, new LookRandomlyGoal(entity));
        entity.goalSelector.addGoal(0, new SwimGoal(entity));
        entity.goalSelector.addGoal(50, new WaterAvoidingRandomWalkingGoal(entity, 1));
    }
}
