package com.feywild.feywild.entity;

import com.feywild.feywild.effects.ModEffects;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.goals.AddShieldGoal;
import com.feywild.feywild.entity.goals.BoredCheckingGoal;
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
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, false, new AddShieldGoal(this, ModEffects.fireWalk, 30)));
        //TODO make summer Misschief Goal
        //  this.goalSelector.addGoal(20, new TargetFireGoal(this));
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.summerSparkleParticle;
    }

    @Override
    public int getBoredCount() {
        return 10;
    }
}
