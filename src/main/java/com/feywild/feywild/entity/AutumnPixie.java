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

public class AutumnPixie extends Fey {

    protected AutumnPixie(EntityType<? extends Fey> type, Level level) {
        super(type, Alignment.AUTUMN, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, false, new AddShieldGoal(this, ModEffects.windWalk, 30)));
        //TODO add autumn misschief
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.autumnSparkleParticle;
    }

    @Override
    public int getBoredCount() {
        return 10;
    }
}
