package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.ParticleSerializer;
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
import net.minecraft.util.*;
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

    private final AnimationFactory factory = new AnimationFactory(this);
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
                .add(Attributes.MOVEMENT_SPEED, 0.1)
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

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(30, new LookAtGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.addGoal(30, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.1f, 1));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
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
        return SoundEvents.FOX_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FOX_AMBIENT;
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

    private <E extends IAnimatable> PlayState walkPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mandragora.walk", true));
        return PlayState.CONTINUE;
    }

    /*ANIMATION */

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<MandragoraEntity> walkController = new AnimationController<>(this, "walkController", 0, this::walkPredicate);
        animationData.addAnimationController(walkController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public enum Variation {MELON, ONION, POTATO, PUMPKIN, TOMATO}
}
