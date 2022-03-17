package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.GroundFeyBase;
import com.feywild.feywild.entity.base.ITameable;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.entity.goals.SneezeGoal;
import com.feywild.feywild.entity.goals.WaveGoal;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class ShroomlingEntity extends GroundFeyBase implements IAnimatable, ITameable {

    public static final DataParameter<Integer> STATE = EntityDataManager.defineId(ShroomlingEntity.class, DataSerializers.INT);
    private static final double MIN_MOVING_SPEED_SQR = 0.05 * 0.05;
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean isTamed = false;

    protected ShroomlingEntity(EntityType<? extends FeyBase> entityType, World world) {
        super(entityType, Alignment.AUTUMN, world);
        this.moveControl = new MovementController(this);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.1);
    }

    public static boolean canSpawn(EntityType<? extends ShroomlingEntity> entity, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(world.getBlockState(pos.below()).getBlock());
    }

    @Nullable
    @Override
    public Vector3d getCurrentPointOfInterest() {
        if (!this.isTamed()) {
            return null;
        } else if (summonPos != null) {
            return summonPos.add(0.5D, 0.5D, 0.5D);
        } else {
            PlayerEntity player = this.getOwner();
            if (player != null) {
                return player.position();
            }
        }
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Tamed", this.isTamed);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.isTamed = nbt.getBoolean("Tamed");
    }

    @Nullable
    @Override
    public BasicParticleType getParticle() {
        return null;
    }

    @Override
    public boolean isTamed() {
        return this.isTamed;
    }

    public void setTamed(boolean tamed) {
        this.isTamed = tamed;
    }

    public ShroomlingEntity.State getState() {
        ShroomlingEntity.State[] states = ShroomlingEntity.State.values();
        return states[MathHelper.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(ShroomlingEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.1f, 8));
        this.goalSelector.addGoal(20, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, getMovementRange(), 1.0f));
        this.goalSelector.addGoal(60, new WaveGoal(this));
        this.goalSelector.addGoal(30, new SneezeGoal(this));
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
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
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected int getExperienceReward(@Nonnull PlayerEntity player) {
        return 0;
    }

    @Override
    public boolean canBeLeashed(@Nonnull PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity entityIn) {
        return false;
    }

    /* SOUND */

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
        return ModSoundEvents.shroomlingHurt;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.shroomlingDeath;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextBoolean() ? ModSoundEvents.shroomlingAmbience : null;
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(PlayerEntity player, @Nonnull Vector3d vec, @Nonnull Hand hand) {
        if (!this.level.isClientSide) {
            if (player.isShiftKeyDown()) {
                if (this.owner != null && this.owner.equals(player.getUUID())) {
                    if (Objects.equals(this.getCurrentPointOfInterest(), player.position())) {
                        this.setOrderedToStay();
                    } else {
                        this.setOrderedToFollow();
                    }
                }
            }
        }

        ItemStack itemstack = player.getItemInHand(hand);
        if (ComposterBlock.COMPOSTABLES.containsKey(itemstack.getItem()) &&
                player == this.getOwner()) {
            if (!level.isClientSide) {
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                FeywildMod.getNetwork().sendParticles(this.level, ParticleSerializer.Type.CROPS_GROW, this.getX(), this.getY(), this.getZ());
                player.swing(hand, true);
                this.spawnAtLocation(new ItemStack(Items.BONE_MEAL));
                this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);
            }
            return ActionResultType.sidedSuccess(level.isClientSide);
        }
        return ActionResultType.CONSUME;
    }

    /* ANIMANTION */

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (!this.dead && !this.isDeadOrDying()) {
            if (this.getState() == State.WAVING) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shroomling.wave", false));
                return PlayState.CONTINUE;
            } else if (this.getState() == State.SNEEZING) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shroomling.sneeze", false));
                return PlayState.CONTINUE;
            }
        }
        if (this.getDeltaMovement().lengthSqr() < MIN_MOVING_SPEED_SQR) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shroomling.static", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shroomling.walk", true));
        }

        return PlayState.CONTINUE;
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean isPersistenceRequired() {
        return this.isTamed;
    }

    public enum State {
        IDLE, WAVING, SNEEZING
    }
}
