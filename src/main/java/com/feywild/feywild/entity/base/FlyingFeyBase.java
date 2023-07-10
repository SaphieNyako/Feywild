package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.trait.FlyingEntity;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class FlyingFeyBase extends FeyBase implements FlyingEntity {

    protected FlyingFeyBase(EntityType<? extends FeyBase> entityType, Alignment alignment, Level level) {
        super(entityType, alignment, level);
        this.moveControl = new FlyingMoveControl(this, 4, true);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerFlyingGoals(this);
    }

    @Override
    public void travel(@Nonnull Vec3 to) {
        this.flyingTravel(this, to);
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return this.createFlyingNavigation(this, level);
    }
}
