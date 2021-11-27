package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import com.mojang.math.Vector3d;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class FeyBase extends PathfinderMob implements IAnimatable {

    public final Alignment alignment;

    private final AnimationFactory factory = new AnimationFactory(this);

    @Nullable
    protected UUID owner;
    private int counter = 0;
    public Vec3 summonPos = null;

    protected FeyBase(EntityType<? extends PathfinderMob> entityType, Alignment alignment, Level level) {
        super(entityType, level);
        this.alignment = alignment;
        this.noCulling = true;
        this.moveControl = new FlyingMoveControl(this, 4, true);
    }

    @Nullable
    public Player getOwner() {
        return this.owner == null ? null : this.level.getPlayerByUUID(this.owner);
    }

    public void setOwner(@Nullable Player owner) {
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
    public Vec3 getCurrentPointOfInterest() {
        return summonPos;
    }

    public void setCurrentTargetPos(@Nullable BlockPos currentTargetPos) {
        this.summonPos = currentTargetPos == null ? null : new Vec3(currentTargetPos.getX(), currentTargetPos.getY(), currentTargetPos.getZ());
    }

    public void setCurrentTargetPos(@Nullable Vec3 currentTargetPos) {
        this.summonPos = currentTargetPos;
    }

    @Nullable
    public abstract SimpleParticleType getParticle();

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, getMovementRange(), 1.5f));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
    }

    protected int getMovementRange() {
        return 6;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide && getParticle()!= null && random.nextInt(11) == 0) {
            level.addParticle(
                    this.getParticle(),
                    this.getX() + (Math.random() - 0.5),
                    this.getY() + 1 + (Math.random() - 0.5),
                    this.getZ() + (Math.random() - 0.5),
                    0, -0.1, 0
            );
        }else if(counter > 160 && !level.isClientSide && getOwner()!=null) {
            if (QuestData.get((ServerPlayer) getOwner()).getAlignment() != this.alignment && QuestData.get((ServerPlayer) getOwner()).getAlignment() != null) {
                getOwner().sendMessage(new TranslatableComponent("message.feywild." + alignment.id + ".dissapear"), getOwnerId());
                remove(RemovalReason.DISCARDED);
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
    public boolean causeFallDamage(float fallDistance, float multiplier, @Nonnull DamageSource source) {
        return false;
    }

    @Override
    protected int getExperienceReward(@Nonnull Player player) {
        return 0;
    }

    @Override
    public boolean canBeLeashed(@Nonnull Player player) {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity entityIn) {
        return false;
    }

    @Override
    public float getVoicePitch() {
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
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.owner != null) {
            nbt.putUUID("Owner", this.owner);
        }
        if (getCurrentPointOfInterest() != null) {
            NBTX.putPos(nbt, "SummonPos", new BlockPos(this.summonPos.x, this.summonPos.y, this.summonPos.z));
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        BlockPos pos = NBTX.getPos(nbt, "SummonPos", null);
        if (pos != null) {
            this.summonPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
