package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class TreeEntBase extends PathfinderMob implements IAnimatable, IOwnable, ISummonable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(TreeEntBase.class, EntityDataSerializers.INT);
    private static final int FEY_WOOD_HEAL_AMOUNT = 25;
    private final AnimationFactory factory = new AnimationFactory(this);
    //No Alignment
    protected UUID owner;
    @javax.annotation.Nullable
    private BlockPos summonPos = null;
    //TODO Add WoodBlock that heals this specific Tree Ent abstract method


    protected TreeEntBase(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.moveControl = new MoveControl(this);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0)
                .add(Attributes.ARMOR_TOUGHNESS, 5)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    public static boolean canSpawn(EntityType<? extends FeyBase> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter getter, BlockPos pos) {
        return getter.getRawBrightness(pos, 0) > 8;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.1f, 8));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, GoToTargetPositionGoal.byBlockPos(this, this::getSummonPos, 5, 0.5f));
        //ATTACK MONSTER GOAL
        //ATTACK PLAYER GOAL?
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 vec, @Nonnull InteractionHand hand) {
        return super.interactAt(player, vec, hand);
        //Special interaction
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    public TreeEntBase.State getState() {
        TreeEntBase.State[] states = TreeEntBase.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(TreeEntBase.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (this.getState() == TreeEntBase.State.ATTACKING) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tree_ent.attack", false));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tree_ent.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tree_ent.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.owner != null) {
            nbt.putUUID("Owner", this.owner);
        } else {
            nbt.remove("Owner");
        }
        if (this.summonPos != null) {
            nbt.put("SummonPos", NbtUtils.writeBlockPos(this.summonPos));
        } else {
            nbt.remove("SummonPos");
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        this.summonPos = nbt.contains("SummonPos", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(nbt.getCompound("SummonPos")).immutable() : null;
    }


    @javax.annotation.Nullable
    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(@Nullable UUID uid) {
        this.owner = uid;
    }

    @Override
    public Level getEntityLevel() {
        return this.level;
    }

    @Nullable
    @Override
    public BlockPos getSummonPos() {
        return this.summonPos;
    }

    @Override
    public void setSummonPos(BlockPos pos) {
        this.summonPos = pos == null ? null : pos.immutable();
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @Nonnull DamageSource source) {
        return false;
    }

    @Override
    public int getExperienceReward() {
        return this.isTamed() ? 0 : super.getExperienceReward();
    }

    @Override
    public boolean canBeLeashed(@Nonnull Player player) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSq) {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity entityIn) {
        return false;
        //CAN RIDE?
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
    public boolean isPersistenceRequired() {
        return true;
    }

    public enum State {
        IDLE, ATTACKING
    }
}
