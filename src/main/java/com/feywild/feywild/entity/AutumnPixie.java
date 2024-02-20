package com.feywild.feywild.entity;

import com.feywild.feywild.entity.ability.Ability;
import com.feywild.feywild.entity.ability.ModAbilities;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class AutumnPixie extends Pixie {

    protected AutumnPixie(EntityType<? extends Pixie> type, Level level) {
        super(type, Alignment.AUTUMN, level);
    }

    @Override
    protected Ability<?> getDefaultAbility() {
        return ModAbilities.windWalk;
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.autumnSparkleParticle;
    }

}
