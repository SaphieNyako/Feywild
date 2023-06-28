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
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

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

        if (!this.level().isClientSide) {
            if (level().getBiome(this.blockPosition()).is(ModBiomeTags.IS_AUTUMN)) {
                setVariant(BotaniaPixieVariant.AUTUMN);
            } else if (level().getBiome(this.blockPosition()).is(ModBiomeTags.IS_SPRING)) {
                setVariant(BotaniaPixieVariant.SPRING);
            } else if (level().getBiome(this.blockPosition()).is(ModBiomeTags.IS_SUMMER)) {
                setVariant(BotaniaPixieVariant.SUMMER);
            } else if (level().getBiome(this.blockPosition()).is(ModBiomeTags.IS_WINTER)) {
                setVariant(BotaniaPixieVariant.WINTER);
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.botania_pixie.fly"));
            return PlayState.CONTINUE;
        }));
    }

    public enum BotaniaPixieVariant {
        DEFAULT("default"),
        SPRING(Alignment.SPRING),
        SUMMER(Alignment.SUMMER),
        AUTUMN(Alignment.AUTUMN),
        WINTER(Alignment.WINTER);
        
        public final String id;

        BotaniaPixieVariant(Alignment alignment) {
            this(alignment.id);
        }
        
        BotaniaPixieVariant(String id) {
            this.id = id;
        }
    }
}
