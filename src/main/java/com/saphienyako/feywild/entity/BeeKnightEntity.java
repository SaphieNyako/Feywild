package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;

public class BeeKnightEntity extends FlyingFeyBase {
    //ATTACKS
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BeeKnightEntity.class, EntityDataSerializers.INT);

    public final AnimationState SIT_ANIMATION = new AnimationState();
    public final AnimationState ATTACK_ANIMATION = new AnimationState();

    public boolean isBeingRemovedTogether = false;

    protected BeeKnightEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE,0);
    }



    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // If not riding a BeeMount, remove itself
            if (!(this.getVehicle() instanceof BeeMountEntity)) {
                this.remove(RemovalReason.DISCARDED);
            }
        }

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (getState() == State.ATTACK) {
            if (!ATTACK_ANIMATION.isStarted()) {
                ATTACK_ANIMATION.start(this.tickCount);
            }
        } else {
            ATTACK_ANIMATION.stop();
        }

        SIT_ANIMATION.start(this.tickCount);
    }

    @SuppressWarnings("resource")
    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if (superResult == InteractionResult.PASS) {

            //GIVE COOKIE, HEAL
            if (player.getItemInHand(hand).is(Items.COOKIE) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(3);
                if (!this.isTamed() && player instanceof ServerPlayer serverPlayer && this.owner == null) {
                    Random random = new Random();
                    if (random.nextInt(3) == 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.FEY_DUST.get()));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                   /*     if(FeywildConfig.voicesActive) {
                            serverPlayer.playNotifySound(
                                    this.getCookieSound(),
                                    SoundSource.NEUTRAL,
                                    1.0F,
                                    1.0F
                            );
                        }

                     */
                        this.discard();
                        player.sendSystemMessage(getFeyCookieMessage());
                    }
                }
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                if (!level().isClientSide) {
                    PacketDistributor.sendToPlayersTrackingEntity(
                            this,
                            new ParticleMessage(
                                    ParticleMessage.Particles.FEY_HEART,
                                    this.getOnPos()
                            )
                    );
                }

                player.swing(hand, true);

                //NAME TAG
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level().isClientSide) {
                    player.sendSystemMessage(getFeyNameMessage());
                    if (FeywildConfig.voicesActive && this.getVoiceActive()) {
                        player.playNotifySound(
                                this.getNameSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
                    }
                }

                //PIXIE ORB OPENS BEE MOUNT MENU
            } else if (player.getItemInHand(hand).getItem() == ModItems.PIXIE_ORB.get()) {
                BeeMountEntity mount = this.getMount();

                if (mount != null) {
                    return mount.interactAt(player, hitVec, hand);
                }
                player.swing(hand, true);

            } else if (!this.isTamed()) {
                BeeMountEntity mount = this.getMount();

                if (mount != null) {
                    return mount.interactAt(player, hitVec, hand);
                }
                player.swing(hand, true);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }

    public BeeMountEntity getMount() {
        if (this.getVehicle() instanceof BeeMountEntity mount) {
            return mount;
        }
        return null;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.level().isClientSide && !this.isBeingRemovedTogether) {
            this.isBeingRemovedTogether = true;

            BeeMountEntity mount = getMount();
            if (mount != null && !mount.isRemoved()) {
                mount.isBeingRemovedTogether = true;
                mount.remove(reason);
            }
        }

        super.remove(reason);
    }


    @Override
    public Alignment getAlignment() {
        return Alignment.SUMMER;
    }

    @Override
    public ItemLike getDismissItem() {
        return null;
    }

    @Override
    public SoundEvent getCookieSound() {
        return null;
    }

    @Override
    public SoundEvent getNameSound() {
        return null;
    }

    @Override
    public SoundEvent getSummonSound() {
        return null;
    }

    @Override
    public SoundEvent getDismissSound() {
        return null;
    }

    @Override
    public SoundEvent getFollowSound() {
        return null;
    }

    @Override
    public SoundEvent getStaySound() {
        return null;
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return null;
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return null;
    }


    public BeeKnightEntity.State getState() {
        BeeKnightEntity.State[] states = BeeKnightEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(BeeKnightEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public enum State {
        SIT, ATTACK
    }

}
