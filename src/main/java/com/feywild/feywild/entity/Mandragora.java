package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.GroundFeyBase;
import com.feywild.feywild.entity.base.ITameable;
import com.feywild.feywild.entity.goals.TameCheckingGoal;
import com.feywild.feywild.entity.goals.mandragora.SingGoal;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
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
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class Mandragora extends GroundFeyBase implements ITameable {

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

    @Override
    public boolean canFollowPlayer() {
        return true;
    }

    public MandragoraVariant getVariant() {
        return MandragoraVariant.values()[this.entityData.get(VARIANT)];
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.2f, 4));
        this.goalSelector.addGoal(10, new TameCheckingGoal(this, false, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false)));
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

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isCasting()) {
            if (this.level().random.nextInt(20) == 0 && !Minecraft.getInstance().isPaused()) {
                this.level().addParticle(ParticleTypes.NOTE,
                        this.getX() + (Math.random() - 0.5),
                        this.getY() + 1 + (Math.random() - 0.5),
                        this.getZ() + (Math.random() - 0.5),
                        0.917, 0, 0
                );
            }
        }
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if (superResult == InteractionResult.PASS) {
            if (player.getItemInHand(hand).is(ModItemTags.COOKIES) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                if (!level().isClientSide) {
                    this.heal(4);
                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                    FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.FEY_HEART, this.getX(), this.getY(), this.getZ());
                    player.swing(hand, true);
                }
                return InteractionResult.sidedSuccess(level().isClientSide);
            } else if (player.getItemInHand(hand).getItem() == ModBlocks.mandrakeCrop.getSeed()) {
                if (!level().isClientSide) {
                    Mandragora entity = ModEntities.mandragora.create(level());
                    if (entity != null) {
                        entity.setSummonPos(this.getSummonPos());
                        entity.setPos(position().x, position().y, position().z);
                        entity.setOwner(getOwner());
                        level().addFreshEntity(entity);
                        if (!player.isCreative())
                            player.getItemInHand(hand).shrink(1);
                        remove(Entity.RemovalReason.DISCARDED);
                    }
                }
                return InteractionResult.sidedSuccess(level().isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        } else {
            return superResult;
        }
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return ModSoundEvents.mandragoraHurt.getSoundEvent();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.mandragoraDeath.getSoundEvent();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextBoolean() ? ModSoundEvents.mandragoraAmbience.getSoundEvent() : null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (this.isCasting() && !(this.dead || this.isDeadOrDying())) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.mandragora.sing"));
                return PlayState.CONTINUE;
            }
            if (this.getDeltaMovement().lengthSqr() < MIN_MOVING_SPEED_SQR) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.mandragora.idle"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.mandragora.walk"));
            }
            return PlayState.CONTINUE;
        }));
    }

    public enum MandragoraVariant {
        MELON, ONION, POTATO, PUMPKIN, TOMATO
    }
}
