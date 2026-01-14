package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.GroundEntity;
import com.saphienyako.feywild.entity.goals.*;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
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

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Map;
import java.util.Random;

public class MandragoraEntity extends FeyBase implements GroundEntity {

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
        //TODO add SING goal
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
        nbt.putInt("MandragoraVariant", this.entityData.get(VARIANT));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("MandragoraVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("MandragoraVariant"));
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

    public enum State {
        IDLE, POSE, WALK, SING
    }

    public enum MandragoraVariant {
        //Closed/Open eyed blossom in 1.21.4+
        //TorchFlower 1.20+
        DEFAULT, ALLIUM, AZURE_BLUET, BLUE_ORCHID, CORNFLOWER, DANDELION, LILY_OF_THE_VALLEY, ORANGE_TULIP, PINK_TULIP, WHITE_TULIP, RED_TULIP, OXEYE_DAISY, POPPY, WITHER_ROSE, TORCHFLOWER
    }
}
