package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.data.ShroomlingItems;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.GroundEntity;
import com.saphienyako.feywild.entity.base.intereface.ITradeable;
import com.saphienyako.feywild.entity.goals.*;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
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
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import java.util.Map;
import java.util.Random;

public class ShroomlingEntity extends FeyBase implements GroundEntity, ITradeable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(ShroomlingEntity.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ShroomlingEntity.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState SNEEZE_ANIMATION = new AnimationState();

    public final AnimationState POSE_ANIMATION = new AnimationState();

    public final AnimationState WALK_ANIMATION = new AnimationState();

    public final AnimationState WAVE_ANIMATION = new AnimationState();

    private int movingTicks = 0;

    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    public static final Map<Item, ShroomlingEntity.ShroomlingVariant> MUSHROOM_VARIANTS = Map.ofEntries(
            Map.entry(Items.RED_MUSHROOM, ShroomlingVariant.DEFAULT),
            Map.entry(Items.BROWN_MUSHROOM, ShroomlingVariant.BROWN),
            Map.entry(ModBlocks.ORANGE_MUSHROOM.asItem(), ShroomlingVariant.ORANGE),
            Map.entry(ModBlocks.YELLOW_MUSHROOM.asItem(), ShroomlingVariant.YELLOW),
            Map.entry(ModBlocks.GREEN_MUSHROOM.asItem(), ShroomlingVariant.GREEN),
            Map.entry(ModBlocks.LIGHT_BLUE_MUSHROOM.asItem(), ShroomlingVariant.LIGHT_BLUE),
            Map.entry(ModBlocks.BLUE_MUSHROOM.asItem(), ShroomlingVariant.BLUE),
            Map.entry(ModBlocks.PURPLE_MUSHROOM.asItem(), ShroomlingVariant.PURPLE),
            Map.entry(ModBlocks.PINK_MUSHROOM.asItem(), ShroomlingEntity.ShroomlingVariant.PINK)
    );


    protected ShroomlingEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.entityData.set(VARIANT, ShroomlingEntity.ShroomlingVariant.DEFAULT.ordinal());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerGroundGoals(this);
        this.goalSelector.addGoal(0, new GroundPanicGoal(this));
        this.goalSelector.addGoal(1, new GroundIronPanicGoal(this, this.level(), 0.25, 6));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(25, new WaveGoal(this));
        this.goalSelector.addGoal(20, new SneezeGoal(this));
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
        nbt.putInt("ShroomlingVariant", this.entityData.get(VARIANT));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("ShroomlingVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("ShroomlingVariant"));
        }
    }


    @SuppressWarnings("resource")
    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > MIN_MOVING_SPEED_SQR;
    }

    private boolean isActuallyMoving() {
        if (isMoving()) {
            movingTicks = 20; // stay "moving" for 40 ticks
        } else {
            movingTicks--;
        }

        return movingTicks > 0;
    }

    private void setupAnimationStates() {

        // SNEEZE
        if (getState() == State.SNEEZE && getState() != State.WAVE) {
            if (!SNEEZE_ANIMATION.isStarted()) {
                SNEEZE_ANIMATION.start(this.tickCount);
            }
        } else {
            SNEEZE_ANIMATION.stop();
        }

        // POSE
        if (getState() == State.POSE) {
            if (!POSE_ANIMATION.isStarted()) {
                POSE_ANIMATION.start(this.tickCount);
            }
        } else {
            POSE_ANIMATION.stop();
        }

        // WAVE
        if (getState() == State.WAVE && getState() != State.SNEEZE) {
            if (!WAVE_ANIMATION.isStarted()) {
                WAVE_ANIMATION.start(this.tickCount);
            }
        } else {
            WAVE_ANIMATION.stop();
        }


        if (getState() != State.WAVE && getState() != State.SNEEZE ) {
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
                if (!level().isClientSide) {
                    player.sendSystemMessage(getFeyNameMessage());
                    if(FeywildConfig.voicesActive && this.getVoiceActive()) {
                        player.playNotifySound(
                                this.getCookieSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
                    }
                }
                if (!this.isTamed() && player instanceof ServerPlayer serverPlayer && this.owner == null) {
                    Random random = new Random();
                    if (random.nextInt(6) == 0) {
                        this.spawnAtLocation(new ItemStack(ModItems.FEY_DUST.get()));
                        this.playSound(SoundEvents.ENDERMAN_TELEPORT);
                        if(FeywildConfig.voicesActive) {
                            serverPlayer.playNotifySound(
                                    this.getDismissSound(),
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

                //MUSHROOM VARIANT
            } else if (MUSHROOM_VARIANTS.containsKey(player.getItemInHand(hand).getItem())) {
                ShroomlingEntity.ShroomlingVariant variant = MUSHROOM_VARIANTS.get(player.getItemInHand(hand).getItem());
                this.setVariant(variant);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                if (!level().isClientSide) {
                    PacketDistributor.sendToPlayersTrackingEntity(
                            this,
                            new ParticleMessage(
                                    ParticleMessage.Particles.DANDELION_FLUFF,
                                    this.getOnPos().above()
                            )
                    );
                    player.playNotifySound(
                            SoundEvents.COMPOSTER_EMPTY,
                            SoundSource.NEUTRAL,
                            1.0F,
                            1.0F
                    );
                }
                player.swing(hand, true);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }

    //TODO check below

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return null;
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.AUTUMN;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_SHROOMLING;
    }

    public SoundEvent getWaveSound() {
        return ModSounds.SHROOMLING_WAVE.get();
    }

    public SoundEvent getSneezeSound() {
        return ModSounds.SHROOMLING_SNEEZE.get();
    }

    @Override
    public SoundEvent getCookieSound() {
        return ModSounds.SHROOMLING_COOKIE.get();
    }

    @Override
    public SoundEvent getNameSound() {
        return ModSounds.SHROOMLING_NAME.get();
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.SHROOMLING_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.SHROOMLING_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.SHROOMLING_FOLLOW.get();
    }

    @Override
    public SoundEvent getStaySound() {
        return ModSounds.SHROOMLING_STAY.get();
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.SHROOMLING_ABILITY_ON.get();
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.SHROOMLING_ABILITY_OFF.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.SHROOMLING_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SHROOMLING_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        Random random = new Random();
        if (random.nextFloat() < 0.1f) {
           if(random.nextInt(2) == 0) return ModSounds.SHROOMLING_AMBIANCE_02.get();
           else return ModSounds.SHROOMLING_AMBIANCE_01.get();
        } else return null;
    }


    public State getState() {
        State[] states = State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(State state) {
        this.entityData.set(STATE, state.ordinal());
    }


    public ShroomlingEntity.ShroomlingVariant getVariant() {
        return ShroomlingEntity.ShroomlingVariant.values()[this.entityData.get(VARIANT)];
    }

    public void setVariant(ShroomlingEntity.ShroomlingVariant variant) {this.entityData.set(VARIANT, variant.ordinal());}

    @Override
    public SoundEvent getTradeSound() {
        return ModSounds.SHROOMLING_TRADE.get();
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
        List<ItemStack> items = ShroomlingItems.shroomlingItems();
        if (items.isEmpty()) return ItemStack.EMPTY;

        Random random = new Random();
        int index = random.nextInt(items.size());
        return items.get(index).copy();
    }


    public enum State {
        IDLE, POSE, WALK, WAVE, SNEEZE
    }

    public enum ShroomlingVariant {
        DEFAULT, BROWN, ORANGE, YELLOW, GREEN, LIGHT_BLUE, BLUE, PURPLE, PINK
    }
}
