package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.entity.base.FlyingBossBase;
import com.saphienyako.feywild.entity.goals.mab.IntimidateGoal;
import com.saphienyako.feywild.entity.goals.mab.PhysicalAttackGoal;
import com.saphienyako.feywild.entity.goals.mab.SummonVexGoal;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.sound.ModSounds;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MabEntity extends FlyingBossBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(MabEntity.class, EntityDataSerializers.INT);

    public final AnimationState FLYING_IDLE_ANIMATION = new AnimationState();
    public final AnimationState FLYING_ANIMATION = new AnimationState();
    public final AnimationState CHANNEL_ANIMATION = new AnimationState();
    public final AnimationState INTIMIDATION_ANIMATION = new AnimationState();

    public final AnimationState ATTACKING_ANIMATION = new AnimationState();
    private int movingTicks = 0;
    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    public MabEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.mab").withStyle(ChatFormatting.BLUE),
                BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false).setCreateWorldFog(true));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FLYING_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.LUCK, 0.2);
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
        this.goalSelector.addGoal(4, new SummonVexGoal(this));
        this.goalSelector.addGoal(3, new IntimidateGoal(this));
        this.goalSelector.addGoal(2, new PhysicalAttackGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 64, true, false, null));
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

        MabEntity.State state = getState();

        if (state == MabEntity.State.ATTACKING) {

            if (!ATTACKING_ANIMATION.isStarted()) {
                ATTACKING_ANIMATION.start(this.tickCount);
            }

            FLYING_IDLE_ANIMATION.stop();
            FLYING_ANIMATION.stop();
            INTIMIDATION_ANIMATION.stop();
            CHANNEL_ANIMATION.stop();
            return;
        }


        if (state == MabEntity.State.CHANNEL) {

            if (!CHANNEL_ANIMATION.isStarted()) {
                CHANNEL_ANIMATION.start(this.tickCount);
            }

            FLYING_IDLE_ANIMATION.stop();
            FLYING_ANIMATION.stop();
            INTIMIDATION_ANIMATION.stop();
            ATTACKING_ANIMATION.stop();
            return;
        }

        if (state == MabEntity.State.INTIMIDATION) {

            if (!INTIMIDATION_ANIMATION.isStarted()) {
                INTIMIDATION_ANIMATION.start(this.tickCount);
            }

            FLYING_IDLE_ANIMATION.stop();
            FLYING_ANIMATION.stop();
            CHANNEL_ANIMATION.stop();
            ATTACKING_ANIMATION.stop();
            return;
        }

        CHANNEL_ANIMATION.stop();
        INTIMIDATION_ANIMATION.stop();
        ATTACKING_ANIMATION.stop();

        if (isActuallyMoving()) {

            if (!FLYING_ANIMATION.isStarted()) {
                FLYING_ANIMATION.start(this.tickCount);
            }

            FLYING_IDLE_ANIMATION.stop();

        } else {

            if (!FLYING_IDLE_ANIMATION.isStarted()) {
                FLYING_IDLE_ANIMATION.start(this.tickCount);
            }

            FLYING_ANIMATION.stop();
        }

        //CHANNEL_ANIMATION.stop();
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MAB_AMBIANCE.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return ModSounds.MAB_HURT.get();
    }


    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MAB_DEATH.get();
    }

    public MabEntity.State getState() {
        MabEntity.State[] states = MabEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(MabEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
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
        return ModParticles.WINTER_SPARKLE_PARTICLE.get();
    }

    @Override
    public SpriteEntity.SpriteVariant getSpriteVariant() {
        return SpriteEntity.SpriteVariant.WINTER;
    }

    public enum State {
        IDLE_FLYING, FLYING, CHANNEL, INTIMIDATION, ATTACKING
    }
}
