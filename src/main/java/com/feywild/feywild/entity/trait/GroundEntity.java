package com.feywild.feywild.entity.trait;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public interface GroundEntity {
    
    default void registerGroundGoals(PathfinderMob self) {
        self.goalSelector.addGoal(5, new MoveTowardsTargetGoal(self, 0.1f, 8));
        self.goalSelector.addGoal(8, new RandomLookAroundGoal(self));
        self.goalSelector.addGoal(0, new FloatGoal(self));
        self.goalSelector.addGoal(50, new WaterAvoidingRandomStrollGoal(self, 1));
    }
}
