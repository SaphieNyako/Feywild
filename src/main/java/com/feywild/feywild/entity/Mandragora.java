package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.GroundFeyBase;
import com.feywild.feywild.entity.goals.SingGoal;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Mandragora extends GroundFeyBase implements IAnimatable {

    public static final EntityDataAccessor<Boolean> CASTING = SynchedEntityData.defineId(Mandragora.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Mandragora.class, EntityDataSerializers.INT);

    public static final double MIN_MOVING_SPEED_SQR = 0.05 * 0.05;

    public Mandragora(EntityType<? extends FeyBase> type, Level level) {
        super(type, Alignment.SPRING, level);
        this.noCulling = true;
        this.entityData.set(VARIANT, getRandom().nextInt(MandragoraVariant.values().length));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return FeyBase.getDefaultAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1);
    }

    public MandragoraVariant getVariant() {
        return MandragoraVariant.values()[this.entityData.get(VARIANT)];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.2f, 4));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(20, new SingGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CASTING, false);
        this.entityData.define(VARIANT, VARIANT.getId());
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("MandragoraVariant", this.entityData.get(VARIANT));
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("MandragoraVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("MandragoraVariant"));
        }
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    public boolean isCasting() {
        return this.entityData.get(CASTING);
    }

    public void setCasting(boolean casting) {
        this.entityData.set(CASTING, casting);
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if (superResult == InteractionResult.PASS) {
            if (player.getItemInHand(hand).getItem() == Items.COOKIE && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                if (!level.isClientSide) {
                    this.heal(4);
                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                    FeywildMod.getNetwork().sendParticles(this.level, ParticleSerializer.Type.FEY_HEART, this.getX(), this.getY(), this.getZ());
                    player.swing(hand, true);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else if (player.getItemInHand(hand).getItem() == ModBlocks.mandrakeCrop.getSeed()) {
                if (!level.isClientSide) {
                    Mandragora entity = ModEntityTypes.mandragora.create(level);
                    if (entity != null) {
                        entity.setSummonPos(this.getSummonPos());
                        entity.setPos(position().x, position().y, position().z);
                        entity.setOwner(getOwner());
                        level.addFreshEntity(entity);
                        if (!player.isCreative())
                            player.getItemInHand(hand).shrink(1);
                        remove(Entity.RemovalReason.DISCARDED);
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        } else {
            return superResult;
        }
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (this.isCasting() && !(this.dead || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mandragora.sing", false));
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().lengthSqr() < MIN_MOVING_SPEED_SQR) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mandragora.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mandragora.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return ModSoundEvents.mandragoraHurt;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.mandragoraDeath;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextBoolean() ? ModSoundEvents.mandragoraAmbience : null;
    }

    public enum MandragoraVariant {
        MELON, ONION, POTATO, PUMPKIN, TOMATO
    }
}
