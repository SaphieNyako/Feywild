package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.UUID;

public class BeeMountEntity extends FlyingFeyBase {
    //MOVEMENT
    //FOLLOW/STAY on FeyBase
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BeeMountEntity.class, EntityDataSerializers.INT);

    public final AnimationState FLY_ANIMATION = new AnimationState();
    public final AnimationState FLY_IDLE_ANIMATION = new AnimationState();
    private UUID knightUUID; //LINK BEE KNIGHT
    private int movingTicks = 0;
    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;

    protected BeeMountEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    //TODO Determines Stats
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
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        if (this.knightUUID != null) {
            tag.putUUID("KnightUUID", this.knightUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.hasUUID("KnightUUID")) {
            this.knightUUID = tag.getUUID("KnightUUID");
        }
    }


    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (this.level().isClientSide) return;

        if (this.getPassengers().isEmpty()) {

            BeeKnightEntity knight = this.getLinkedKnight();

            if (knight == null) {
                knight = this.spawnKnight();

                if (knight != null) {
                    this.knightUUID = knight.getUUID();
                }
            }

            if (knight != null && !knight.isPassenger()) {
                knight.startRiding(this, true);
            }
        }
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


    @Nonnull
    private BeeKnightEntity getLinkedKnight() {
        if (this.knightUUID == null) return null;

        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return null;
        }

        Entity entity = serverLevel.getEntity(this.knightUUID);
        return entity instanceof BeeKnightEntity knight ? knight : null;
    }

    private BeeKnightEntity spawnKnight() {
        BeeKnightEntity knight = ModEntities.BEE_KNIGHT.get().create(this.level());

        if (knight != null) {
            knight.moveTo(this.position());
            this.level().addFreshEntity(knight);
        }
        return knight;
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return false;
    }

    @Override
    public boolean isEffectiveAi() {
        return true;
    }

    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity living) {
            return living;
        }
        return null;
    }

    @Override
    public void travel(Vec3 travelVector) {
        LivingEntity controller = this.getControllingPassenger();

        if (this.isVehicle() && controller != null) {
            this.setYRot(controller.getYRot());
            this.yRotO = this.getYRot();

            float forward = controller.zza;
            float strafe = controller.xxa;

            float speed = (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);

            Vec3 input = new Vec3(strafe, 0, forward);
            input = input.scale(speed);
            System.out.println("Delta: " + this.getDeltaMovement());
            super.travel(input);
            return;
        }

        super.travel(travelVector);
    }

    @Override
    public void rideTick() {
        super.rideTick();

        if (this.isVehicle()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0, 0));
            this.flyingTravel(this, Vec3.ZERO);
        }
    }

    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            double yOffset = 0.5D;

            moveFunction.accept(passenger,
                    this.getX(),
                    this.getY() + yOffset, this.getZ());
        }
    }


    @Override
    public boolean isVehicle() {
        return false;
    }

    @Override
    public Alignment getAlignment() {
        return null;
    }

    @Override
    public ItemLike getDismissItem() {
        return null;
    }

    //TODO SOUND IF HAS_BEE_KNIGHT RETURN BEE KNIGHT SOUND ELSE RETURN BEE SOUND BUZZ BUZZ


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
        return null;
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

    public BeeKnightEntity.State getState() {
        BeeKnightEntity.State[] states = BeeKnightEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(BeeKnightEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    public enum State {
        FLY, FLY_IDLE
    }
}
