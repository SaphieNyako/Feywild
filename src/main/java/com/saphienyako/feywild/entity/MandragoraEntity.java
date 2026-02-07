package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.data.MandragoraItems;
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
import com.saphienyako.feywild.sound.ModSounds;
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

public class MandragoraEntity extends FeyBase implements GroundEntity, ITradeable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(MandragoraEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(MandragoraEntity.class, EntityDataSerializers.INT);

    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState SING_ANIMATION = new AnimationState();

    public final AnimationState POSE_ANIMATION = new AnimationState();

    public final AnimationState WALK_ANIMATION = new AnimationState();
    private int movingTicks = 0;

    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    public static final Map<Item, MandragoraVariant> FLOWER_VARIANTS = Map.ofEntries(
            Map.entry(Items.ALLIUM, MandragoraVariant.ALLIUM),
            Map.entry(Items.AZURE_BLUET, MandragoraVariant.AZURE_BLUET),
            Map.entry(Items.BLUE_ORCHID, MandragoraVariant.BLUE_ORCHID),
            Map.entry(Items.CORNFLOWER, MandragoraVariant.CORNFLOWER),
            Map.entry(Items.DANDELION, MandragoraVariant.DANDELION),
            Map.entry(Items.LILY_OF_THE_VALLEY, MandragoraVariant.LILY_OF_THE_VALLEY),
            Map.entry(Items.ORANGE_TULIP, MandragoraVariant.ORANGE_TULIP),
            Map.entry(Items.PINK_TULIP, MandragoraVariant.PINK_TULIP),
            Map.entry(Items.WHITE_TULIP, MandragoraVariant.WHITE_TULIP),
            Map.entry(Items.RED_TULIP, MandragoraVariant.RED_TULIP),
            Map.entry(Items.OXEYE_DAISY, MandragoraVariant.OXEYE_DAISY),
            Map.entry(Items.POPPY, MandragoraVariant.POPPY),
            Map.entry(Items.WITHER_ROSE, MandragoraVariant.WITHER_ROSE),
            Map.entry(Items.TORCHFLOWER, MandragoraVariant.TORCHFLOWER)
    );


    protected MandragoraEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.entityData.set(VARIANT, MandragoraVariant.DEFAULT.ordinal());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.registerGroundGoals(this);
        this.goalSelector.addGoal(0, new GroundPanicGoal(this));
        this.goalSelector.addGoal(1, new GroundIronPanicGoal(this, this.level(), 0.25, 6));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(20, new SingGoal(this));
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
        builder.define(VARIANT, MandragoraVariant.DEFAULT.ordinal());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("MandragoraVariant", this.entityData.get(VARIANT));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("MandragoraVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("MandragoraVariant"));
        }
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity living && !this.isTamed()) {
            living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 2 * 60, 0));
            PacketDistributor.sendToPlayersTrackingEntity(
                    this,
                    new ParticleMessage(
                            ParticleMessage.Particles.SHROOMLING_SNEEZE,
                            this.blockPosition().above()
                    ));
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
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.WITHER) {
           if(this.getVariant() == MandragoraVariant.WITHER_ROSE) return false;
        }
        return super.canBeAffected(effect);
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
        if (getState() == MandragoraEntity.State.POSE) {
            if (!POSE_ANIMATION.isStarted()) {
                POSE_ANIMATION.start(this.tickCount);
            }
        } else {
            POSE_ANIMATION.stop();
        }

        // SING
        if (getState() == MandragoraEntity.State.SING) {
            if (!SING_ANIMATION.isStarted()) {
                SING_ANIMATION.start(this.tickCount);
            }
        } else {
            SING_ANIMATION.stop();
        }


        if (getState() != MandragoraEntity.State.SING) {
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

                //GIVE FLOWER
            } else if (FLOWER_VARIANTS.containsKey(player.getItemInHand(hand).getItem())) {
                MandragoraVariant variant = FLOWER_VARIANTS.get(player.getItemInHand(hand).getItem());
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



    @Override
    public Alignment getAlignment() {
        return Alignment.SPRING;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_MANDRAGORA;
    }

    @Override
    public SoundEvent getCookieSound() {
        return ModSounds.MANDRAGORA_COOKIE.get();
    }

    @Override
    public SoundEvent getNameSound() {
        return ModSounds.MANDRAGORA_NAME.get();
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.MANDRAGORA_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.MANDRAGORA_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.MANDRAGORA_FOLLOW.get();
    }

    @Override
    public SoundEvent getStaySound() {
        return ModSounds.MANDRAGORA_STAY.get();
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.MANDRAGORA_ABILITY_ON.get();
    }

    public SoundEvent getSingSound() {return ModSounds.MANDRAGORA_SING.get();}

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.MANDRAGORA_ABILITY_OFF.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source) {
        return ModSounds.MANDRAGORA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MANDRAGORA_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        Random random = new Random();
        if (random.nextFloat() < 0.1f) {
            if(random.nextInt(2) == 0) return ModSounds.MANDRAGORA_AMBIANCE_02.get();
            else return ModSounds.MANDRAGORA_AMBIANCE_01.get();
        } else return null;
    }

    public MandragoraEntity.State getState() {
        MandragoraEntity.State[] states = MandragoraEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }


    public void setState(MandragoraEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }


    public MandragoraVariant getVariant() {
        return MandragoraVariant.values()[this.entityData.get(VARIANT)];
    }

    public void setVariant(MandragoraEntity.MandragoraVariant variant) {this.entityData.set(VARIANT, variant.ordinal());}

    @Override
    public SoundEvent getTradeSound() {
        return ModSounds.MANDRAGORA_TRADE.get();
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
        List<ItemStack> items = MandragoraItems.mandragoraItems();
        if (items.isEmpty()) return ItemStack.EMPTY;

        Random random = new Random();
        int index = random.nextInt(items.size());
        return items.get(index).copy();
    }

    public enum State {
        IDLE, POSE, WALK, SING
    }

    public enum MandragoraVariant {
        //Closed/Open eyed blossom in 1.21.4+
        //TorchFlower 1.20+
        DEFAULT, ALLIUM, AZURE_BLUET, BLUE_ORCHID, CORNFLOWER, DANDELION, LILY_OF_THE_VALLEY, ORANGE_TULIP, PINK_TULIP, WHITE_TULIP, RED_TULIP, OXEYE_DAISY, POPPY, WITHER_ROSE, TORCHFLOWER
    }
}
