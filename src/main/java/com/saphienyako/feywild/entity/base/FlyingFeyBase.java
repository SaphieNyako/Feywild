package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.base.intereface.FlyingEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class FlyingFeyBase extends FeyBase implements FlyingEntity {
    protected FlyingFeyBase(EntityType<? extends CreatureEntity> entityType, World level) {
        super(entityType, level);
        this.moveControl = new FlyingMovementController(this, 4, true);
    }

    @Override
    public boolean isOnGround() {
        return false;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerFlyingGoals(this);
    }

    @Override
    public void travel(@Nonnull Vector3d to) {
        this.flyingTravel(this, to);
    }

    @Nonnull
    @Override
    protected PathNavigator createNavigation(@Nonnull World level) {
        return this.createFlyingNavigation(this, level);
    }
}
