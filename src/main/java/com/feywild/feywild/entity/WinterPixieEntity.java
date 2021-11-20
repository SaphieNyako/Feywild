package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.PixieEntity;
import com.feywild.feywild.entity.goals.SummonSnowManGoal;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class WinterPixieEntity extends PixieEntity {
    
    protected WinterPixieEntity(EntityType<? extends PixieEntity> type, World world) {
        super(type, Alignment.WINTER, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new SummonSnowManGoal(this));
    }

    @Override
    public BasicParticleType getParticle() {
        return ParticleTypes.ENCHANTED_HIT;
    }
}
