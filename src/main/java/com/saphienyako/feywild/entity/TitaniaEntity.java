package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.entity.base.FlyingBossBase;
import com.saphienyako.feywild.entity.goals.titania.TitaniaCastingGoal;
import com.saphienyako.feywild.entity.goals.titania.TitaniaEnchantingGoal;
import com.saphienyako.feywild.entity.goals.titania.TitaniaPanicGoal;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;

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
        this.setPathfindingMalus(PathType.WATER, -1.0F);
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
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 64, true, false, null));
        this.goalSelector.addGoal(40, new TitaniaCastingGoal(this, this.level()));
        this.goalSelector.addGoal(40, new TitaniaEnchantingGoal(this, this.level()));
        this.goalSelector.addGoal(50, new TitaniaPanicGoal(this, 0.003, 16));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
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

        TitaniaEntity.State state = getState();

        if (state == TitaniaEntity.State.CASTING) {

            if (!CASTING_ANIMATION.isStarted()) {
                CASTING_ANIMATION.start(this.tickCount);
            }

            IDLE_ANIMATION.stop();
            FLYING_ANIMATION.stop();
            ENCHANTING_ANIMATION.stop();
            return;
        }

        if (state == TitaniaEntity.State.ENCHANTING) {

            if (!ENCHANTING_ANIMATION.isStarted()) {
                ENCHANTING_ANIMATION.start(this.tickCount);
            }

            IDLE_ANIMATION.stop();
            FLYING_ANIMATION.stop();
            CASTING_ANIMATION.stop();
            return;
        }

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

        CASTING_ANIMATION.stop();
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return ModSounds.TITANIA_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TITANIA_DEATH.get();
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
