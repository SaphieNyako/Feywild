package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class FeyBase extends PathfinderMob implements IAnimatable, IOwnable, ISummonable {

    public final Alignment alignment;

    @Nullable
    private BlockPos summonPos = null;
    
    @Nullable
    protected UUID owner;

    private int unalignedTicks = 0;

    private final AnimationFactory factory = new AnimationFactory(this);
    
    protected FeyBase(EntityType<? extends PathfinderMob> entityType, Alignment alignment, Level level) {
        super(entityType, level);
        this.alignment = alignment;
        this.noCulling = true;
    }

    @Nullable
    public abstract SimpleParticleType getParticle();
    
    @Nullable
    public Vec3 getCurrentPointOfInterest() {
        if (this.summonPos != null) {
            return new Vec3(this.summonPos.getX() + 0.5, this.summonPos.getY(), this.getSummonPos().getZ() + 0.5);
        } else {
            return null;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, 6, 1.5f));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide && getParticle() != null && random.nextInt(11) == 0) {
            level.addParticle(
                    this.getParticle(),
                    this.getX() + (Math.random() - 0.5),
                    this.getY() + 1 + (Math.random() - 0.5),
                    this.getZ() + (Math.random() - 0.5),
                    0, -0.1, 0
            );
        }
        
        Player owner = this.getOwningPlayer();
        if (owner instanceof ServerPlayer serverPlayer) {
            Alignment ownerAlignment = QuestData.get(serverPlayer).getAlignment();
            if (ownerAlignment != null && ownerAlignment != this.alignment) {
                unalignedTicks += 1;
                if (unalignedTicks >= 300) {
                    owner.sendMessage(new TranslatableComponent("message.feywild." + alignment.id + ".dissapear"), owner.getUUID());
                    this.remove(RemovalReason.DISCARDED);
                }
            } else {
                unalignedTicks = 0;
            }
        } else {
            unalignedTicks = 0;
        }
    }
    
    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.owner != null) {
            nbt.putUUID("Owner", this.owner);
        } else {
            nbt.remove("Owner");
        }
        if (this.summonPos != null) {
            nbt.put("SummonPos", NbtUtils.writeBlockPos(this.summonPos));
        } else {
            nbt.remove("SummonPos");
        }
        nbt.putInt("UnalignedTicks", this.unalignedTicks);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        this.summonPos = nbt.contains("SummonPos", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(nbt.getCompound("SummonPos")).immutable() : null;
        this.unalignedTicks = nbt.getInt("UnalignedTicks");
    }

    @Nullable
    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(UUID uid) {
        this.owner = uid;
    }

    @Override
    public BlockPos getSummonPos() {
        return this.summonPos;
    }

    @Override
    public void setSummonPos(BlockPos pos) {
        this.summonPos = pos.immutable();
    }

    @Override
    public Level getEntityLevel() {
        return this.level;
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
        return this.isTamed() ? 0 : super.getExperienceReward(player);
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
}
