package com.feywild.feywild.entity.util;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.goals.FeyWildPanicGoal;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.entity.goals.TameCheckingGoal;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.network.quest.OpenQuestDisplaySerializer;
import com.feywild.feywild.network.quest.OpenQuestSelectionSerializer;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.FeyGiftTask;
import com.feywild.feywild.quest.util.AlignmentStack;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class FeyEntity extends CreatureEntity implements ITameable, IAnimatable {

    public static final DataParameter<Boolean> CASTING = EntityDataManager.defineId(FeyEntity.class, DataSerializers.BOOLEAN);
    
    public final Alignment alignment;
    @Nullable
    private BlockPos currentTargetPos;
    private boolean isTamed;
    @Nullable
    private UUID owner;
    
    private final AnimationFactory factory = new AnimationFactory(this);

    protected FeyEntity(EntityType<? extends FeyEntity> type, Alignment alignment, World world) {
        super(type, world);
        this.alignment = alignment;
        this.noCulling = true;
        this.moveControl = new FlyingMovementController(this, 4, true);
    }

    /* ATTRIBUTES */
    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2);
    }

    public static boolean canSpawn(EntityType<? extends FeyEntity> entity, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(world.getBlockState(pos.below()).getBlock());
    }

    @Nullable
    public BlockPos getCurrentTargetPos() {
        return currentTargetPos;
    }

    public void setCurrentTargetPos(@Nullable BlockPos currentTargetPos) {
        this.currentTargetPos = currentTargetPos == null ? null : currentTargetPos.immutable();
    }

    @Override
    public boolean isTamed() {
        return isTamed;
    }

    public void setTamed(boolean tamed) {
        this.isTamed = tamed;
    }

    @Nullable
    public PlayerEntity getOwner() {
        return owner == null ? null : this.level.getPlayerByUUID(this.owner);
    }
    
    @Nullable
    public UUID getOwnerId() {
        return owner;
    }

    public void setOwner(@Nullable PlayerEntity owner) {
        setOwner(owner == null ? null : owner.getUUID());
    }

    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
    }

    public Vector3d getCurrentPointOfInterest() {
        if (!this.isTamed()) {
            return null;
        } else if (this.currentTargetPos != null) {
            return new Vector3d(this.currentTargetPos.getX() + 0.5, this.currentTargetPos.getY() + 0.5, this.currentTargetPos.getZ() + 0.5);
        } else {
            PlayerEntity player = this.getOwner();
            if (player != null) {
                return player.position();
            }
        }
        return null;
    }
    
    public boolean isCasting() {
        return this.entityData.get(CASTING);
    }
    
    public void setCasting(boolean casting) {
        this.entityData.set(CASTING, casting);
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CASTING, false);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(50, new FeyWildPanicGoal(this, 0.003, 13));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(30, new LookAtGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, 6, 1.5f));
        this.goalSelector.addGoal(30, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(10, new TameCheckingGoal(this, true, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false)));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Tamed", this.isTamed);
        if (this.currentTargetPos != null) {
            NBTX.putPos(nbt, "CurrentTarget", this.currentTargetPos);
        }
        if (this.owner != null) {
            NBTX.putPos(nbt, "Owner", this.currentTargetPos);
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.isTamed = nbt.getBoolean("Tamed");
        this.currentTargetPos = NBTX.getPos(nbt, "CurrentTarget", null);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
    }

    @Nonnull
    @Override
    protected PathNavigator createNavigation(@Nonnull World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    @Override
    public void travel(@Nonnull Vector3d position) {
        if (this.isInWater()) {
            this.moveRelative(0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
        } else if (this.isInLava()) {
            this.moveRelative(0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
        } else {
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1, this.getZ());
            float slipperiness = 0.91f;
            if (this.onGround) {
                slipperiness = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            float groundMovementModifier = 0.16277137f / (slipperiness * slipperiness * slipperiness);
            slipperiness = 0.91f;
            if (this.onGround) {
                slipperiness = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1f * groundMovementModifier : 0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(slipperiness));
        }

        this.animationSpeedOld = this.animationSpeed;
        double dx = this.getX() - this.xo;
        double dz = this.getZ() - this.zo;
        float scaledLastHorizontalMotion = MathHelper.sqrt(dx * dx + dz * dz) * 4;
        if (scaledLastHorizontalMotion > 1) {
            scaledLastHorizontalMotion = 1;
        }
        this.animationSpeed += (scaledLastHorizontalMotion - this.animationSpeed) * 0.4;
        this.animationPosition += this.animationSpeed;
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected int getExperienceReward(@Nonnull PlayerEntity player) {
        return 0;
    }

    @Override
    public boolean canBeLeashed(@Nonnull PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity entityIn) {
        return false;
    }

    @Override
    protected float getVoicePitch() {
        return 1;
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d hitVec, @Nonnull Hand hand) {
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                if (this.owner != null && this.owner.equals(player.getUUID())) {
                    if (this.getCurrentTargetPos() == null) {
                        this.setCurrentTargetPos(this.blockPosition());
                        player.sendMessage(new TranslationTextComponent("message.feywild." + alignment.id + "_fey_stay").append(new TranslationTextComponent("message.feywild.fey_stay").withStyle(TextFormatting.ITALIC)), player.getUUID());
                    } else {
                        this.setCurrentTargetPos(null);
                        player.sendMessage(new TranslationTextComponent("message.feywild." + alignment.id + "_fey_follow").append(new TranslationTextComponent("message.feywild.fey_follow").withStyle(TextFormatting.ITALIC)), player.getUUID());
                    }
                }
            } else if (player instanceof ServerPlayerEntity && tryAcceptGift((ServerPlayerEntity) player, hand)) {
                player.swing(hand, true);
            } else if (player.getItemInHand(hand).getItem() == Items.COOKIE && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                heal(4);
                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                FeywildMod.getNetwork().sendParticles(this.level, ParticleSerializer.Type.FEY_HEART, this.getX(), this.getY(), this.getZ());
                player.swing(hand, true);
            } else if (this.isTamed() && player instanceof ServerPlayerEntity && this.owner != null && this.owner.equals(player.getUUID())) {
                this.interactQuest((ServerPlayerEntity) player, hand); 
            }
        }
        return ActionResultType.CONSUME;
    }
    
    private void interactQuest(ServerPlayerEntity player, Hand hand) {
        QuestData quests = QuestData.get(player);
        if (quests.canComplete(this.alignment)) {
            QuestDisplay completionDisplay = quests.completePendingQuest();
            if (completionDisplay != null) {
                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(completionDisplay, false));
                player.swing(hand, true);
            } else {
                List<SelectableQuest> active = quests.getActiveQuests();
                if (active.size() == 1) {
                    FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(active.get(0).display, false));
                    player.swing(hand, true);
                } else if (!active.isEmpty()) {
                    FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestSelectionSerializer.Message(this.getDisplayName(), this.alignment, active));
                    player.swing(hand, true);
                }
            }
        } else {
            QuestDisplay initDisplay = quests.initialize(this.alignment);
            if (initDisplay != null) {
                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(initDisplay, true));
                player.swing(hand, true);
            }
        }
    }
    
    private boolean tryAcceptGift(ServerPlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty()) {
            AlignmentStack input = new AlignmentStack(this.alignment, stack);
            if (QuestData.get(player).checkComplete(FeyGiftTask.INSTANCE, input)) {
                if (!player.isCreative()) stack.shrink(1);
                player.sendMessage(new TranslationTextComponent("message.feywild." + this.alignment.id + "_fey_thanks"), player.getUUID());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return this.isTamed;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSq) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
        return ModSoundEvents.pixieHurt;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.pixieDeath;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return random.nextBoolean() ? ModSoundEvents.pixieAmbient : null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pixie.fly", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState castingPredicate(AnimationEvent<E> event) {
        if (this.isCasting() && !(this.dead || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pixie.spellcasting", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<FeyEntity> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        AnimationController<FeyEntity> castingController = new AnimationController<>(this, "castingController", 0, this::castingPredicate);
        animationData.addAnimationController(flyingController);
        animationData.addAnimationController(castingController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}



