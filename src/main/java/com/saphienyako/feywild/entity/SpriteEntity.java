package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpriteEntity extends FlyingFeyBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(SpriteEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SpriteEntity.class, EntityDataSerializers.INT);

    public final AnimationState FLY_IDLE_ANIMATION = new AnimationState();
    public final AnimationState FLY_ANIMATION = new AnimationState();

    public final AnimationState HAPPY_ANIMATION = new AnimationState();

    public final AnimationState ANGRY_ANIMATION = new AnimationState();

    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;
    private int movingTicks = 0;
    protected SpriteEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.entityData.set(VARIANT, SpriteVariant.HEXEN.ordinal());
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE,0);
        builder.define(VARIANT, SpriteVariant.HEXEN.ordinal());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("SpriteVariant", this.entityData.get(VARIANT));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("SpriteVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("SpriteVariant"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

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
    public SimpleParticleType getParticle() {
        return ModParticles.SPRING_SPARKLE_PARTICLE.get();
    }


    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > MIN_MOVING_SPEED_SQR;
    }

    private boolean isActuallyMoving() {
        if (isMoving()) {
            movingTicks = 5;
        } else {
            movingTicks = Math.max(0, movingTicks - 1);
        }
        return movingTicks > 0;
    }

    private void setupAnimationStates() {
        if (isActuallyMoving()) {
            if (!FLY_ANIMATION.isStarted()) {
                FLY_ANIMATION.start(this.tickCount);
            }
            FLY_IDLE_ANIMATION.stop();
        } else {
            if (!FLY_IDLE_ANIMATION.isStarted()) {
                FLY_IDLE_ANIMATION.start(this.tickCount);
            }
            FLY_ANIMATION.stop();
        }
    }


    @Override
    public Alignment getAlignment() {
        return Alignment.HEXEN;
    }

    @Override
    public ItemLike getDismissItem() {
        return ItemStack.EMPTY.getItem();
    }

    //TODO SOUND

    @Override
    public SoundEvent getCookieSound() {
        return null;
    }

    @Override
    public SoundEvent getNameSound() {
        return null;
    }

    @Override
    public SoundEvent getSummonSound() {
        return null;
    }

    @Override
    public SoundEvent getDismissSound() {
        return SoundEvents.ALLAY_AMBIENT_WITH_ITEM;
    }

    @Override
    public SoundEvent getFollowSound() {
        return null;
    }

    @Override
    public SoundEvent getStaySound() {
        return null;
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return null;
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return null;
    }

    public SpriteEntity.State getState() {
        SpriteEntity.State[] states = SpriteEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }


    public void setState(SpriteEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }


    public SpriteEntity.SpriteVariant getVariant() {
        return SpriteEntity.SpriteVariant.values()[this.entityData.get(VARIANT)];
    }

    public void setVariant(SpriteEntity.SpriteVariant variant) {this.entityData.set(VARIANT, variant.ordinal());}


    public enum State {
        IDLE, POSE, WALK, SING
    }

    public enum SpriteVariant {
        SPRING, SUMMER, WINTER, AUTUMN, HEXEN, BLOSSOM
    }

}
