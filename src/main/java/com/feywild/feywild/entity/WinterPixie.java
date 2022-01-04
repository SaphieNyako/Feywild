package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.goals.SummonSnowManGoal;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class WinterPixie extends Fey {

    protected WinterPixie(EntityType<? extends Fey> type, Level level) {
        super(type, Alignment.WINTER, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new SummonSnowManGoal(this));
    }

    @Override
    public SimpleParticleType getParticle() {
        return ParticleTypes.ENCHANTED_HIT;
    }
}
