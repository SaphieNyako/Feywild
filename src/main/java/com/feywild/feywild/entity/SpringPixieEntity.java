package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.PixieEntity;
import com.feywild.feywild.entity.goals.TargetBreedGoal;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class SpringPixieEntity extends PixieEntity {

    protected SpringPixieEntity(EntityType<? extends PixieEntity> type, World world) {
        super(type, Alignment.SPRING, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new TargetBreedGoal(this));
    }

    @Override
    public BasicParticleType getParticle() {
        return ParticleTypes.HAPPY_VILLAGER;
    }
}
