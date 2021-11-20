package com.feywild.feywild.entity.base;


import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class FeyBase extends CreatureEntity implements IAnimatable {

    public final Alignment alignment;

    private int counter = 0;
    private final AnimationFactory factory = new AnimationFactory(this);

    @Nullable
    protected UUID owner;

    protected FeyBase(EntityType<? extends CreatureEntity> entityType, Alignment alignment, World world) {
        super(entityType, world);
        this.alignment = alignment;
        this.noCulling = true;
    }

    @Nullable
    public PlayerEntity getOwner() {
        return this.owner == null ? null : this.level.getPlayerByUUID(this.owner);
    }

    public void setOwner(@Nullable PlayerEntity owner) {
        this.setOwner(owner == null ? null : owner.getUUID());
    }

    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
    }

    @Nullable
    public UUID getOwnerId() {
        return this.owner;
    }

    @Nullable
    public abstract Vector3d getCurrentPointOfInterest();

    @Nullable
    public abstract BasicParticleType getParticle();

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(30, new LookAtGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, getMovementRange(), 1.5f));
        this.goalSelector.addGoal(30, new LookRandomlyGoal(this));
    }

    protected int getMovementRange() {
        return 6;
    }

    @Override
    public void tick() {
        super.tick();
        counter++;
        if (level.isClientSide && random.nextInt(11) == 0 && getParticle()!= null) {
            level.addParticle(
                    this.getParticle(),
                    this.getX() + (Math.random() - 0.5),
                    this.getY() + 1 + (Math.random() - 0.5),
                    this.getZ() + (Math.random() - 0.5),
                    0, -0.1, 0
            );
        }else if(counter > 160 && !level.isClientSide && getOwner()!=null){
            if(QuestData.get((ServerPlayerEntity) getOwner()).getAlignment() != this.alignment && QuestData.get((ServerPlayerEntity) getOwner()).getAlignment()  != null){
                getOwner().sendMessage(new TranslationTextComponent("message.feywild."+alignment.id+".dissapear"),getOwnerId());
                remove();
            }
            counter = 0;
        }
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


    @Override
    public boolean requiresCustomPersistence() {
        return true;
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
        return this.random.nextBoolean() ? ModSoundEvents.pixieAmbient : null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.owner != null) {
            nbt.putUUID("Owner", this.owner);
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
    }
}
