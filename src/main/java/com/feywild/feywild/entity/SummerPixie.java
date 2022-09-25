package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.goals.TargetFireGoal;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class SummerPixie extends Fey {

    protected SummerPixie(EntityType<? extends Fey> type, Level level) {
        super(type, Alignment.SUMMER, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new TargetFireGoal(this));
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.summerSparkleParticle;
    }
}
