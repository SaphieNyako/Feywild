package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.entity.base.intereface.GroundEntity;
import com.saphienyako.feywild.entity.goals.mab.IntimidateGoal;
import com.saphienyako.feywild.entity.goals.mab.PhysicalAttackGoal;
import com.saphienyako.feywild.entity.goals.mab.SummonVexGoal;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class OberonEntity extends BossBase implements GroundEntity {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(OberonEntity.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState WALKING_ANIMATION = new AnimationState();
    public final AnimationState CHARGING_ANIMATION = new AnimationState();
    public final AnimationState KICKING_ANIMATION = new AnimationState();

    public final AnimationState REARING_ANIMATION = new AnimationState();

    private int movingTicks = 0;
    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;
    public OberonEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.oberon").withStyle(ChatFormatting.GREEN),
                BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false).setCreateWorldFog(true));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, 0);
    }

    public static boolean canSpawn(EntityType<? extends BossBase> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.registerGroundGoals(this);
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
      //  this.goalSelector.addGoal(40, new OberonKickingGoal(this));
      //  this.goalSelector.addGoal(30, new OberonRearingGoal(this));
      //  this.goalSelector.addGoal(50, new OberonChargingGoal(this));
      //  this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 64, true, false, null));
        //TODO Goals
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > MIN_MOVING_SPEED_SQR;
    }

    private boolean isActuallyMoving() {
        if (isMoving()) {
            movingTicks = 5;
        } else {
            movingTicks = Math.max(0, movingTicks - 1);
        }
        return movingTicks > 0;
    }

    private void setupAnimationStates() {
        // IDLE, WALKING, CHARGING, KICKING, REARING
        OberonEntity.State state = getState();

        if (state == OberonEntity.State.CHARGING) {

            if (!CHARGING_ANIMATION.isStarted()) {
                CHARGING_ANIMATION.start(this.tickCount);
            }

            IDLE_ANIMATION.stop();
            WALKING_ANIMATION.stop();
            KICKING_ANIMATION.stop();
            REARING_ANIMATION.stop();
            return;
        }


        if (state == OberonEntity.State.REARING) {

            if (!REARING_ANIMATION.isStarted()) {
                REARING_ANIMATION.start(this.tickCount);
            }

            IDLE_ANIMATION.stop();
            WALKING_ANIMATION.stop();
            KICKING_ANIMATION.stop();
            CHARGING_ANIMATION.stop();
            return;
        }

        if (state == OberonEntity.State.KICKING) {

            if (!KICKING_ANIMATION.isStarted()) {
                KICKING_ANIMATION.start(this.tickCount);
            }

            IDLE_ANIMATION.stop();
            WALKING_ANIMATION.stop();
            REARING_ANIMATION.stop();
            CHARGING_ANIMATION.stop();
            return;
        }

        if (isActuallyMoving()) {

            if (!WALKING_ANIMATION.isStarted()) {
                WALKING_ANIMATION.start(this.tickCount);
            }

            IDLE_ANIMATION.stop();

        } else {

            if (!IDLE_ANIMATION.isStarted()) {
                IDLE_ANIMATION.start(this.tickCount);
            }

            WALKING_ANIMATION.stop();
        }

    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return super.getHurtSound(p_21239_);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    public SoundEvent getSummonSound() {
        return null;
    }

    @Override
    public Component getFeySummonMessage() {
        return null;
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.SPRING_SPARKLE_PARTICLE.get();
    }

    @Override
    public SpriteEntity.SpriteVariant getSpriteVariant() {
        return SpriteEntity.SpriteVariant.SPRING;
    }

    public OberonEntity.State getState() {
        OberonEntity.State[] states = OberonEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(OberonEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public enum State {
        IDLE, WALKING, CHARGING, KICKING, REARING
    }
}
