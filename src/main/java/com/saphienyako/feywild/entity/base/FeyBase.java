package com.saphienyako.feywild.entity.base;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.entity.goals.GoToTargetPositionGoal;
import com.saphienyako.feywild.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
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
    }

    public static boolean canSpawn(ServerLevelAccessor level, BlockPos pos) {
        return isBrightEnoughToSpawn(level, pos);
    }

    protected static boolean isBrightEnoughToSpawn(LevelReader level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) > 8;
    }

    public SimpleParticleType getParticle() {
        return null;
    }


    public Vec3 getCurrentPointOfInterest() {
        // Skip moving if someone is riding
        if (this.isVehicle()) {
            Entity passenger = this.getFirstPassenger();
            if (!(passenger instanceof BeeKnightEntity)) {
                return null;
            }
        }
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
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, 12, this.getTargetPositionSpeed()));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
    }
    @Override
    public void tick() {
        super.tick();
    }


    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);

        if (this.owner != null) {
            output.store("Owner", UUIDUtil.CODEC, this.owner);
        }

        if (this.summonPos != null) {
            output.store("SummonPos", BlockPos.CODEC, this.summonPos);
        }

        output.putBoolean("FollowingPlayer", this.followingPlayer);
        output.putBoolean("AbilityActive", this.abilityActive);
        output.putBoolean("VoiceActive", this.voiceActive);
    }

    /** @noinspection NullableProblems*/
    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);

        this.owner = input.read("Owner", UUIDUtil.CODEC)
                .orElse(null);

        this.summonPos = input.read("SummonPos", BlockPos.CODEC)
                .map(BlockPos::immutable)
                .orElse(null);

        this.followingPlayer = input.getBooleanOr("FollowingPlayer", false);
        this.abilityActive = input.getBooleanOr("AbilityActive", false);
        this.voiceActive = input.getBooleanOr("VoiceActive", false);
    }

    @Override
    public boolean hurtServer(@NotNull ServerLevel level, DamageSource source, float amount) {
        Entity attacker = source.getEntity();

        if (attacker instanceof LivingEntity living) {
            if (!canBeDamagedByAnyWeapon() && !isIronTool(living.getMainHandItem())) {
                return false;
            }
        }

        return super.hurtServer(level, source, amount);
    }

    public boolean isIronTool(ItemStack stack) {
        return stack.is(ModTags.Items.IRON_TOOLS);
    }

    protected boolean canBeDamagedByAnyWeapon() {
        return false;
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
    protected int calculateFallDamage(double fallDistance, float damageModifier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    protected int getBaseExperienceReward(@NotNull ServerLevel level) {
        return this.isTamed() ? 0 : super.getBaseExperienceReward(level);
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
        Identifier id = this.getType().builtInRegistryHolder().key().identifier();
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
