package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.goals.TargetBreedGoal;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class SpringPixie extends Fey {

    protected SpringPixie(EntityType<? extends Fey> type, Level level) {
        super(type, Alignment.SPRING, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new TargetBreedGoal(this));
    }

    @Override
    public SimpleParticleType getParticle() {
        return ParticleTypes.HAPPY_VILLAGER;
    }
}
