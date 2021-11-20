package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.PixieEntity;
import com.feywild.feywild.entity.goals.AddShieldGoal;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class AutumnPixieEntity extends PixieEntity {

    protected AutumnPixieEntity(EntityType<? extends PixieEntity> type, World world) {
        super(type, Alignment.AUTUMN, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new AddShieldGoal(this));
    }

    @Override
    public BasicParticleType getParticle() {
        return ParticleTypes.WITCH;
    }
}