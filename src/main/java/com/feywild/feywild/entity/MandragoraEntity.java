package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.entity.goals.SingGoal;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MandragoraEntity extends CreatureEntity implements IAnimatable {

    public static final DataParameter<Boolean> CASTING = EntityDataManager.defineId(MandragoraEntity.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    private BlockPos summonPos;
    // private String variation;
    private Variation mandragoraVariation;

    protected MandragoraEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.noCulling = true;
        this.moveControl = new MovementController(this);
        setVariation();
    }

    /* ATTRIBUTES */
    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.LUCK, 0.2);
    }

    public void setVariation() {
        Random random = new Random();
        switch (random.nextInt(5)) {
            case 0:
                mandragoraVariation = Variation.MELON;
                break;
            case 1:
                mandragoraVariation = Variation.ONION;
                break;
            case 2:
                mandragoraVariation = Variation.POTATO;
                break;
            case 3:
                mandragoraVariation = Variation.PUMPKIN;
                break;
            default:
                mandragoraVariation = Variation.TOMATO;
        }
    }

    public Variation getVariation() {
        return mandragoraVariation;
    }

    public BlockPos getSummonPos() {
        return this.summonPos;
    }

    public void setSummonPos(BlockPos summonPos) {
        this.summonPos = summonPos.immutable();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.2f, 4));
        this.goalSelector.addGoal(2, GoToTargetPositionGoal.byBlockPos(this, this::getSummonPos, 5, 0.2f));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(20, new SingGoal(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.summonPos != null) {
            NBTX.putPos(nbt, "SummonPos", this.summonPos);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.summonPos = NBTX.getPos(nbt, "SummonPos", null);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CASTING, false);
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    public boolean isCasting() {
        return this.entityData.get(CASTING);
    }

    public void setCasting(boolean casting) {
        this.entityData.set(CASTING, casting);
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

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
        return ModSoundEvents.mandragoraHurt;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.mandragoraDeath;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        switch (random.nextInt(4)) {
            case 0:
                return ModSoundEvents.mandragoraAmbience01;
            case 1:
                return ModSoundEvents.mandragoraAmbience02;
            default:
                return null;
        }
    }

    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d hitVec, @Nonnull Hand hand) {
        if (player.getItemInHand(hand).getItem() == Items.COOKIE && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
            this.heal(4);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            FeywildMod.getNetwork().sendParticles(this.level, ParticleSerializer.Type.FEY_HEART, this.getX(), this.getY(), this.getZ());
            player.swing(hand, true);
        }
        return ActionResultType.CONSUME;
    }

    /*ANIMATION */
    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (this.isCasting() && !(this.dead || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mandragora.sing", false));
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mandragora.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mandragora.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public enum Variation {MELON, ONION, POTATO, PUMPKIN, TOMATO}
}
