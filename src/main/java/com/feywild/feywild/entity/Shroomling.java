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
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.phys.Vec3;
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

public class Shroomling extends GroundFeyBase implements IAnimatable, ITameable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(DwarfBlacksmith.class, EntityDataSerializers.INT);
    private static final double MIN_MOVING_SPEED_SQR = 0.05 * 0.05;
    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean isTamed;

    public Shroomling(EntityType<? extends FeyBase> type, Level level) {
        super(type, Alignment.AUTUMN, level);
        this.noCulling = true;
        this.moveControl = new MoveControl(this);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.1);
    }

    public static boolean canSpawn(EntityType<? extends Shroomling> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(level.getBlockState(pos.below()).getBlock());
    }

    @Override
    public boolean isTamed() {
        return this.isTamed;
    }

    public void setTamed(boolean tamed) {
        this.isTamed = tamed;
    }

    @Nullable
    @Override
    public Vec3 getCurrentPointOfInterest() {
        if (!this.isTamed()) {
            return null;
        } else if (summonPos != null) {
            return summonPos.add(0.5D, 0.5D, 0.5D);
        } else {
            Player player = this.getOwner();
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
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Tamed", this.isTamed);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.isTamed = nbt.getBoolean("Tamed");
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return null;
    }

    public Shroomling.State getState() {
        Shroomling.State[] states = Shroomling.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(Shroomling.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomStrollGoal(this, 1));
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
    public boolean causeFallDamage(float fallDistance, float multiplier, @Nonnull DamageSource source) {
        return false;
    }

    @Override
    protected int getExperienceReward(@Nonnull Player player) {
        return 0;
    }

    @Override
    public boolean canBeLeashed(@Nonnull Player player) {
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
        return SoundEvents.VILLAGER_HURT;
    }
    //replace with custom sound

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }
    //replace with custom sound

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }
    //replace with custom sound

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        if (!this.level.isClientSide) {
            if (player.isShiftKeyDown()) {
                if (this.owner != null && this.owner.equals(player.getUUID())) {
                    if (Objects.equals(this.getCurrentPointOfInterest(), player.position())) {
                        this.setCurrentTargetPos(this.blockPosition());
                        player.sendMessage(new TranslatableComponent("message.feywild.shroomling_fey_stay").append(new TranslatableComponent("message.feywild.fey_stay").withStyle(ChatFormatting.ITALIC)), player.getUUID());
                    } else {
                        this.setCurrentTargetPos((BlockPos) null);
                        player.sendMessage(new TranslatableComponent("message.feywild.shroomling_fey_follow").append(new TranslatableComponent("message.feywild.fey_follow").withStyle(ChatFormatting.ITALIC)), player.getUUID());
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
                player.addItem(new ItemStack(Items.BONE_MEAL));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.CONSUME;
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
        return true; //unsure
    }

    public enum State {
        IDLE, WAVING, SNEEZING
    }
}
