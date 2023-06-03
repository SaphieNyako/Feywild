package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.entity.goals.pixie.*;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class SpringPixie extends Pixie {


    protected SpringPixie(EntityType<? extends Pixie> type, Level level) {
        super(type, Alignment.SPRING, level);
        setAbility(Ability.FLOWER_WALK);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        addPixieAbilities();
    }

    private void addPixieAbilities() {
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, false, new FlowerWalkGoal(this)));
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, false, new FrostWalkGoal(this)));
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, false, new FireWalkGoal(this)));
        this.goalSelector.addGoal(20, new BoredCheckingGoal(this, false, new FireWalkGoal(this)));
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.springSparkleParticle;
    }
}
