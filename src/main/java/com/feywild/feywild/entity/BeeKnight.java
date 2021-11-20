package com.feywild.feywild.entity;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FlyingFeyBase;
import com.feywild.feywild.entity.goals.BeeRestrictAttackGoal;
import com.feywild.feywild.entity.goals.FeyAttackableTargetGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
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

public class BeeKnight extends FlyingFeyBase implements IAnimatable {

    public static final DataParameter<Boolean> AGGRAVATED = EntityDataManager.defineId(BeeKnight.class, DataSerializers.BOOLEAN);

    @Nullable
    private BlockPos treasurePos = null;

    public BeeKnight(EntityType<? extends BeeKnight> type, World world) {
        super(type, Alignment.SUMMER, world);
    }

    public static boolean canSpawn(EntityType<? extends BeeKnight> entity, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock()) || Tags.Blocks.SAND.contains(world.getBlockState(pos.below()).getBlock());
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return FlyingFeyBase.getDefaultAttributes()
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.FOLLOW_RANGE, 80)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FLYING_SPEED, 2.25);
    }

    public static void anger(World world, PlayerEntity player, BlockPos pos) {
        if (!world.isClientSide && player instanceof ServerPlayerEntity) {
                QuestData quests = QuestData.get((ServerPlayerEntity) player);
                if (quests.getAlignment() != Alignment.SUMMER || quests.getReputation() < MobConfig.summer_bee_knight.required_reputation) {
                    AxisAlignedBB aabb = new AxisAlignedBB(pos).inflate(2 * MobConfig.summer_bee_knight.aggrevation_range);
                    world.getEntities(ModEntityTypes.beeKnight, aabb, entity -> true).forEach(entity -> {
                        if(entity.treasurePos != null && pos.closerThan(entity.treasurePos, MobConfig.summer_bee_knight.aggrevation_range) && player != entity.getOwner())
                            entity.setAggravated(true);
                    });
                }
        }
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(@Nonnull IServerWorld world, @Nonnull DifficultyInstance difficulty, @Nonnull SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT nbt) {
        setTreasurePos(this.blockPosition());
        return super.finalizeSpawn(world, difficulty, reason, data, nbt);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new FeyAttackableTargetGoal<>(this, PlayerEntity.class, true));
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
    public BasicParticleType getParticle() {
        return ParticleTypes.CRIT;
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(@Nonnull PlayerEntity player, @Nonnull Vector3d hitVec, @Nonnull Hand hand) {
        if (!player.level.isClientSide && player instanceof ServerPlayerEntity) {
            QuestData quests = QuestData.get((ServerPlayerEntity) player);
            if ((quests.getAlignment() == Alignment.SUMMER && quests.getReputation() >= MobConfig.summer_bee_knight.required_reputation && getOwner() == null ) || player.getUUID() == owner) {
                player.sendMessage(new TranslationTextComponent("message.feywild.bee_knight_pass"), player.getUUID());
            } else {
                player.sendMessage(new TranslationTextComponent("message.feywild.bee_knight_fail"), player.getUUID());
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        setTreasurePos(NBTX.getPos(nbt, "TreasurePos", null));
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
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
    public Vector3d getCurrentPointOfInterest() {
        if (treasurePos == null) {
            return null;
        } else {
            return new Vector3d(treasurePos.getX(), treasurePos.getY(), treasurePos.getZ());
        }
    }

    @Override
    public boolean doHurtTarget(@Nonnull Entity pEntity) {
        if(pEntity instanceof LivingEntity)
            ((LivingEntity) pEntity).addEffect(new EffectInstance(Effects.POISON,20 * 5,1));
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
