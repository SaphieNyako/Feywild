package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.goals.GatherCropsAbilityGoal;
import com.saphienyako.feywild.entity.goals.GatherMobItemsGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class WinterPixieEntity extends PixieBase {
    protected WinterPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new GatherMobItemsGoal(this, this.level()));
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
        return ModEffects.WINTER_BLESSING.get();
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.WINTER_SPARKLE_PARTICLE.get();
    }
}
