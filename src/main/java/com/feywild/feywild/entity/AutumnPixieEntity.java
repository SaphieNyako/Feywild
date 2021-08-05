package com.feywild.feywild.entity;

import com.feywild.feywild.entity.goals.AddShieldGoal;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class AutumnPixieEntity extends FeyEntity {

    protected AutumnPixieEntity(EntityType<? extends FeyEntity> type, World world) {
        super(type, Alignment.AUTUMN, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AddShieldGoal(this));
    }
}