package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.entity.goals.ashen_lord.*;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AshenLordEntity extends BossBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(AshenLordEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> CHANNEL_TYPE = SynchedEntityData.defineId(AshenLordEntity.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState WALKING_ANIMATION = new AnimationState();
    public final AnimationState CHANNEL_ANIMATION = new AnimationState();

    private int movingTicks = 0;
    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    @Nullable
    private ForcedAbility forcedAbility;

    private boolean retaliationRequested;

    private int agitationStage = 0;

    public AshenLordEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.ashen_lord").withStyle(ChatFormatting.DARK_RED),
                BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true).setCreateWorldFog(true));
        this.maxUpStep = 1.2F;
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 4D)
                .add(Attributes.ATTACK_DAMAGE, 12)
                .add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.ARMOR, 5);


    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
        this.entityData.define(CHANNEL_TYPE, 0);
    }

    public static boolean canSpawn(EntityType<? extends BossBase> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(1, new AshenLordRetaliationGoal(this));
        this.goalSelector.addGoal(2, new AutumnLeafShieldGoal(this));
        this.goalSelector.addGoal(3, new SummerHurlLeavesGoal(this));
        this.goalSelector.addGoal(4, new WinterHurlLeavesGoal(this));
        this.goalSelector.addGoal(5, new SpringLeafWhirlwindGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 64, true, false, null));
        this.goalSelector.addGoal(40, new MoveTowardsTargetGoal(this, 1.0f, 16));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomStrollGoal(this, 1));
    }

    //TODO add Moving Light?

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean damaged = super.hurt(source, amount);

        if (!damaged || this.level.isClientSide()) {
            return damaged;
        }

        if (source.getEntity() instanceof Player) {
            retaliationRequested = true;
        }

        return true;
    }

    public boolean hasRetaliationRequest() {
        return retaliationRequested;
    }

    public void consumeRetaliationRequest() {
        retaliationRequested = false;
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide()) {
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

        State state = getState();


        if (state == State.CHANNEL) {

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
        return ModSounds.ASHEN_LORD_AMBIANCE.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return ModSounds.ASHEN_LORD_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ASHEN_LORD_DEATH.get();
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

    public State getState() {
        State[] states = State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public ChannelType getChannelType() {
        ChannelType[] types = ChannelType.values();
        return types[Mth.clamp(this.entityData.get(CHANNEL_TYPE), 0, types.length - 1)];
    }

    public void setChannelType(ChannelType channelType) {
        this.entityData.set(CHANNEL_TYPE, channelType.ordinal());
    }

    public void startChanneling(ChannelType channelType) {
        this.setChannelType(channelType);
        this.setState(State.CHANNEL);
    }

    public void stopChanneling() {
        this.setState(State.IDLE);
        this.setChannelType(ChannelType.NONE);
    }

    @Nullable
    public ForcedAbility getForcedAbility() {
        return forcedAbility;
    }

    public boolean isForcedAbility(ForcedAbility ability) {
        return forcedAbility == ability;
    }

    public void setForcedAbility(ForcedAbility ability) {
        this.forcedAbility = ability;
    }

    public void clearForcedAbility() {
        this.forcedAbility = null;
    }

    public int getAgitationStage() {
        return agitationStage;
    }

    public void increaseAgitationStage() {
        agitationStage = Math.min(agitationStage + 1, 2);
    }

    public void resetAgitationStage() {
        agitationStage = 0;
    }

    public enum State {
        IDLE, WALKING, CHANNEL
    }

    public enum ChannelType {
        NONE, SPRING, SUMMER, AUTUMN, WINTER
    }

    public enum ForcedAbility {
        AUTUMN_SHIELD, SPRING_WHIRLWIND, SUMMER_LEAVES, WINTER_LEAVES
    }
}
