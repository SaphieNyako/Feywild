package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.FlyingBossBase;
import com.feywild.feywild.entity.base.ISummonable;
import com.feywild.feywild.entity.goals.mab.IntimidateGoal;
import com.feywild.feywild.entity.goals.mab.PhysicalAttackGoal;
import com.feywild.feywild.entity.goals.mab.SummonVexGoal;
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
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Mab extends FlyingBossBase implements ISummonable {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(Mab.class, EntityDataSerializers.INT);
    public final Alignment alignment;
    @Nullable
    private BlockPos summonPos;

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
        return random.nextInt(3) == 0 ? ModSoundEvents.mabAmbience.getSoundEvent() : null;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return random.nextInt(3) == 0 ? ModSoundEvents.mabHurt.getSoundEvent() : null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.mabDeath.getSoundEvent();
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

    @Override
    public void die(@Nonnull DamageSource damageSource) {
        super.die(damageSource);
        this.remove(RemovalReason.KILLED);
    }

    @Nullable
    @Override
    public BlockPos getSummonPos() {
        return this.summonPos;
    }

    @Override
    public void setSummonPos(@Nullable BlockPos pos) {
        this.summonPos = pos == null ? null : pos.immutable();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (!this.dead && !this.isDeadOrDying()) {
                if (this.getState() == State.PHYSICAL) {
                    event.getController().setAnimation(RawAnimation.begin().thenPlay("physical_attack"));
                    return PlayState.CONTINUE;
                } else if (this.getState() == State.SPECIAL) {
                    event.getController().setAnimation(RawAnimation.begin().thenPlay("special_attack"));
                    return PlayState.CONTINUE;
                } else if (this.getState() == State.INTIMIDATE) {
                    event.getController().setAnimation(RawAnimation.begin().thenPlay("intimidation"));
                    return PlayState.CONTINUE;
                }
            }
            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("flying"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
            }
            return PlayState.CONTINUE;
        }));
    }

    public enum State {
        IDLE, PHYSICAL, SPECIAL, INTIMIDATE
    }
}
