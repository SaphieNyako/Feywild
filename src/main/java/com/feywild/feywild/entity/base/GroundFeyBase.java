package com.feywild.feywild.entity.base;

import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.World;


public abstract class GroundFeyBase extends FeyBase {

    protected GroundFeyBase(EntityType<? extends CreatureEntity> entityType, Alignment alignment, World world) {
        super(entityType, alignment, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new WaterAvoidingRandomWalkingGoal(this, 1));
    }
}
