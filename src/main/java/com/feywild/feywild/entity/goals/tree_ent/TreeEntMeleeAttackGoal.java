package com.feywild.feywild.entity.goals.tree_ent;

import com.feywild.feywild.entity.SpringTreeEnt;
import com.feywild.feywild.entity.base.TreeEnt;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import javax.annotation.Nonnull;

public class TreeEntMeleeAttackGoal extends MeleeAttackGoal {

    protected final TreeEnt entity;

    public TreeEntMeleeAttackGoal(TreeEnt entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
        this.entity = entity;
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= d0 + 4 && getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
        }
    }

    @Override
    public void start() {
        this.entity.setState(SpringTreeEnt.State.ATTACKING);
        this.entity.playSound(ModSoundEvents.treeEntAttacking.getSoundEvent(), 0.7f, 1);
        super.start();
    }

    @Override
    public void stop() {
        this.entity.setState(SpringTreeEnt.State.IDLE);
        super.stop();
    }
}
