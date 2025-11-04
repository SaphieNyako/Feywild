package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.SpringPixieEntity;
import com.saphienyako.feywild.entity.goals.BlessingEffectGoal;
import com.saphienyako.feywild.entity.goals.PanicGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;

public abstract class PixieBase extends FlyingFeyBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(SpringPixieEntity.class, EntityDataSerializers.INT);
    public final AnimationState IDLE_ANIMATION = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState SPELL_CASTING_ANIMATION = new AnimationState();
    public int spellCastingAnimationTimeout = 0;
    protected PixieBase(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    //Can always follow players

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(50, new PanicGoal(this, 0.003, 13));
        this.goalSelector.addGoal(20, new BlessingEffectGoal(this, getMobEffect(), this.level()));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }


    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {

        if(getState().equals(State.IDLE) && idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.IDLE_ANIMATION.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(getState().equals(State.SPELL_CASTING) && spellCastingAnimationTimeout <= 0) {
            this.IDLE_ANIMATION.stop();
            spellCastingAnimationTimeout = 85;
            SPELL_CASTING_ANIMATION.start(this.tickCount);
        } else {
            --this.spellCastingAnimationTimeout;
        }

        if(!getState().equals(State.SPELL_CASTING)) {
            SPELL_CASTING_ANIMATION.stop();
        }
      //TODO make FLY and IDLE work, check for moving?
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        ItemStack stack = player.getItemInHand(hand);
        if (superResult == InteractionResult.PASS) {
            if (!stack.isEmpty() && player instanceof ServerPlayer && this.tryAcceptGift((ServerPlayer) player, hand)) {
                player.swing(hand, true);

                //GIVE COOKIE, HEAL, DISSAPEAR
            } else if (player.getItemInHand(hand).is(Items.COOKIE) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(3);
                if (!this.isTamed() && player instanceof ServerPlayer && this.owner == null) {
                    Random random = new Random();
                    if (random.nextInt(6) == 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.FEY_DUST.get()));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                        this.discard();
                        player.sendSystemMessage(getPixieCookieMessage());
                    }
                }
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                FeywildNetwork.sendParticles(this.level(), ParticleMessage.Type.FEY_HEART, this.getOnPos());
                player.swing(hand, true);

                //NAME TAG
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level().isClientSide) {
                    player.sendSystemMessage(getPixieNameMessage());
                }

                //PIXIE ORB
            } /*  else if (!this.isTamed() && this.getOwner() == null && player instanceof ServerPlayer && player.getItemInHand(hand).getItem() == Items.ENDER_EYE) {
                player.swing(player.getUsedItemHand(), true);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                player.addItem(new ItemStack(ModItems.pixieOrb));
                this.remove(RemovalReason.DISCARDED);

                //QUEST
            } */ else if (this.isTamed() && player instanceof ServerPlayer && this.owner != null && this.owner.equals(player.getUUID())) {
                //TODO QUEST
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }

    protected abstract Component getPixieNameMessage();
    protected abstract Component getPixieCookieMessage();

    protected abstract MobEffect getMobEffect();

    private boolean tryAcceptGift(ServerPlayer player, InteractionHand hand) {
      /*  ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty()) {

            if (QuestData.get(player).checkComplete(FeyGiftTask.INSTANCE, input)) {
                if (!player.isCreative()) stack.shrink(1);
                player.sendSystemMessage(Component.translatable("message.feywild." + this.alignment.id + "_fey_thanks"));
                return true;
            }
        } */
        //TODO Quest GIFT
        return false;
    }

    public SpringPixieEntity.State getState() {
        SpringPixieEntity.State[] states = SpringPixieEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(SpringPixieEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public enum State {
        IDLE, POSE, FLY, SPELL_CASTING
    }
}
