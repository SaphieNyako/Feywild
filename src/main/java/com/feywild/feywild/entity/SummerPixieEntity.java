package com.feywild.feywild.entity;

import com.feywild.feywild.entity.goals.TargetFireGoal;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class SummerPixieEntity extends FeyEntity {

    protected SummerPixieEntity(EntityType<? extends FeyEntity> type, World world) {
        super(type, Alignment.SUMMER, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new TargetFireGoal(this));
    }
}

