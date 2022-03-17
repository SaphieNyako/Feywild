package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public abstract class FeyBase extends CreatureEntity implements IAnimatable {

    public final Alignment alignment;
    private final AnimationFactory factory = new AnimationFactory(this);
    public Vector3d summonPos = null;
    @Nullable
    protected UUID owner;
    private boolean orderedToStay;

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
    public Vector3d getCurrentPointOfInterest() {
        return summonPos;
    }

    public void setCurrentTargetPos(@Nullable BlockPos currentTargetPos) {
        this.summonPos = currentTargetPos == null ? null : new Vector3d(currentTargetPos.getX(), currentTargetPos.getY(), currentTargetPos.getZ());
    }

    public void setCurrentTargetPos(@Nullable Vector3d currentTargetPos) {
        this.summonPos = currentTargetPos;
    }

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
        if (level.isClientSide && random.nextInt(11) == 0 && getParticle() != null) {
            level.addParticle(
                    this.getParticle(),
                    this.getX() + (Math.random() - 0.5),
                    this.getY() + 1 + (Math.random() - 0.5),
                    this.getZ() + (Math.random() - 0.5),
                    0, -0.1, 0
            );
        } else if (!level.isClientSide && this.tickCount % 160 == 0 && getOwner() instanceof ServerPlayerEntity && this.isAlive()) {
            ServerPlayerEntity owner = (ServerPlayerEntity) getOwner();
            if (!this.alignment.equals(QuestData.get(owner).getAlignment()) && QuestData.get(owner).getAlignment() != null) {
                owner.sendMessage(new TranslationTextComponent("message.feywild." + this.getEntity().getEncodeId().replace("feywild:", "") + ".disappear"), getOwnerId());
                remove();
            }
        }
    }

    @Override
    public boolean isVehicle() {
        return false;
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
        if (getCurrentPointOfInterest() != null) {
            NBTX.putPos(nbt, "SummonPos", new BlockPos(this.getCurrentPointOfInterest().x, this.getCurrentPointOfInterest().y, this.getCurrentPointOfInterest().z));
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        BlockPos pos = NBTX.getPos(nbt, "SummonPos", null);
        if (pos != null) {
            this.summonPos = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public boolean isOrderedToStay() {
        return this.orderedToStay;
    }

    public void setOrderedToStay() {
        this.orderedToStay = true;
        this.setCurrentTargetPos(this.blockPosition());
        Objects.requireNonNull(this.getOwner()).sendMessage(new TranslationTextComponent("message.feywild." + this.alignment.id + "_fey_stay").append(new TranslationTextComponent("message.feywild.fey_stay").withStyle(TextFormatting.ITALIC)), this.getOwner().getUUID());
    }

    public void setOrderedToFollow() {
        this.orderedToStay = false;
        this.setCurrentTargetPos((BlockPos) null);
        Objects.requireNonNull(this.getOwner()).sendMessage(new TranslationTextComponent("message.feywild." + this.alignment.id + "_fey_follow").append(new TranslationTextComponent("message.feywild.fey_follow").withStyle(TextFormatting.ITALIC)), this.getOwner().getUUID());
    }

    public void unableToFollow() {
        this.orderedToStay = true;
        this.setCurrentTargetPos(this.blockPosition());
    }
}
