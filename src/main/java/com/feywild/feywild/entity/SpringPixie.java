package com.feywild.feywild.entity;

import com.feywild.feywild.effects.ModEffects;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.goals.AddShieldGoal;
import com.feywild.feywild.entity.goals.BoredCheckingGoal;
import com.feywild.feywild.entity.goals.TargetBreedGoal;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class SpringPixie extends Fey {

    protected SpringPixie(EntityType<? extends Fey> type, Level level) {
        super(type, Alignment.SPRING, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, false, new TargetBreedGoal(this)));
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, true, new AddShieldGoal(this, ModEffects.flowerWalk, 20)));
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.springSparkleParticle;
    }

    @Override
    public int getBoredCount() {
        return 20;
    }
}
