package com.feywild.feywild.entity;

import com.feywild.feywild.entity.ability.Ability;
import com.feywild.feywild.entity.ability.ModAbilities;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SpringPixie extends Pixie {
    
    protected SpringPixie(EntityType<? extends Pixie> type, Level level) {
        super(type, Alignment.SPRING, level);
    }

    @Override
    protected Ability<?> getDefaultAbility() {
        return ModAbilities.flowerWalk;
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.springSparkleParticle;
    }
}
