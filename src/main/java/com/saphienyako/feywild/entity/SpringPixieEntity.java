package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.goals.BreedAbilityGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;


import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;

public class SpringPixieEntity extends PixieBase {

    protected SpringPixieEntity(EntityType<? extends CreatureEntity> entityType, World level) {
        super(entityType, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new BreedAbilityGoal(this, this.level));
    }

    @Override
    public IParticleData getParticle() {
        return ModParticles.SPRING_SPARKLE_PARTICLE.get();
    }

    @Override
   public Alignment getAlignment() {
        return Alignment.SPRING;
    }

    @Override
    public Item getDismissItem() {
        return ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get();
    }

    @Override
    public SoundEvent getCookieSound() {
        return ModSounds.SPRING_PIXIE_COOKIE.get();
    }

    @Override
    public SoundEvent getNameSound() {
        return ModSounds.SPRING_PIXIE_NAME.get();
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.SPRING_PIXIE_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.SPRING_PIXIE_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.SPRING_PIXIE_FOLLOW.get();
    }

    @Override
    public SoundEvent getStaySound() {
        return ModSounds.SPRING_PIXIE_STAY.get();
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.SPRING_PIXIE_ABILITY_ON.get();
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.SPRING_PIXIE_ABILITY_OFF.get();
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.SPRING_PIXIE_HURT.get();
    }


    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SPRING_PIXIE_DEATH.get();
    }


    @Override
    protected SoundEvent getAmbientSound() {
        Random random = new Random();
        if (random.nextFloat() < 0.1f) {
            return ModSounds.SPRING_PIXIE_GIGGLE.get();
        } else return null;
    }
}
