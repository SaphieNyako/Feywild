package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.entity.goals.GoToTargetPositionGoal;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;
import java.util.UUID;

public abstract class FeyBase extends PathfinderMob implements IOwnable, ISummonable {

    @javax.annotation.Nullable
    protected UUID owner;
    @javax.annotation.Nullable
    private BlockPos summonPos = null;
    private boolean followingPlayer = false;
    private boolean abilityActive = false;

    protected FeyBase(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    public static boolean canSpawn(EntityType<? extends FeyBase> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
        //TODO make mobs spawn in overworld?
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter getter, BlockPos pos) {
        return getter.getRawBrightness(pos, 0) > 8;
    }

    @javax.annotation.Nullable
    public SimpleParticleType getParticle() {
        return null;
    }

    @javax.annotation.Nullable
    public Vec3 getCurrentPointOfInterest() {
        if (this.getFollowingPlayer()) {
            Player player = this.getOwningPlayer();
            return player == null ? null : player.position();
        } else if (this.summonPos != null) {
            return new Vec3(this.summonPos.getX() + 0.5, this.summonPos.getY(), this.summonPos.getZ() + 0.5);
        } else {
            return null;
        }
    }

    public float getTargetPositionSpeed() {
        return 1.5f;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, 6, this.getTargetPositionSpeed()));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide && this.getParticle() != null && random.nextInt(11) == 0) {
            for (int i = 0; i < 4; i++) {
                level().addParticle(this.getParticle(),
                        this.getX() + (Math.random() - 0.5),
                        this.getY() + 1 + (Math.random() - 0.5),
                        this.getZ() + (Math.random() - 0.5),
                        0, 0, 0
                );
            }
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
        nbt.putBoolean("FollowingPlayer", this.followingPlayer);
        nbt.putBoolean("AbilityActive", this.abilityActive);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        this.summonPos = nbt.contains("SummonPos", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(nbt.getCompound("SummonPos")).immutable() : null;
        this.followingPlayer = nbt.getBoolean("FollowingPlayer");
        this.abilityActive = nbt.getBoolean("AbilityActive");

    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player && player == this.getOwningPlayer()) {
            return true;
        }
        return super.isDamageSourceBlocked(damageSource);
    }
    @javax.annotation.Nullable
    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(@javax.annotation.Nullable UUID uid) {
        this.owner = uid;
    }


    @Override
    public @org.jetbrains.annotations.Nullable BlockPos getSummonPos() {
        return this.summonPos;
    }

    @Override
    public void setSummonPos(BlockPos pos) {
        this.summonPos = pos == null ? null : pos.immutable();
    }

    public void setFollowingPlayer(Boolean followingPlayer){
        this.followingPlayer = followingPlayer;
    }

    public Boolean getFollowingPlayer(){
        return this.followingPlayer;
    }

    public Boolean getAbilityActive() {
        return this.abilityActive;
    }

    public void setAbilityActive(Boolean abilityActive) {
        this.abilityActive = abilityActive;
    }

    @Override
    public Level getEntityLevel() {
        return this.level();
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
    public int getExperienceReward() {
        return this.isTamed() ? 0 : super.getExperienceReward();
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

    @javax.annotation.Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
       return ModSounds.PIXIE_HURT.get();
    }

    @javax.annotation.Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PIXIE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        Random random = new Random();
        if(random.nextFloat() < 0.1f){
            return ModSounds.PIXIE_AMBIENT.get();
        } else return null;

    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }

    public abstract Alignment getAligment();

    public abstract ItemLike getDismissItem();

    public String getEntityName(){
        ResourceLocation id =this.getType().builtInRegistryHolder().key().location();
        return id.getPath();
    }

    public Component getFeyNameMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_name");
    }

    public Component getFeyCookieMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_cookie");
    }

    public Component getFeyFollowMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_follow");
    }

    public Component getFeyStayMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_stay");
    }
}
