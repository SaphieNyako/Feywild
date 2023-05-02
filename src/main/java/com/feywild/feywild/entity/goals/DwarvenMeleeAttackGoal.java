package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import javax.annotation.Nonnull;

public class DwarvenMeleeAttackGoal extends MeleeAttackGoal {

    protected final DwarfBlacksmith entity;

    public DwarvenMeleeAttackGoal(DwarfBlacksmith entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.entity = entity;
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= d0 + 2 && getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
            this.entity.playSound(ModSoundEvents.dwarfAttack, 0.7f, 1);
        }
    }

    @Override
    public void start() {
        this.entity.setState(DwarfBlacksmith.State.ATTACKING);
        super.start();
    }

    @Override
    public void stop() {
        this.entity.setState(DwarfBlacksmith.State.IDLE);
        super.stop();
    }
}
