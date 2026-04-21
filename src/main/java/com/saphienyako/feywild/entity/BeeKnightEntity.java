package com.saphienyako.feywild.entity;


import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.data.BeeKnightItems;
import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import com.saphienyako.feywild.entity.base.intereface.ITradeable;
import com.saphienyako.feywild.entity.goals.AbilityCheckingGoal;
import com.saphienyako.feywild.entity.goals.TradeForGemsGoal;
import com.saphienyako.feywild.entity.goals.guardian_goals.BeeKnightMeleeAttackGoal;
import com.saphienyako.feywild.entity.goals.guardian_goals.BeeKnightResetTargetGoal;
import com.saphienyako.feywild.entity.goals.guardian_goals.BeeMountMoveTowardsBeeKnightTargetGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Random;

public class BeeKnightEntity extends FlyingFeyBase implements ITradeable {
    //ATTACKS
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BeeKnightEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Boolean>ON_DUTY = SynchedEntityData.defineId(BeeKnightEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState SIT_ANIMATION = new AnimationState();
    public final AnimationState ATTACK_ANIMATION = new AnimationState();

    public final AnimationState WING_ANIMATION = new AnimationState();

    public int animationTimeout = 0;

    public boolean isBeingRemovedTogether = false;

    protected BeeKnightEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {

        this.goalSelector.addGoal(1, new BeeKnightMeleeAttackGoal(this, 2.0D, true));
        this.targetSelector.addGoal(1,new HurtByTargetGoal(this).setAlertOthers(BeeKnightEntity.class));
        this.targetSelector.addGoal(2, new AbilityCheckingGoal(this, true, new NearestAttackableTargetGoal<>(this, Monster.class, false)));
        this.targetSelector.addGoal(3, new BeeKnightResetTargetGoal<>(this));
        this.goalSelector.addGoal(5, new BeeMountMoveTowardsBeeKnightTargetGoal(this, 4D, 4));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new TradeForGemsGoal(this));
        this.getNavigation().setCanFloat(true);
    }


    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ARMOR, 0.0)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE,0);
        this.entityData.define(ON_DUTY, true);
    }

    public void stopBeingAngry() {
        this.setLastHurtByMob(null);
        this.setTarget(null);
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        if (this.isBlocking() && !damageSource.is(DamageTypeTags.BYPASSES_SHIELD)) {
            Vec3 vec32 = damageSource.getSourcePosition();
            if (vec32 != null) {
                Vec3 vec3 = this.calculateViewVector(0.0F, this.getYHeadRot());
                Vec3 vec31 = vec32.vectorTo(this.position());
                vec31 = new Vec3(vec31.x, 0.0, vec31.z).normalize();
                return vec31.dot(vec3) < 0.0;
            }
        }
        Entity attacker = damageSource.getEntity();
        if(this.isTamed() && attacker instanceof Player player && player == getOwningPlayer()) {
            ItemStack held = player.getMainHandItem();
            if (!isIronTool(held)) {
                return true;
            }
        }

        return false;
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

        if (!WING_ANIMATION.isStarted()) {
            WING_ANIMATION.start(this.tickCount);
        }

        if (!SIT_ANIMATION.isStarted()) {
            SIT_ANIMATION.start(this.tickCount);
        }

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
                        if(ModConfig.COMMON.voice_active.get()) {
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
                if (!level().isClientSide) {
                    FeywildNetwork.sendParticles(this.level(), ParticleMessage.Type.FEY_HEART, this.getOnPos());
                }

                player.swing(hand, true);

                //NAME TAG
            } else if (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
                setCustomName(player.getItemInHand(hand).getHoverName().copy());
                setCustomNameVisible(true);
                if (!level().isClientSide) {
                    player.sendSystemMessage(getFeyNameMessage());
                    if(ModConfig.COMMON.voice_active.get() && this.getVoiceActive()) {
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
    public Boolean getAbilityActive() {
        if(getMount() == null) {return false;}
        else return getMount().getAbilityActive();
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
        return ModSounds.BEE_KNIGHT_COOKIE.get();
    }

    @Override
    public SoundEvent getNameSound() {
        return ModSounds.BEE_KNIGHT_NAME.get();
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.BEE_KNIGHT_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.BEE_KNIGHT_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.BEE_KNIGHT_FOLLOW.get();
    }

    @Override
    public SoundEvent getStaySound() {
        return ModSounds.BEE_KNIGHT_STAY.get();
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.BEE_KNIGHT_PROTECT.get();
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.BEE_KNIGHT_GUARD.get();
    }

    public SoundEvent getAttackSound() {
        Random random = new Random();
        int i = random.nextInt(5);

        return switch (i) {
            case 0 -> ModSounds.BEE_KNIGHT_ATTACK_01.get();
            case 1 -> ModSounds.BEE_KNIGHT_ATTACK_02.get();
            case 2 -> ModSounds.BEE_KNIGHT_ATTACK_03.get();
            default -> SoundEvents.BEE_STING;
        };
    }


    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.BEE_KNIGHT_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BEE_KNIGHT_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BEE_KNIGHT_AMBIANCE.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 800;
    }

    @Override
    public SoundEvent getTradeSound() {
        return ModSounds.BEE_KNIGHT_TRADE.get();
    }

    @Override
    public ItemStack getTradeItem() {
        return ModItems.FEY_GEM.get().getDefaultInstance();
    }

    @Override
    public boolean isTradeItem(ItemStack stack) {
        return stack.is(ModItems.FEY_GEM.get());
    }

    @Override
    public ItemStack getTradeResult() {
        List<ItemStack> items = BeeKnightItems.beeKnightItems();
        if (items.isEmpty()) return ItemStack.EMPTY;

        Random random = new Random();
        int index = random.nextInt(items.size());
        return items.get(index).copy();
    }


    public State getState() {
        State[] states = State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public enum State {
        IDLE, ATTACK
    }
}
