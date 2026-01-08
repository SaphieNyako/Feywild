package com.saphienyako.feywild.entity.base.intereface;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public interface FlyingEntity {


    default void registerFlyingGoals(CreatureEntity self) {
        self.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(self, 1));
    }

    default void flyingTravel(CreatureEntity entity, Vector3d position) {
        if (entity.isInWater()) {
            entity.moveRelative(0.02f, position);
            entity.move(MoverType.SELF, entity.getDeltaMovement());
            entity.setDeltaMovement(entity.getDeltaMovement().scale(0.8));
        } else if (entity.isInLava()) {
            entity.moveRelative(0.02f, position);
            entity.move(MoverType.SELF, entity.getDeltaMovement());
            entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5));
        } else {
            BlockPos ground = new BlockPos(entity.getX(), entity.getY() - 1, entity.getZ());
            float slipperiness = 0.91f;
            if (entity.isOnGround()) {
                slipperiness = entity.level.getBlockState(ground).getSlipperiness(entity.level, ground, entity) * 0.91F;
            }

            float groundMovementModifier = 0.16277137f / (slipperiness * slipperiness * slipperiness);
            slipperiness = 0.91f;
            if (entity.isOnGround()) {
                slipperiness = entity.level.getBlockState(ground).getSlipperiness(entity.level, ground, entity) * 0.91F;
            }

            entity.moveRelative(entity.isOnGround() ? 0.1f * groundMovementModifier : 0.02f, position);
            entity.move(MoverType.SELF, entity.getDeltaMovement());
            entity.setDeltaMovement(entity.getDeltaMovement().scale(slipperiness));
        }

        // --- Custom animation tracking for 1.16.5 ---
        try {
            entity.getClass().getField("prevAnimationSpeed").setFloat(entity, entity.getClass().getField("animationSpeed").getFloat(entity));
            double dx = entity.getX() - entity.xOld;
            double dz = entity.getZ() - entity.yOld;
            float motion = (float) Math.sqrt(dx * dx + dz * dz) * 4;
            if (motion > 1) motion = 1;
            float currentSpeed = entity.getClass().getField("animationSpeed").getFloat(entity);
            entity.getClass().getField("animationSpeed").setFloat(entity, currentSpeed + (motion - currentSpeed) * 0.4f);
            float animPos = entity.getClass().getField("animationPosition").getFloat(entity);
            entity.getClass().getField("animationPosition").setFloat(entity, animPos + (float) entity.getClass().getField("animationSpeed").getFloat(entity));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    }

    default PathNavigator createFlyingNavigation(MobEntity entity, World level) {
        FlyingPathNavigator navigator = new FlyingPathNavigator(entity, level);
        navigator.setCanOpenDoors(false);
        //navigator.setCanFloat(true);
        //navigator.setCanPassDoors(true);
        return navigator;
    }
}
