package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.entity.base.FlyingBossBase;
import com.saphienyako.feywild.entity.goals.titania.TitaniaPanicGoal;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class TitaniaEntity extends FlyingBossBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(TitaniaEntity.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState FLYING_ANIMATION = new AnimationState();
    public final AnimationState CASTING_ANIMATION = new AnimationState();
    public final AnimationState ENCHANTING_ANIMATION = new AnimationState();

    private int movingTicks = 0;
    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    public TitaniaEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.titania").withStyle(ChatFormatting.YELLOW),
                BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false).setCreateWorldFog(true));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 35) //??
                .add(Attributes.FLYING_SPEED, 35) //??
                .add(Attributes.MAX_HEALTH, 150)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
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
        //TODO launch sprite entity at player location
         this.goalSelector.addGoal(50, new TitaniaPanicGoal(this, 0.003, 16));
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
        if (isActuallyMoving()) {
            if (!FLYING_ANIMATION.isStarted()) {
                FLYING_ANIMATION.start(this.tickCount);
            }
            IDLE_ANIMATION.stop();
        } else {
            if (!IDLE_ANIMATION.isStarted()) {
                IDLE_ANIMATION.start(this.tickCount);
            }
            FLYING_ANIMATION.stop();
        }
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return null;
          //TODO
        //random.nextInt(3) == 0 ? ModSoundEvents.titaniaAmbience.getSoundEvent() : ModSoundEvents.beatingWings.getSoundEvent();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return null;
        //TODO
        //return random.nextInt(3) == 0 ? ModSoundEvents.titaniaHurt.getSoundEvent() : null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
        //TODO
    }

    public TitaniaEntity.State getState() {
        TitaniaEntity.State[] states = TitaniaEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(TitaniaEntity.State state) {
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
        return ModParticles.SUMMER_SPARKLE_PARTICLE.get();
    }

    @Override
    public SpriteEntity.SpriteVariant getSpriteVariant() {
       return SpriteEntity.SpriteVariant.SUMMER;
    }

    public enum State {
        IDLE_FLYING, FLYING, CASTING, ENCHANTING
    }

}
