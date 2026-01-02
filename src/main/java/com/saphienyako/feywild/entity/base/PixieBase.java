package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.goals.IronPanicGoal;
import com.saphienyako.feywild.entity.goals.PanicGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.network.PlaySoundMessage;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;

public abstract class PixieBase extends FlyingFeyBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(PixieBase.class, EntityDataSerializers.INT);
    public static final double MIN_MOVING_SPEED_SQR = 0.05 * 0.05;

    protected PixieBase(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new PanicGoal(this));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(40, new IronPanicGoal(this, this.level,0.25, 6 ));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if (superResult == InteractionResult.PASS) {

            // GIVE COOKIE, HEAL
            if (player.getItemInHand(hand).is(Items.COOKIE) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                if (!level.isClientSide) {
                    this.heal(3);

                    if (!this.isTamed() && player instanceof ServerPlayer && this.owner == null) {
                        Random random = new Random();
                        if (random.nextInt(6) == 0) {
                            this.spawnAtLocation(new ItemStack(ModItems.FEY_DUST.get()));
                            this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                            FeywildNetwork.sendToPlayer(new PlaySoundMessage(this.getCookieSound().getLocation(), this.blockPosition()), (ServerPlayer) player);

                            this.discard();
                            player.sendMessage(getFeyCookieMessage(), player.getUUID());
                        }
                    }

                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }

                    FeywildNetwork.sendParticles(this.level, ParticleMessage.Type.FEY_HEART, this.getOnPos());
                    player.swing(hand, true);
                }
                // NAME TAG
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);

                if (!level.isClientSide) {
                    player.sendMessage(getFeyNameMessage(), player.getUUID());
                    if (this.getVoiceActive()) {
                        FeywildNetwork.sendToPlayer(new PlaySoundMessage(this.getNameSound().getLocation(), this.blockPosition()), (ServerPlayer) player);
                    }
                }

                //PIXIE ORB OPENS MENU
            } else if (player.getItemInHand(hand).getItem() == ModItems.PIXIE_ORB.get() && this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
                FeywildNetwork.sendToPlayer(new OpenMenuMessage(
                                this.getName(),
                                this.getId(),
                                this.getAlignment(),
                                this.getFollowingPlayer(),
                                this.blockPosition(),
                                this.getAbilityActive(),
                                this.getVoiceActive()),
                        (ServerPlayer) player);
                player.swing(hand, true);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<PixieBase> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        AnimationController<PixieBase> castingController = new AnimationController<>(this, "castingController", 0, this::castingPredicate);
        animationData.addAnimationController(flyingController);
        animationData.addAnimationController(castingController);
    }
    @SuppressWarnings("removal")
    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().lengthSqr() < MIN_MOVING_SPEED_SQR) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fly", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }
    @SuppressWarnings("removal")
    private <E extends IAnimatable> PlayState castingPredicate(AnimationEvent<E> event) {
        if (this.getState() == State.SPELL_CASTING && !(this.dead || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("spellcasting", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }



    public PixieBase.State getState() {
        PixieBase.State[] states = PixieBase.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }


    public void setState(PixieBase.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public enum State {
        IDLE, POSE, FLY, SPELL_CASTING
    }
}
