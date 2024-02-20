package com.feywild.feywild.entity.base;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Objects;
import java.util.UUID;

public abstract class FeyBase extends PathfinderMob implements IOwnable, ISummonable, GeoEntity {

    @Nullable private final Alignment alignment;
    @Nullable protected UUID owner;
    @Nullable private BlockPos summonPos = null;
    private int unalignedTicks = 0;
    private boolean followingPlayer = false;

    protected FeyBase(EntityType<? extends PathfinderMob> entityType, @Nullable Alignment alignment, Level level) {
        super(entityType, level);
        this.alignment = alignment;
        this.noCulling = true;
    }

    @Nullable
    public Alignment alignment() {
        return this.alignment;
    }
    
    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2);
    }

    public static boolean canSpawn(EntityType<? extends FeyBase> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter getter, BlockPos pos) {
        return getter.getRawBrightness(pos, 0) > 8;
    }

    @Nullable
    public SimpleParticleType getParticle() {
        return null;
    }

    @Nullable
    public Vec3 getCurrentPointOfInterest() {
        if (this.canFollowPlayer() && this.followingPlayer) {
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

    public boolean canFollowPlayer() {
        return false;
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
        if (!MiscConfig.summon_all_fey) {
            Player owner = this.getOwningPlayer();
            if (owner instanceof ServerPlayer serverPlayer) {
                Alignment ownerAlignment = QuestData.get(serverPlayer).getAlignment();
                if (ownerAlignment != null && this.alignment != null && ownerAlignment != this.alignment) {
                    unalignedTicks += 1;
                    if (unalignedTicks >= 300) {
                        owner.sendSystemMessage(Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.getType())).getPath() + ".disappear"));
                        this.remove(RemovalReason.DISCARDED);
                    }
                } else {
                    unalignedTicks = 0;
                }
            } else {
                unalignedTicks = 0;
            }
        }
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        if (player.isShiftKeyDown() && this.canFollowPlayer()) {
            if (this.owner != null && this.owner.equals(player.getUUID())) {
                if (this.followingPlayer) {
                    this.followingPlayer = false;
                    this.setSummonPos(this.blockPosition());
                    player.sendSystemMessage(Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.getType())).getPath() + ".stay").append(Component.translatable("message.feywild.fey_stay").withStyle(ChatFormatting.ITALIC)));
                } else {
                    this.followingPlayer = true;
                    player.sendSystemMessage(Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.getType())).getPath() + ".follow").append(Component.translatable("message.feywild.fey_follow").withStyle(ChatFormatting.ITALIC)));
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
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
        nbt.putBoolean("FollowingPlayer", this.followingPlayer);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        this.summonPos = nbt.contains("SummonPos", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(nbt.getCompound("SummonPos")).immutable() : null;
        this.unalignedTicks = nbt.getInt("UnalignedTicks");
        this.followingPlayer = nbt.getBoolean("FollowingPlayer");
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof Player player && player == this.getOwningPlayer()) {
            return true;
        }
        return super.isDamageSourceBlocked(damageSource);
    }

    @Nullable
    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(@Nullable UUID uid) {
        this.owner = uid;
    }

    @Nullable
    @Override
    public BlockPos getSummonPos() {
        return this.summonPos;
    }

    @Override
    public void setSummonPos(BlockPos pos) {
        this.summonPos = pos == null ? null : pos.immutable();
    }

    @Override
    public Level getEntityLevel() {
        return this.level();
    }

    @Override
    public boolean canFreeze() {
        return this.alignment != Alignment.WINTER;
    }

    @Override
    public boolean fireImmune() {
        return this.alignment == Alignment.SUMMER;
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

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return ModSoundEvents.pixieHurt.getSoundEvent();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.pixieDeath.getSoundEvent();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextBoolean() ? ModSoundEvents.pixieAmbient.getSoundEvent() : null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
