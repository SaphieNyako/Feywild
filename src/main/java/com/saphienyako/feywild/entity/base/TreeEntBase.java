package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.intereface.GroundEntity;
import com.saphienyako.feywild.entity.goals.GoToTargetPositionGoal;
import com.saphienyako.feywild.entity.goals.TameCheckingGoal;
import com.saphienyako.feywild.entity.goals.tree_ent_goals.TreeEntMeleeAttackGoal;
import com.saphienyako.feywild.entity.goals.tree_ent_goals.TreeEntMoveAndSoundGoal;
import com.saphienyako.feywild.entity.goals.tree_ent_goals.TreeEntResetTargetGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;

public abstract class TreeEntBase extends FeyBase implements GroundEntity, PlayerRideableJumping {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(TreeEntBase.class, EntityDataSerializers.INT);


    public final AnimationState IDLE_ANIMATION = new AnimationState();
    public final AnimationState WALK_QUICK_ANIMATION = new AnimationState();

    public final AnimationState ATTACK_ANIMATION = new AnimationState();

    public final AnimationState WALK_ANIMATION = new AnimationState();

    private int movingTicks = 0;

    public static final double MIN_MOVING_SPEED_SQR = 1.0E-8;

    private boolean isJumping;
    protected float playerJumpPendingScale;

    private int walkingSoundCooldown = 0;

    private static final SoundEvent[] STORIES = new SoundEvent[] {
            ModSounds.TREE_ENT_STORY_01.get(),
            ModSounds.TREE_ENT_STORY_02.get(),
            ModSounds.TREE_ENT_STORY_03.get(),
            ModSounds.TREE_ENT_STORY_04.get(),
            ModSounds.TREE_ENT_STORY_05.get(),
            ModSounds.TREE_ENT_STORY_06.get()
    };


    public TreeEntBase(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(1, new TreeEntMeleeAttackGoal(this, 2.0D, true));
        this.goalSelector.addGoal(50, new TreeEntMoveAndSoundGoal(this, 1.0D));
        this.targetSelector.addGoal(1,new HurtByTargetGoal(this).setAlertOthers(TreeEntBase.class));
        this.targetSelector.addGoal(2, new TameCheckingGoal(this, false, new NearestAttackableTargetGoal<>(this, Player.class, true)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, false));
        this.targetSelector.addGoal(3, new TreeEntResetTargetGoal<>(this));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 1.0D, 8));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.JUMP_STRENGTH, 1.5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0)
                .add(Attributes.ATTACK_DAMAGE, 15)
                .add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.STEP_HEIGHT, 1.0);

    }

    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE,0);
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

        return false;
    }


    @SuppressWarnings("resource")
    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (level().isClientSide && this.getParticle() != null && random.nextInt(15) == 0) {
            for (int i = 0; i < 4; i++) {
                level().addParticle(
                        this.getParticle(),
                        this.getX() + random.nextDouble() - 0.5,
                        this.getY() + random.nextDouble() * 6.0 - 3.0,
                        this.getZ() + random.nextDouble() - 0.5,
                        0, 0, 0
                );
            }
        }
    }

    @Override
    public void rideTick() {
        super.rideTick();
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > MIN_MOVING_SPEED_SQR;
    }

    private boolean isActuallyMoving() {
        if (isMoving()) {
            movingTicks = 20; // TODO is this needed for such large entity?
        } else {
            movingTicks--;
        }

        return movingTicks > 0;
    }

    private void setupAnimationStates() {
        // ATTACK
        if (getState() == TreeEntBase.State.ATTACK) {
            if (!ATTACK_ANIMATION.isStarted()) {
                ATTACK_ANIMATION.start(this.tickCount);
            }
            IDLE_ANIMATION.stop();
        } else {
            ATTACK_ANIMATION.stop();
        }
        //MOUNTED
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player) {
            if (isActuallyMoving()) {
                if (!WALK_QUICK_ANIMATION.isStarted()) {
                    WALK_QUICK_ANIMATION.start(this.tickCount);
                }
                IDLE_ANIMATION.stop();

            } else {
                if (!IDLE_ANIMATION.isStarted()) {
                    IDLE_ANIMATION.start(this.tickCount);
                }
                WALK_QUICK_ANIMATION.stop();
            }
        }


        if (getState() != TreeEntBase.State.ATTACK) {
            if (isActuallyMoving()) {
                if (!WALK_ANIMATION.isStarted()) {
                    WALK_ANIMATION.start(this.tickCount);
                }
                IDLE_ANIMATION.stop();
                WALK_QUICK_ANIMATION.stop();
            } else {
                if (!IDLE_ANIMATION.isStarted()) {
                    IDLE_ANIMATION.start(this.tickCount);
                }
                WALK_ANIMATION.stop();
                WALK_QUICK_ANIMATION.stop();
            }
        }
    }

    @SuppressWarnings("resource")
    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {

        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        //NAME TAG
        if (superResult == InteractionResult.PASS) {
            //NAME
            if  (player.getItemInHand(hand).getItem() == Items.NAME_TAG) {
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
            } else if (!this.isTamed() || !player.getUUID().equals(this.owner)) {
                if (player instanceof ServerPlayer serverPlayer) {
                    player.displayClientMessage(
                            Component.translatable("message.feywild.pixie_whisper")
                                    .withStyle(ChatFormatting.LIGHT_PURPLE)
                                    .append(Component.translatable("message.feywild.pixie_orb_untamed").withStyle(ChatFormatting.ITALIC)),
                            true
                    );
                }
                player.swing(hand, true);
            } else if (player.getItemInHand(hand).is(Items.BONE_MEAL) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(3);
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
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }

    @Override
    public boolean canRide(@NonNull Entity entity) {
        return entity instanceof Player player
                && this.isTamed()
                && player.getUUID().equals(this.owner);
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity passenger) {
        return passenger instanceof Player;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        return passenger instanceof LivingEntity living ? living : null;
    }
    @NonNull
    @Override
    protected Vec3 getPassengerAttachmentPoint(@NonNull Entity passenger, @NotNull EntityDimensions dimensions, float partialTick) {
        return super.getPassengerAttachmentPoint(passenger, dimensions, partialTick)
                .add(new Vec3(0.0, 0.15 * (double)partialTick, -0.0 * (double)partialTick).yRot(-this.getYRot() * (float) (Math.PI / 180.0)));
    }


    @Override
    protected void removePassenger(@NotNull Entity passenger) {
        super.removePassenger(passenger);
        if (passenger instanceof Player) {
            this.setSummonPos(this.blockPosition());
        }
    }

    @Override
    public boolean canAttack(@Nonnull LivingEntity target) {
        return !this.isVehicle() && super.canAttack(target);
    }



    @Override
    public void travel(@Nonnull Vec3 travelVector) {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            this.setYRot(player.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(player.getXRot() * 0.5F);

            float forward = player.zza;
            float strafe = player.xxa;

            this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            super.travel(new Vec3(strafe, travelVector.y, forward));

            return;
        }

        super.travel(travelVector);
    }

    @Override
    protected void tickRidden(@NonNull Player player,@NonNull Vec3 movementInput) {
        super.tickRidden(player, movementInput);

        if (walkingSoundCooldown > 0) {
            walkingSoundCooldown--;
        }

        if (this.isControlledByLocalInstance()) {
            if (this.onGround()) {
                this.setIsJumping(false);

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
                    this.executeRidersJump(this.playerJumpPendingScale, movementInput);
                }

                this.playerJumpPendingScale = 0.0F;
            }
        }
    }

    protected void executeRidersJump(float jumpScale, Vec3 movementInput) {
        double jumpStrength = this.getAttributeValue(Attributes.JUMP_STRENGTH) * jumpScale;
        Vec3 motion = this.getDeltaMovement();
        this.setDeltaMovement(motion.x, jumpStrength, motion.z);
        this.setIsJumping(true);
        this.hasImpulse = true;

        if (movementInput.z > 0.0) {
            float f = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
            float f1 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
            this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f * jumpScale, 0.0, 0.4F * f1 * jumpScale));
        }
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        if (this.canJump()) {
            if (jumpPower < 0) jumpPower = 0;
            this.playerJumpPendingScale = 0.4F + 0.4F * jumpPower / 90.0F;
            this.setIsJumping(true); // mark as jumping
        }
    }

    @Override
    public void handleStartJump(int p_21695_) {
    }

    @Override
    public boolean canJump() {
        return this.isVehicle() && this.getControllingPassenger() instanceof Player;
    }


    @Override
    public void handleStopJump() {
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public void setIsJumping(boolean jumping) {
        this.isJumping = jumping;
    }


    //SOUND


    @Override
    public void playStepSound(@Nonnull BlockPos pos,@Nonnull BlockState state) {
        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {
            if (walkingSoundCooldown <= 0) {
                this.playSound(ModSounds.TREE_ENT_WALKING.get(), 0.1f, 1.0f);
                walkingSoundCooldown = 88; // ~4 seconds
            }
        }
    }

    public SoundEvent getWalkingSound() {return ModSounds.TREE_ENT_WALKING.get();}
    public SoundEvent getAttackingSound() {return ModSounds.TREE_ENT_ATTACKING.get();}
    public SoundEvent getBlessingSound() {return ModSounds.TREE_ENT_BLESSING.get();}

    @Override
    public SoundEvent getCookieSound() {
        return null;
    }

    @Override
    public SoundEvent getNameSound() {
        return ModSounds.TREE_ENT_NAME.get();
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.TREE_ENT_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.TREE_ENT_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.TREE_ENT_MOUNT.get();
    }

    @Override
    public SoundEvent getStaySound() {
        return ModSounds.TREE_ENT_STAY.get();
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return null;
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source) {
        return ModSounds.TREE_ENT_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TREE_ENT_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if(this.isVehicle() && this.getControllingPassenger() instanceof Player) {
            Random random = new Random();
                return STORIES[random.nextInt(STORIES.length)]; //TODO make goal if vehicle tell a story and tell it on player side.
        } else
            return ModSounds.TREE_ENT_AMBIANCE.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 600; //YES THIS IS A THING T_T
    }

    public abstract MobEffect getEffect();

    public TreeEntBase.State getState() {
        TreeEntBase.State[] states = TreeEntBase.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(TreeEntBase.State state) {
        this.entityData.set(STATE, state.ordinal());
    }
    

    public enum State {
        IDLE, ATTACK, WALK, QUICK_WALK
    }
}
