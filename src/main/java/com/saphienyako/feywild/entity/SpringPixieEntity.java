package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SpringPixieEntity extends PixieBase {

    protected SpringPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Component getPixieNameMessage() {
        return  Component.translatable("message.fairy_craft.spring_pixie_name");
    }

    @Override
    protected Component getPixieCookieMessage() {
        return  Component.translatable("message.fairy_craft.spring_pixie_feed");
    }

    @Override
    protected MobEffect getMobEffect() {
        return ModEffects.SPRING_BLESSING.get();
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.SPRING_SPARKLE_PARTICLE.get();
    }
}
