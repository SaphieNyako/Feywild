package com.feywild.feywild.entity.base;

import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class FeyBase extends CreatureEntity implements IAnimatable {
    public final Alignment alignment;

    private final AnimationFactory factory = new AnimationFactory(this);

    protected FeyBase(EntityType<? extends CreatureEntity> entityType, Alignment alignment, World world) {
        super(entityType, world);
        this.alignment = alignment;
        this.noCulling = true;
        this.moveControl = new FlyingMovementController(this, 4, true);
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2);
    }

    @Nullable
    public abstract Vector3d getCurrentPointOfInterest();
    
    public abstract BasicParticleType getParticle();
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(30, new LookAtGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, 6, 1.5f));
        this.goalSelector.addGoal(30, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(this, 1));
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide && random.nextInt(11) == 0) {
            level.addParticle(
                    this.getParticle(),
                    this.getX() + (Math.random() - 0.5),
                    this.getY() + 1 + (Math.random() - 0.5),
                    this.getZ() + (Math.random() - 0.5),
                    0, -0.1, 0
            );
        }
    }

    @Override
    public void travel(@Nonnull Vector3d position) {
        if (this.isInWater()) {
            this.moveRelative(0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8));
        } else if (this.isInLava()) {
            this.moveRelative(0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
        } else {
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1, this.getZ());
            float slipperiness = 0.91f;
            if (this.onGround) {
                slipperiness = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            float groundMovementModifier = 0.16277137f / (slipperiness * slipperiness * slipperiness);
            slipperiness = 0.91f;
            if (this.onGround) {
                slipperiness = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1f * groundMovementModifier : 0.02f, position);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(slipperiness));
        }

        this.animationSpeedOld = this.animationSpeed;
        double dx = this.getX() - this.xo;
        double dz = this.getZ() - this.zo;
        float scaledLastHorizontalMotion = MathHelper.sqrt(dx * dx + dz * dz) * 4;
        if (scaledLastHorizontalMotion > 1) {
            scaledLastHorizontalMotion = 1;
        }
        this.animationSpeed += (scaledLastHorizontalMotion - this.animationSpeed) * 0.4;
        this.animationPosition += this.animationSpeed;
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
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected int getExperienceReward(@Nonnull PlayerEntity player) {
        return 0;
    }

    @Override
    public boolean canBeLeashed(@Nonnull PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity entityIn) {
        return false;
    }

    @Override
    protected float getVoicePitch() {
        return 1;
    }


    @Nonnull
    @Override
    protected PathNavigator createNavigation(@Nonnull World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
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
