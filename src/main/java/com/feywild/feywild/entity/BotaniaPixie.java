package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.FlyingFeyBase;
import com.feywild.feywild.entity.goals.FeywildPanicGoal;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.tag.ModBiomeTags;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class BotaniaPixie extends FlyingFeyBase {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(BotaniaPixie.class, EntityDataSerializers.INT);

    public BotaniaPixie(EntityType<? extends FeyBase> entityType, Level level) {
        super(entityType, Alignment.SPRING, level);
        this.entityData.set(VARIANT, BotaniaPixieVariant.DEFAULT.ordinal());
    }

    public BotaniaPixieVariant getVariant() {
        return BotaniaPixieVariant.values()[this.entityData.get(VARIANT)];
    }

    public void setVariant(BotaniaPixie.BotaniaPixieVariant variant) {
        this.entityData.set(VARIANT, variant.ordinal());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new FeywildPanicGoal(this, 0.003, 13));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, VARIANT.getId());
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("BotaniaPixieVariant", this.entityData.get(VARIANT));
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("BotaniaPixieVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("BotaniaPixieVariant"));
        }
    }

    @Nonnull
    @Override
    public SimpleParticleType getParticle() {
        return switch (getVariant()) {
            case SUMMER -> ModParticles.summerSparkleParticle;
            case AUTUMN -> ModParticles.autumnSparkleParticle;
            case WINTER -> ModParticles.winterSparkleParticle;
            default -> ModParticles.springSparkleParticle;
        };
    }

    @Override
    public void tick() {
        super.tick();

        if (level.getBiome(this.blockPosition()).is(ModBiomeTags.IS_AUTUMN)) {
            setVariant(BotaniaPixieVariant.AUTUMN);
        } else if (level.getBiome(this.blockPosition()).is(ModBiomeTags.IS_SPRING)) {
            setVariant(BotaniaPixieVariant.SPRING);
        } else if (level.getBiome(this.blockPosition()).is(ModBiomeTags.IS_SUMMER)) {
            setVariant(BotaniaPixieVariant.SUMMER);
        } else if (level.getBiome(this.blockPosition()).is(ModBiomeTags.IS_WINTER)) {
            setVariant(BotaniaPixieVariant.WINTER);
        }

        if (level.isClientSide && getParticle() != null && random.nextInt(11) == 0) {
            for (int i = 0; i < 4; i++) {
                this.level.addParticle(getParticle(), this.getX() + (Math.random() - 0.5) * 0.25,
                        this.getY() + (Math.random() - 0.5) * 0.25,
                        this.getZ() + (Math.random() - 0.5) * 0.25,
                        0, 0, 0);
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<BotaniaPixie> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        data.addAnimationController(flyingController);
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.botania_pixie.fly", true));
        return PlayState.CONTINUE;
    }

    public enum BotaniaPixieVariant {
        DEFAULT, SPRING, SUMMER, AUTUMN, WINTER
    }
}
