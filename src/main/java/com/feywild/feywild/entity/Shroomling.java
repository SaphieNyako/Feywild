package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.GroundFeyBase;
import com.feywild.feywild.entity.base.ITameable;
import com.feywild.feywild.entity.goals.shroomling.SneezeGoal;
import com.feywild.feywild.entity.goals.shroomling.WaveGoal;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class Shroomling extends GroundFeyBase implements ITameable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Shroomling.class, EntityDataSerializers.INT);

    public static final double MIN_MOVING_SPEED_SQR = 0.05 * 0.05;

    public Shroomling(EntityType<? extends FeyBase> type, Level level) {
        super(type, Alignment.AUTUMN, level);
        this.noCulling = true;
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return FeyBase.getDefaultAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1);
    }

    @Override
    public float getTargetPositionSpeed() {
        return 1.0f;
    }

    @Override
    public boolean canFollowPlayer() {
        return true;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(60, new WaveGoal(this));
        this.goalSelector.addGoal(30, new SneezeGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    public Shroomling.State getState() {
        Shroomling.State[] states = Shroomling.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(Shroomling.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        ItemStack itemstack = player.getItemInHand(hand);
        if (ComposterBlock.COMPOSTABLES.containsKey(itemstack.getItem()) && player.getGameProfile().getId().equals(this.getOwner())
                && !player.getItemInHand(hand).is(ModItemTags.COOKIES)
                && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.CROPS_GROW, this.getX(), this.getY(), this.getZ());
            player.swing(hand, true);
            this.spawnAtLocation(new ItemStack(Items.BONE_MEAL));
            this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else if (player.getItemInHand(hand).is(ModItemTags.COOKIES) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
            this.heal(4);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 1, this.getZ());
            player.swing(hand, true);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
        return ModSoundEvents.shroomlingHurt.getSoundEvent();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.shroomlingDeath.getSoundEvent();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextBoolean() ? ModSoundEvents.shroomlingAmbience.getSoundEvent() : null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (!this.dead && !this.isDeadOrDying()) {
                if (this.getState() == State.WAVING) {
                    event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shroomling.wave"));
                    return PlayState.CONTINUE;
                } else if (this.getState() == State.SNEEZING) {
                    event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shroomling.sneeze"));
                    return PlayState.CONTINUE;
                }
            }
            if (this.getDeltaMovement().lengthSqr() < MIN_MOVING_SPEED_SQR) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shroomling.static"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shroomling.walk"));
            }

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    public enum State {
        IDLE, WAVING, SNEEZING
    }
}
