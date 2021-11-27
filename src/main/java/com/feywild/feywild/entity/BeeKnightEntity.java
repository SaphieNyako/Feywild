package com.feywild.feywild.entity;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FlyingFeyBase;
import com.feywild.feywild.entity.base.IAngry;
import com.feywild.feywild.entity.goals.BeeRestrictAttackGoal;
import com.feywild.feywild.entity.goals.FeyAttackableTargetGoal;
import com.feywild.feywild.entity.goals.ReturnToPositionKnightGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BeeKnightEntity extends FlyingFeyBase implements IAnimatable, IAngry {

    public static final EntityDataAccessor<Boolean> AGGRAVATED = SynchedEntityData.defineId(BeeKnightEntity.class, EntityDataSerializers.BOOLEAN);

    public BeeKnightEntity(EntityType<? extends BeeKnightEntity> type, Level level) {
        super(type, Alignment.SUMMER, level);
    }

    public static boolean canSpawn(EntityType<? extends BeeKnightEntity> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(level.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(level.getBlockState(pos.below()).getBlock());
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return FlyingFeyBase.getDefaultAttributes()
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.FOLLOW_RANGE, 80)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FLYING_SPEED, 2.25);
    }

    public static void anger(Level level, Player player, BlockPos pos) {
        if (!level.isClientSide && player instanceof ServerPlayer) {
            QuestData quests = QuestData.get((ServerPlayer) player);
            if (quests.getAlignment() != Alignment.SUMMER || quests.getReputation() < MobConfig.bee_knight.required_reputation) {
                AABB aabb = new AABB(pos).inflate(2 * MobConfig.bee_knight.aggrevation_range);
                level.getEntities(ModEntityTypes.beeKnight, aabb, entity -> true).forEach(entity -> {
                    if (entity.getCurrentPointOfInterest() != null && pos.closerThan(entity.getCurrentPointOfInterest(), MobConfig.bee_knight.aggrevation_range) && player != entity.getOwner()) {
                        entity.setTarget(player);
                        entity.setAngry(true);
                    }
                });
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag nbt) {
        setCurrentTargetPos(this.blockPosition());
        return super.finalizeSpawn(level, difficulty, reason, data, nbt);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.targetSelector.addGoal(2, new FeyAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new BeeRestrictAttackGoal(this, 1.2f, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(11, new ReturnToPositionKnightGoal(this, this::getCurrentPointOfInterest, getMovementRange(), 1.5f));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));
    }

    @Override
    protected int getMovementRange() {
        return MobConfig.bee_knight.aggrevation_range;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && hurtTime > 0 && getLastHurtByMob() != getOwner()) {  //&& getTarget() != null
                setTarget(getLastHurtByMob());
                setAngry(true);

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
            if ((quests.getAlignment() == Alignment.SUMMER && quests.getReputation() >= MobConfig.bee_knight.required_reputation && getOwner() == null) || player.getUUID() == owner) {
                player.sendMessage(new TranslatableComponent("message.feywild.bee_knight_pass"), player.getUUID());
            } else {
                player.sendMessage(new TranslatableComponent("message.feywild.bee_knight_fail"), player.getUUID());
            }
        }
        return InteractionResult.SUCCESS;
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bee_knight.fly", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<BeeKnightEntity> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        animationData.addAnimationController(flyingController);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGGRAVATED, false);
    }

    @Override
    public boolean isAngry() {
        return this.entityData.get(AGGRAVATED);
    }

    @Override
    public void setAngry(boolean value) {
        this.entityData.set(AGGRAVATED,value);
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
