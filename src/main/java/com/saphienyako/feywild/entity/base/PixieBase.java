package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.goals.IronPanicGoal;
import com.saphienyako.feywild.entity.goals.PanicGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(PixieBase.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState SPELL_CASTING_ANIMATION = new AnimationState();
    public int spellCastingAnimationTimeout = 0;
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


    @Override
    public void tick() {
        super.tick();
        if(this.level.isClientSide()) {
            setupAnimationStates();
        }
        if (level.isClientSide && this.getParticle() != null && random.nextInt(11) == 0) {
            for (int i = 0; i < 4; i++) {
                level.addParticle(this.getParticle(),
                        this.getX() + (Math.random() - 0.5),
                        this.getY() + 1 + (Math.random() - 0.5),
                        this.getZ() + (Math.random() - 0.5),
                        0, 0, 0
                );
            }
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
        if (superResult == InteractionResult.PASS) {

            // GIVE COOKIE, HEAL
            if (player.getItemInHand(hand).is(Items.COOKIE) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                if (!level.isClientSide) {
                    this.heal(3);

                    if (!this.isTamed() && player instanceof ServerPlayer serverPlayer && this.owner == null) {
                        Random random = new Random();
                        if (random.nextInt(6) == 0) {
                            this.spawnAtLocation(new ItemStack(ModItems.FEY_DUST.get()));
                            this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                            if (ModConfig.COMMON.voice_active.get()) {
                                serverPlayer.playNotifySound(
                                        this.getCookieSound(),
                                        SoundSource.NEUTRAL,
                                        1.0F,
                                        1.0F
                                );
                            }
                            this.discard();
                            player.sendSystemMessage(getFeyCookieMessage());
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
                    player.sendSystemMessage(getFeyNameMessage());
                    if (this.getVoiceActive() && ModConfig.COMMON.voice_active.get()) {
                        player.playNotifySound(
                                this.getNameSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
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
            } //UNTAMED MESSAGE
            else if (!this.isTamed() || !player.getUUID().equals(this.owner)) {
                if (player instanceof ServerPlayer serverPlayer) {
                    player.displayClientMessage(
                            Component.translatable("message.feywild.pixie_whisper")
                                    .withStyle(ChatFormatting.LIGHT_PURPLE)
                                    .append(Component.translatable("message.feywild.pixie_orb_untamed").withStyle(ChatFormatting.ITALIC)),
                            true
                    );
                }
                player.swing(hand, true);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

    public abstract String getQuestLineId();

    public abstract String getBackground();

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
