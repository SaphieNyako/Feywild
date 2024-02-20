package com.feywild.feywild.entity;

import com.feywild.feywild.entity.ability.Ability;
import com.feywild.feywild.entity.ability.ModAbilities;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SummerPixie extends Pixie {

    protected SummerPixie(EntityType<? extends Pixie> type, Level level) {
        super(type, Alignment.SUMMER, level);
    }

    @Override
    protected Ability<?> getDefaultAbility() {
        return ModAbilities.fireWalk;
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.summerSparkleParticle;
    }
}
