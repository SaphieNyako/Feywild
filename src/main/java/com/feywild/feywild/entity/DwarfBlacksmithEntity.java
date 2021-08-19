package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.ITameable;
import com.feywild.feywild.entity.base.TraderEntity;
import com.feywild.feywild.entity.goals.DwarvenAttackGoal;
import com.feywild.feywild.entity.goals.GoToAnvilPositionGoal;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.entity.goals.RefreshStockGoal;
import com.feywild.feywild.entity.model.DwarfBlacksmithModel;
import com.feywild.feywild.world.dimension.ModDimensions;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class DwarfBlacksmithEntity extends TraderEntity implements ITameable, IAnimatable {

    public static final DataParameter<Integer> STATE = EntityDataManager.defineId(DwarfBlacksmithEntity.class, DataSerializers.INT);
    //GeckoLib variable
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private BlockPos summonPos;
    private boolean isTamed;

    public DwarfBlacksmithEntity(EntityType<? extends TraderEntity> type, World worldIn) {
        super(type, worldIn);
        //GeckoLib check
        this.noCulling = true;
        this.moveControl = new MovementController(this);
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 36)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.ARMOR_TOUGHNESS, 5)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    public static boolean canSpawn(EntityType<DwarfBlacksmithEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
        //noinspection deprecation
        if (pos.getY() >= world.getSeaLevel() - 10 || world.canSeeSky(pos) || random.nextDouble() < 0.15) {
            return false;
        } else {
            return checkMobSpawnRules(type, world, reason, pos, random);
        }
    }

    @Override
    public String getTradeCategory() {
        return this.isTamed() ? "tamed" : "untamed";
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(PlayerEntity player, @Nonnull Vector3d vec, @Nonnull Hand hand) {
        if (!player.getCommandSenderWorld().isClientSide) {
            trade(player);
        }
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }

    protected void trade(PlayerEntity player){
        this.setTradingPlayer(player); //added
        this.openTradingScreen(player, new TranslationTextComponent("Dwarven Trader"), 1);
        player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.dialogue"), false);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(@Nonnull IServerWorld world, @Nonnull DifficultyInstance difficulty, @Nonnull SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataNbt) {
        this.restrictTo(this.blockPosition(), 7);
        return super.finalizeSpawn(world, difficulty, reason, spawnData, dataNbt);
    }

    public BlockPos getSummonPos() {
        return this.summonPos;
    }

    public void setSummonPos(BlockPos summonPos) {
        this.summonPos = summonPos.immutable();
    }

    @Override
    public boolean isTamed() {
        return this.isTamed;
    }

    public void setTamed(boolean tamed) {
        this.isTamed = tamed;
    }

    public State getState() {
        State[] states = State.values();
        return states[MathHelper.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.1f, 8));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.5D));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, GoToTargetPositionGoal.byBlockPos(this, this::getSummonPos, 5, 0.5f));
        this.goalSelector.addGoal(2, new GoToAnvilPositionGoal(this, this::getSummonPos, 5));
        this.goalSelector.addGoal(6, new RefreshStockGoal(this));
        this.targetSelector.addGoal(1, new DwarvenAttackGoal(this));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Tamed", this.isTamed);
        if (this.summonPos != null) {
            NBTX.putPos(nbt, "SummonPos", this.summonPos);
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.isTamed = nbt.getBoolean("Tamed");
        this.summonPos = NBTX.getPos(nbt, "SummonPos", null);
    }

    @Override
    public boolean checkSpawnRules(@Nonnull IWorld worldIn, @Nonnull SpawnReason spawnReasonIn) {
        return super.checkSpawnRules(worldIn, spawnReasonIn) && this.blockPosition().getY() < 60 && !worldIn.canSeeSky(this.blockPosition());
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    @Override
    protected boolean canRide(@Nonnull Entity entityIn) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected int getExperienceReward(@Nonnull PlayerEntity player) {
        return 0;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
        return SoundEvents.VILLAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_CELEBRATE;
    }

    @Override
    protected float getVoicePitch() {
        return 0.6f;
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (!this.dead && !this.isDeadOrDying()) {
            if (this.getState() == State.ATTACKING) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.smash", false));
                return PlayState.CONTINUE;
            } else if (this.getState() == State.WORKING) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.craft", true));
                return PlayState.CONTINUE;
            }
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.stand", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    public enum State {
        IDLE, ATTACKING, WORKING
    }
}
