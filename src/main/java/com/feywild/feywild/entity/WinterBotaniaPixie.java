package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.BotaniaPixie;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class WinterBotaniaPixie extends BotaniaPixie {

    public WinterBotaniaPixie(EntityType<? extends FeyBase> entityType, Level level) {
        super(entityType, Alignment.WINTER, level);
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.winterSparkleParticle;
    }
}
