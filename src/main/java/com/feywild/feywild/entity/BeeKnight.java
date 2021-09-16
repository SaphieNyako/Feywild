package com.feywild.feywild.entity;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.CapabilityQuests;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
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

public class BeeKnight extends FeyBase implements IAnimatable {

    // Model changes
    public static final DataParameter<Boolean> AGGRAVATED = EntityDataManager.defineId(BeeKnight.class, DataSerializers.BOOLEAN);

    private BlockPos treasureBlock;

    protected BeeKnight(EntityType<? extends BeeKnight> type, World world) {
        super(type, Alignment.SUMMER, world);
    }

    public static boolean canSpawn(EntityType<? extends BeeKnight> entity, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(world.getBlockState(pos.below()).getBlock());
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.FOLLOW_RANGE, 80)
                .add(Attributes.ATTACK_DAMAGE, 4);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        setTreasureBlock(this.blockPosition());
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(30, new LookAtGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.addGoal(70, new GoToTargetPositionGoal(this, this::getTreasureVector, 20, 1.5f));
        this.goalSelector.addGoal(30, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
        this.goalSelector.addGoal(1, new RestrictedAttackGoal(this, 1.2f, true));
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && hurtTime > 0) {  //&& getTarget() != null
            setTarget(getLastHurtByMob());
            setAggravated(true);

        }
        super.tick();
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d hitVec, @Nonnull Hand hand) {
        if (!player.level.isClientSide) {
            player.getCapability(CapabilityQuests.QUESTS).ifPresent(cap -> {
                if (cap.getReputation() >= MobConfig.summer_bee_knight.required_reputation && cap.getAlignment() == Alignment.SUMMER) {
                    player.sendMessage(new TranslationTextComponent("message.feywild.bee_knight_pass"), player.getUUID());
                } else {
                    player.sendMessage(new TranslationTextComponent("message.feywild.bee_knight_fail"), player.getUUID());
                }
            });
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        this.setTreasureBlock(new BlockPos(nbt.getInt("TreasureX"), nbt.getInt("TreasureY"), nbt.getInt("TreasureZ")));
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        nbt.putInt("TreasureX", treasureBlock.getX());
        nbt.putInt("TreasureY", treasureBlock.getY());
        nbt.putInt("TreasureZ", treasureBlock.getZ());
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

    public BlockPos getTreasureBlock() {
        return treasureBlock;
    }

    public void setTreasureBlock(BlockPos treasureBlock) {
        this.treasureBlock = treasureBlock;
    }

    public Vector3d getTreasureVector() {
        return new Vector3d(treasureBlock.getX(), treasureBlock.getY(), treasureBlock.getZ());
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
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

    private static class RestrictedAttackGoal extends MeleeAttackGoal {

        BeeKnight creature;

        public RestrictedAttackGoal(BeeKnight creature, double speed, boolean visualContact) {
            super(creature, speed, visualContact);
            this.creature = creature;
        }

        @Override
        public boolean canUse() {
            return creature.isAggravated() && creature.getTarget() != null && !creature.getTarget().isDeadOrDying();
        }

        @Override
        public void start() {
            super.start();
            // creature.setTarget(creature.getLastHurtByMob());
            if (creature.getTreasureBlock() == null || creature.getTreasureBlock().closerThan(new BlockPos(0, 0, 0), 1)) {
                creature.setTreasureBlock(creature.blockPosition());
            }
        }

        @Override
        public boolean canContinueToUse() {
            return creature.isAggravated() && creature.getTreasureBlock().closerThan(creature.blockPosition(), 2 * MobConfig.summer_bee_knight.aggrevation_range) && creature.getTarget() != null && !creature.getTarget().isDeadOrDying();
        }

        @Override
        public void stop() {
            super.stop();
            creature.setAggravated(false);
        }
    }

}
