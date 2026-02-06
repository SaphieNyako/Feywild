package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.data.BellsnickelItems;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.GroundEntity;
import com.saphienyako.feywild.entity.base.intereface.ITradeable;
import com.saphienyako.feywild.entity.goals.GroundIronPanicGoal;
import com.saphienyako.feywild.entity.goals.GroundPanicGoal;
import com.saphienyako.feywild.entity.goals.SingGoal;
import com.saphienyako.feywild.entity.goals.TradeForGemsGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Random;

public class BellsnickelEntity extends FeyBase implements GroundEntity, ITradeable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(BellsnickelEntity.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState TRADE_ANIMATION = new AnimationState();

    public final AnimationState POSE_ANIMATION = new AnimationState();

    public final AnimationState WALK_ANIMATION = new AnimationState();

    private int movingTicks = 0;

    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    protected BellsnickelEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.entityData.set(VARIANT, BellsnickelEntity.BellsnickelVariant.DEFAULT.ordinal());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerGroundGoals(this);
        this.goalSelector.addGoal(0, new GroundPanicGoal(this));
        this.goalSelector.addGoal(1, new GroundIronPanicGoal(this, this.level(), 0.25, 6));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(5, new TradeForGemsGoal(this));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.10)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE,0);
        builder.define(VARIANT, VARIANT.id());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("BellsnickelVariant", this.entityData.get(VARIANT));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("BellsnickelVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("BellsnickelVariant"));
        }
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity living && !this.isTamed()) {
            living.setTicksFrozen(living.getTicksRequiredToFreeze());
            living.setTicksFrozen(living.getTicksFrozen() + 60);
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 2));
            PacketDistributor.sendToPlayersTrackingEntity(
                    this,
                    new ParticleMessage(
                            ParticleMessage.Particles.DANDELION_FLUFF,
                            this.blockPosition().above()
                    )
            );
        }

        return super.isDamageSourceBlocked(damageSource);
    }

    @SuppressWarnings("resource")
    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (level().isClientSide && this.getParticle() != null && random.nextInt(25) == 0) {
                level().addParticle(this.getParticle(),
                        this.getX() + (Math.random() - 0.5),
                        this.getY() + 1 + (Math.random() - 0.5),
                        this.getZ() + (Math.random() - 0.5),
                        0, 0, 0
                );

        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > MIN_MOVING_SPEED_SQR;
    }

    private boolean isActuallyMoving() {
        if (isMoving()) {
            movingTicks = 5;
        } else {
            movingTicks = Math.max(0, movingTicks - 1);
        }
        return movingTicks > 0;
    }

    private void setupAnimationStates() {
        // POSE
        if (getState() == BellsnickelEntity.State.POSE) {
            if (!POSE_ANIMATION.isStarted()) {
                POSE_ANIMATION.start(this.tickCount);
            }
        } else {
            POSE_ANIMATION.stop();
        }

        // SING
        if (getState() == State.TRADE) {
            if (!TRADE_ANIMATION.isStarted()) {
                TRADE_ANIMATION.start(this.tickCount);
            }
        } else {
            TRADE_ANIMATION.stop();
        }


        if (getState() != State.TRADE) {
            if (isActuallyMoving()) {
                if (!WALK_ANIMATION.isStarted()) {
                    WALK_ANIMATION.start(this.tickCount);
                }
                IDLE_ANIMATION.stop();
            } else {
                if (!IDLE_ANIMATION.isStarted()) {
                    IDLE_ANIMATION.start(this.tickCount);
                }
                WALK_ANIMATION.stop();
            }
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
                    if (random.nextInt(6) == 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.FEY_DUST.get()));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                        if(FeywildConfig.voicesActive) {
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
                    if(FeywildConfig.voicesActive && this.getVoiceActive()) {
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
                PacketDistributor.sendToPlayer(
                        (ServerPlayer)player,
                        new OpenMenuMessage(
                                this.getId(),
                                this.getAlignment(),
                                this.getFollowingPlayer(),
                                this.blockPosition(),
                                this.getAbilityActive(),
                                this.getVoiceActive()
                        )
                );
                player.swing(hand, true);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ParticleTypes.SNOWFLAKE;
    }


    @Override
    public Alignment getAlignment() {
        return Alignment.WINTER;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_BELLSNICKEL.get();
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

    @Override
    public SoundEvent getTradeSound() {
        return null;
    }

    @Override
    public ItemStack getTradeItem() {
        return ModItems.FEY_GEM.toStack();
    }

    @Override
    public boolean isTradeItem(ItemStack stack) {
        return stack.is(ModItems.FEY_GEM);
    }

    @Override
    public ItemStack getTradeResult() {
        List<ItemStack> items = BellsnickelItems.bellsnickelItems();
        if (items.isEmpty()) return ItemStack.EMPTY;

        Random random = new Random();
        int index = random.nextInt(items.size());
        return items.get(index).copy();
    }

    public BellsnickelEntity.State getState() {
        BellsnickelEntity.State[] states = BellsnickelEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }


    public void setState(BellsnickelEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }


    public BellsnickelEntity.BellsnickelVariant getVariant() {
        return BellsnickelEntity.BellsnickelVariant.values()[this.entityData.get(VARIANT)];
    }

    public void setVariant(BellsnickelEntity.BellsnickelVariant variant) {this.entityData.set(VARIANT, variant.ordinal());}

    public enum State {
        IDLE, POSE, WALK, TRADE
    }

    public enum BellsnickelVariant {
        DEFAULT, RED_CAP, LORE_MASTER
    }
}
