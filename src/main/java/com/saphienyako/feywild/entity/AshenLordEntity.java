package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.entity.goals.ashen_lord.SummerHurlLeavesGoal;
import com.saphienyako.feywild.entity.goals.ashen_lord.AutumnLeafShieldGoal;
import com.saphienyako.feywild.entity.goals.ashen_lord.WinterHurlLeavesGoal;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class AshenLordEntity extends BossBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(AshenLordEntity.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState WALKING_ANIMATION = new AnimationState();
    public final AnimationState CHANNEL_ANIMATION = new AnimationState();

    private int movingTicks = 0;
    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    public AshenLordEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.ashen_lord").withStyle(ChatFormatting.DARK_RED),
                BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false).setCreateWorldFog(true));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 4D)
                .add(Attributes.ATTACK_DAMAGE, 12)
                .add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.ARMOR, 5)
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
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(2, new AutumnLeafShieldGoal(this));
        this.goalSelector.addGoal(3, new SummerHurlLeavesGoal(this));
        this.goalSelector.addGoal(4, new WinterHurlLeavesGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 64, true, false, null));
        //TODO Goals
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 1.0f, 16));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomStrollGoal(this, 1));
    }

    //TODO add Moving Light?

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

        AshenLordEntity.State state = getState();


        if (state == AshenLordEntity.State.CHANNEL) {

            if (!CHANNEL_ANIMATION.isStarted()) {
                CHANNEL_ANIMATION.start(this.tickCount);
            }

           IDLE_ANIMATION.stop();
            WALKING_ANIMATION.stop();
            return;
        }

        CHANNEL_ANIMATION.stop();

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

       CHANNEL_ANIMATION.stop();
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
        return ModParticles.AUTUMN_SPARKLE_PARTICLE.get();
    }

    @Override
    public SpriteEntity.SpriteVariant getSpriteVariant() {
        return SpriteEntity.SpriteVariant.AUTUMN;
    }

    public AshenLordEntity.State getState() {
        AshenLordEntity.State[] states = AshenLordEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(AshenLordEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public enum State {
        IDLE, WALKING, CHANNEL
    }
}
