package com.feywild.feywild.entity.base;

import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public abstract class FlyingFeyBase extends FeyBase {

    protected FlyingFeyBase(EntityType<? extends FeyBase> entityType, Alignment alignment, Level level) {
        super(entityType, alignment, level);
        this.moveControl = new FlyingMoveControl(this, 4, true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(this, 1));
    }


    @Override
    public void travel(@Nonnull Vec3 position) {
        if (this.isInWater()) {
            this.moveRelative(0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
        } else if (this.isInLava()) {
            this.moveRelative(0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
        } else {
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1, this.getZ());
            float slipperiness = 0.91f;
            if (this.onGround) {
                slipperiness = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
            }

            float groundMovementModifier = 0.16277137f / (slipperiness * slipperiness * slipperiness);
            slipperiness = 0.91f;
            if (this.onGround) {
                slipperiness = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1f * groundMovementModifier : 0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(slipperiness));
        }

        this.animationSpeedOld = this.animationSpeed;
        double dx = this.getX() - this.xo;
        double dz = this.getZ() - this.zo;
        float scaledLastHorizontalMotion = (float) (Math.sqrt(dx * dx + dz * dz) * 4);
        if (scaledLastHorizontalMotion > 1) {
            scaledLastHorizontalMotion = 1;
        }
        this.animationSpeed += (scaledLastHorizontalMotion - this.animationSpeed) * 0.4;
        this.animationPosition += this.animationSpeed;
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        FlyingPathNavigation flyingPathNavigator = new FlyingPathNavigation(this, level);
        flyingPathNavigator.setCanOpenDoors(false);
        flyingPathNavigator.setCanFloat(true);
        flyingPathNavigator.setCanPassDoors(true);
        return flyingPathNavigator;
    }
}
