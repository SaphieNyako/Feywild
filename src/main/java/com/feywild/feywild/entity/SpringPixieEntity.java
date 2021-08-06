package com.feywild.feywild.entity;

import com.feywild.feywild.entity.goals.TargetBreedGoal;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class SpringPixieEntity extends FeyEntity {

    protected SpringPixieEntity(EntityType<? extends FeyEntity> type, World world) {
        super(type, Alignment.SPRING, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new TargetBreedGoal(this));
    }
}
