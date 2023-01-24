package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.FlyingBossBase;
import com.feywild.feywild.entity.goals.IntimidateGoal;
import com.feywild.feywild.entity.goals.PhysicalAttackGoal;
import com.feywild.feywild.entity.goals.SummonVexGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Mab extends FlyingBossBase implements IAnimatable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Mab.class, EntityDataSerializers.INT);
    public final Alignment alignment;
    private final AnimationFactory factory = new AnimationFactory(this);

    protected Mab(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.mab"),
                BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setPlayBossMusic(true).setDarkenScreen(true).setCreateWorldFog(true));
        this.alignment = Alignment.WINTER;
        this.moveControl = new FlyingMoveControl(this, 4, true);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 150)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.LUCK, 0.2);
    }

    public static boolean canSpawn(EntityType<? extends Mab> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(40, new SummonVexGoal(this));
        this.goalSelector.addGoal(50, new IntimidateGoal(this));
        this.goalSelector.addGoal(50, new PhysicalAttackGoal(this));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.mabAmbience;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return ModSoundEvents.mabHurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.mabDeath;
    }

    public Mab.State getState() {
        Mab.State[] states = Mab.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(Mab.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
        if (!this.dead && !this.isDeadOrDying()) {
            if (this.getState() == State.PHYSICAL) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("physical_attack", false));
                return PlayState.CONTINUE;
            } else if (this.getState() == State.SPECIAL) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("special_attack", false));
                return PlayState.CONTINUE;
            } else if (this.getState() == State.INTIMIDATE) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("intimidation", false));
                return PlayState.CONTINUE;
            }
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public enum State {
        IDLE, PHYSICAL, SPECIAL, INTIMIDATE
    }
}
