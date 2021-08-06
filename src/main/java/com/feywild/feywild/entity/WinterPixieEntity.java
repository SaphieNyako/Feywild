package com.feywild.feywild.entity;

import com.feywild.feywild.entity.goals.SummonSnowManGoal;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class WinterPixieEntity extends FeyEntity {
    
    protected WinterPixieEntity(EntityType<? extends FeyEntity> type, World world) {
        super(type, Alignment.WINTER, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new SummonSnowManGoal(this));
    }
}
