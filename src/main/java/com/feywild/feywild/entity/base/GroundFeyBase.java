package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.trait.GroundEntity;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class GroundFeyBase extends FeyBase implements GroundEntity {

    protected GroundFeyBase(EntityType<? extends FeyBase> entityType, Alignment alignment, Level level) {
        super(entityType, alignment, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerGroundGoals(this);
    }
}
