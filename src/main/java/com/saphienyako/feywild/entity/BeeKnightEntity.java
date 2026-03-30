package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BeeKnightEntity extends FlyingFeyBase {
    //ATTACKS
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(BeeKnightEntity.class, EntityDataSerializers.INT);

    public final AnimationState SIT_ANIMATION = new AnimationState();
    public final AnimationState ATTACK_ANIMATION = new AnimationState();

    protected BeeKnightEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level);
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
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (getState() == State.ATTACK) {
            if (!ATTACK_ANIMATION.isStarted()) {
                ATTACK_ANIMATION.start(this.tickCount);
            }
        } else {
            ATTACK_ANIMATION.stop();
        }

        SIT_ANIMATION.start(this.tickCount);
    }

    @Override
    public Alignment getAlignment() {
        return null;
    }

    @Override
    public ItemLike getDismissItem() {
        return null;
    }

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
        SIT, ATTACK
    }

}
