package com.feywild.feywild.entity.trait;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface FlyingEntity {
    
    default void registerFlyingGoals(PathfinderMob self) {
        self.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(self, 1));
    }
    
    default void flyingTravel(PathfinderMob self, Vec3 position) {
        if (self.isInWater()) {
            self.moveRelative(0.02f, position);
            self.move(MoverType.SELF, self.getDeltaMovement());
            self.setDeltaMovement(self.getDeltaMovement().scale(0.8));
        } else if (self.isInLava()) {
            self.moveRelative(0.02f, position);
            self.move(MoverType.SELF, self.getDeltaMovement());
            self.setDeltaMovement(self.getDeltaMovement().scale(0.5));
        } else {
            BlockPos ground = new BlockPos(self.getBlockX(), self.getBlockY() - 1, self.getBlockZ());
            float slipperiness = 0.91f;
            if (self.onGround()) {
                slipperiness = self.level().getBlockState(ground).getFriction(self.level(), ground, self) * 0.91F;
            }

            float groundMovementModifier = 0.16277137f / (slipperiness * slipperiness * slipperiness);
            slipperiness = 0.91f;
            if (self.onGround()) {
                slipperiness = self.level().getBlockState(ground).getFriction(self.level(), ground, self) * 0.91F;
            }

            self.moveRelative(self.onGround() ? 0.1f * groundMovementModifier : 0.02f, position);
            self.move(MoverType.SELF, self.getDeltaMovement());
            self.setDeltaMovement(self.getDeltaMovement().scale(slipperiness));
        }

        double dx = self.getX() - self.xo;
        double dz = self.getZ() - self.zo;
        float scaledLastHorizontalMotion = (float) (Math.sqrt(dx * dx + dz * dz) * 4);
        if (scaledLastHorizontalMotion > 1) {
            scaledLastHorizontalMotion = 1;
        }
        self.walkAnimation.update(scaledLastHorizontalMotion, 0.4f);
    }

    default PathNavigation createFlyingNavigation(PathfinderMob self, Level level) {
        FlyingPathNavigation flyingPathNavigator = new FlyingPathNavigation(self, level);
        flyingPathNavigator.setCanOpenDoors(false);
        flyingPathNavigator.setCanFloat(true);
        flyingPathNavigator.setCanPassDoors(true);
        return flyingPathNavigator;
    }
}
