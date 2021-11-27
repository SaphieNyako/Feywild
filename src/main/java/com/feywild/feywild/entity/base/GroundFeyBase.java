package com.feywild.feywild.entity.base;

import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;

public abstract class GroundFeyBase extends FeyBase{
    protected GroundFeyBase(EntityType<? extends FeyBase> entityType, Alignment alignment, Level level) {
        super(entityType, alignment, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new WaterAvoidingRandomStrollGoal(this, 1));
    }
}
