package com.saphienyako.feywild.entity.base;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.entity.goals.GoToTargetPositionGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class FeyBase extends PathfinderMob implements IOwnable, ISummonable {

    @javax.annotation.Nullable
    protected UUID owner;
    @javax.annotation.Nullable
    private BlockPos summonPos = null;
    private boolean followingPlayer = false;
    private boolean abilityActive = false;

    private boolean voiceActive = true;

    protected FeyBase(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public static boolean canSpawn(EntityType<? extends FeyBase> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter getter, BlockPos pos) {
        return getter.getRawBrightness(pos, 0) > 8;
    }

    public SimpleParticleType getParticle() {
        return null;
    }


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
    @SuppressWarnings("resource")
    @Override
    public void tick() {
        super.tick();

    }



    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
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
        nbt.putBoolean("VoiceActive", this.voiceActive);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        this.summonPos = NbtUtils.readBlockPos(nbt, "SummonPos")
                .map(BlockPos::immutable)
                .orElse(null);
        this.followingPlayer = nbt.getBoolean("FollowingPlayer");
        this.abilityActive = nbt.getBoolean("AbilityActive");
        this.voiceActive = nbt.getBoolean("VoiceActive");
    }

    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity living) {
            ItemStack held = living.getMainHandItem();
            if (!isIronTool(held)) {
                return true;
            }
        }

        return super.isDamageSourceBlocked(damageSource);
    }

    private boolean isIronTool(ItemStack stack) {
        return stack.is(Tags.Items.TOOLS) && stack.getItem() instanceof TieredItem tiered &&
                tiered.getTier() == Tiers.IRON;
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

    public Boolean getVoiceActive() {return this.voiceActive;}

    public void setVoiceActive(Boolean voiceActive){this.voiceActive = voiceActive;}

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
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    protected int getBaseExperienceReward() {
        return this.isTamed() ? 0 : super.getBaseExperienceReward();
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected boolean canRide(@NotNull Entity entityIn) {
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

    public abstract Alignment getAlignment();

    public abstract ItemLike getDismissItem();

    public abstract SoundEvent getCookieSound();
    public abstract SoundEvent getNameSound();

    public abstract SoundEvent getSummonSound();

    public abstract SoundEvent getDismissSound();

    public abstract SoundEvent getFollowSound();

    public abstract SoundEvent getStaySound();

    public abstract SoundEvent getAbilityOnSound();

    public abstract SoundEvent getAbilityOffSound();


    @SuppressWarnings("deprecation")
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

    public Component getFeySummonMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_summon");
    }

    public Component getFeyDismissMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_dismiss");
    }

    public Component getFeyAbilityOnMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_ability_on");
    }

    public Component getFeyAbilityOffMessage(){
        return  Component.translatable("message.feywild."+ getEntityName() + "_ability_off");
    }
}
