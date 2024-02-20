package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.FlyingBossBase;
import com.feywild.feywild.entity.goals.FeywildPanicGoal;
import com.feywild.feywild.entity.goals.titania.BossTargetFireGoal;
import com.feywild.feywild.entity.goals.titania.SummonBeeKnightGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Titania extends FlyingBossBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Titania.class, EntityDataSerializers.INT);
    public final Alignment alignment;

    protected Titania(EntityType<? extends Titania> entityType, Level level) {
        super(entityType, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.titania"),
                BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false).setCreateWorldFog(true));
        this.alignment = Alignment.SUMMER;
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 150)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.LUCK, 0.2);
    }

    public static boolean canSpawn(EntityType<? extends Titania> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(40, new SummonBeeKnightGoal(this));
        this.goalSelector.addGoal(50, new BossTargetFireGoal(this));
        this.goalSelector.addGoal(50, new FeywildPanicGoal(this, 0.003, 16));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return random.nextInt(3) == 0 ? ModSoundEvents.titaniaAmbience.getSoundEvent() : ModSoundEvents.beatingWings.getSoundEvent();
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return random.nextInt(3) == 0 ? ModSoundEvents.titaniaHurt.getSoundEvent() : null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.titaniaDeath.getSoundEvent();
    }

    @Override
    public float getVoicePitch() {
        return 1.1f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    public Titania.State getState() {
        Titania.State[] states = Titania.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(Titania.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (!this.dead && !this.isDeadOrDying()) {
                if (this.getState() == State.CASTING) {
                    event.getController().setAnimation(RawAnimation.begin().thenPlay("casting"));
                    return PlayState.CONTINUE;
                } else if (this.getState() == Titania.State.ENCHANTING) {
                    event.getController().setAnimation(RawAnimation.begin().thenPlay("enchanting"));
                    return PlayState.CONTINUE;
                }
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("flying"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("flying_idle"));
            }
            return PlayState.CONTINUE;
        }));
    }

    public enum State {
        IDLE, CASTING, ENCHANTING
    }
}
