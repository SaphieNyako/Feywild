package com.feywild.feywild.entity;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.goals.BeeRestrictAttackGoal;
import com.feywild.feywild.entity.goals.FeyAttackableTargetGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.util.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;

public class BeeKnight extends FeyBase implements IAnimatable {

    public static final EntityDataAccessor<Boolean> AGGRAVATED = SynchedEntityData.defineId(BeeKnight.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private BlockPos treasurePos = null;

    public BeeKnight(EntityType<? extends BeeKnight> type, Level level) {
        super(type, Alignment.SUMMER, level);
    }

    public static boolean canSpawn(EntityType<? extends BeeKnight> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(level.getBlockState(pos.below()).getBlock());
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return FeyBase.getDefaultAttributes()
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.FOLLOW_RANGE, 80)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FLYING_SPEED, 2.25);
    }

    public static void anger(Level level, Player player, BlockPos pos) {
        if (!level.isClientSide && player instanceof ServerPlayer) {
                QuestData quests = QuestData.get((ServerPlayer) player);
                if (quests.getAlignment() != Alignment.SUMMER || quests.getReputation() < MobConfig.summer_bee_knight.required_reputation) {
                    AABB aabb = new AABB(pos).inflate(2 * MobConfig.summer_bee_knight.aggrevation_range);
                    level.getEntities(ModEntityTypes.beeKnight, aabb, entity -> true).forEach(entity -> {
                        if(entity.treasurePos != null && pos.closerThan(entity.treasurePos, MobConfig.summer_bee_knight.aggrevation_range) && player != entity.getOwner())
                            entity.setAggravated(true);
                    });
                }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag nbt) {
        setTreasurePos(this.blockPosition());
        return super.finalizeSpawn(level, difficulty, reason, data, nbt);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new FeyAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new BeeRestrictAttackGoal(this, 1.2f, true));
    }

    @Override
    protected int getMovementRange() {
        return 2 * MobConfig.summer_bee_knight.aggrevation_range;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && hurtTime > 0 && getLastHurtByMob() != getOwner()) {  //&& getTarget() != null
            if (treasurePos != null && treasurePos.closerThan(blockPosition(), 2 * MobConfig.summer_bee_knight.aggrevation_range)) {
                setTarget(getLastHurtByMob());
                setAggravated(true);
            } else {
                heal(20);
            }
        }
    }

    @Override
    public SimpleParticleType getParticle() {
        return ParticleTypes.CRIT;
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        if (!player.level.isClientSide && player instanceof ServerPlayer) {
            QuestData quests = QuestData.get((ServerPlayer) player);
            if ((quests.getAlignment() == Alignment.SUMMER && quests.getReputation() >= MobConfig.summer_bee_knight.required_reputation && getOwner() == null ) || player.getUUID() == owner) {
                player.sendMessage(new TranslatableComponent("message.feywild.bee_knight_pass"), player.getUUID());
            } else {
                player.sendMessage(new TranslatableComponent("message.feywild.bee_knight_fail"), player.getUUID());
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        setTreasurePos(NBTX.getPos(nbt, "TreasurePos", null));
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
            if (treasurePos != null)
            NBTX.putPos(nbt, "TreasurePos", treasurePos);
        super.addAdditionalSaveData(nbt);
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bee_knight.fly", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<BeeKnight> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        animationData.addAnimationController(flyingController);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGGRAVATED, false);
    }

    public boolean isAggravated() {
        return this.entityData.get(AGGRAVATED);
    }

    public void setAggravated(boolean aggravated) {
        this.entityData.set(AGGRAVATED, aggravated);
    }

    @Nullable
    public BlockPos getTreasurePos() {
        return treasurePos;
    }

    public void setTreasurePos(@Nullable BlockPos treasurePos) {
        this.treasurePos = treasurePos;
    }

    @Nullable
    @Override
    public Vec3 getCurrentPointOfInterest() {
        if (treasurePos == null) {
            return null;
        } else {
            return new Vec3(treasurePos.getX(), treasurePos.getY(), treasurePos.getZ());
        }
    }

    @Override
    public boolean doHurtTarget(@Nonnull Entity pEntity) {
        if(pEntity instanceof LivingEntity)
            ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.POISON,20 * 5,1));
        return super.doHurtTarget(pEntity);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damage) {
        return this.random.nextBoolean() ? ModSoundEvents.pixieHurt : SoundEvents.BEE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return this.random.nextBoolean() ? ModSoundEvents.pixieDeath : SoundEvents.BEE_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BEE_LOOP;
    }
}
