package com.saphienyako.feywild.entity.goals.tree_ent_goals;

import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import javax.annotation.Nonnull;

public class TreeEntMeleeAttackGoal  extends MeleeAttackGoal {
    protected final TreeEntBase entity;

    public TreeEntMeleeAttackGoal(TreeEntBase entity, double speedModifier, boolean followEvenIfNotSeen) {
        super(entity, speedModifier, followEvenIfNotSeen);
        this.entity = entity;
    }

    //TODO does this work?
    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.mob.getTarget();
        if (target != null && this.canTreeEntAttack(target)) {
            this.resetAttackCooldown();
            entity.playSound(entity.getAttackingSound(), 0.3f, 1f);
            this.mob.doHurtTarget(target);
        }
    }

    protected boolean canTreeEntAttack(LivingEntity target) {
        double distSqr = this.mob.distanceToSqr(target);
        double reachSqr = this.getTreeEntAttackReachSqr(target);
        return distSqr <= reachSqr;
    }

    protected double getTreeEntAttackReachSqr(LivingEntity target) {
        float width = this.mob.getBbWidth();
        float reach = width * 3.0F;
        return (double)(reach * reach + target.getBbWidth());
    }

    @Override
    public void start() {
        this.entity.setState(TreeEntBase.State.ATTACK);
        super.start();
    }

    @Override
    public void stop() {
        this.entity.setState(TreeEntBase.State.IDLE);
        super.stop();
    }
}
