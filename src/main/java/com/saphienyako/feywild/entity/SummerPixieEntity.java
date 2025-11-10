package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.goals.BreedAbilityGoal;
import com.saphienyako.feywild.entity.goals.CropGrowAbilityGoal;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class SummerPixieEntity extends PixieBase {
    protected SummerPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new CropGrowAbilityGoal(this, this.level()));
    }

    @Override
    protected Component getPixieNameMessage() {
        return  Component.translatable("message.fairy_craft.summer_pixie_name");
    }

    @Override
    protected Component getPixieCookieMessage() {
        return  Component.translatable("message.fairy_craft.summer_pixie_feed");
    }

    @Override
    protected MobEffect getMobEffect() {
        return ModEffects.SUMMER_BLESSING.get();
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.SUMMER_SPARKLE_PARTICLE.get();
    }
}
