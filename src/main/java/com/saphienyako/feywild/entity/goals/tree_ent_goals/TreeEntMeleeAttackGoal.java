package com.saphienyako.feywild.entity.goals.tree_ent_goals;

import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import javax.annotation.Nonnull;

public class TreeEntMeleeAttackGoal  extends MeleeAttackGoal {
    protected final TreeEntBase entity;
    private TreeEntBase.State lastState = TreeEntBase.State.IDLE;

    public TreeEntMeleeAttackGoal(TreeEntBase entity, double speedModifier, boolean followEvenIfNotSeen) {
        super(entity, speedModifier, followEvenIfNotSeen);
        this.entity = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.isTimeToAttack() && this.canTreeEntAttack(target)) {
            this.resetAttackCooldown();

            this.mob.doHurtTarget(target);
        }
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity target = this.mob.getTarget();

        if (target != null) {
            double dist = this.mob.distanceToSqr(target);
            double reach = this.getTreeEntAttackReachSqr(target);

            if (dist <= reach * 2.0) {
                entity.setState(TreeEntBase.State.ATTACK);
            }
        }

        if (entity.getState() != lastState) {

            if (entity.getState() == TreeEntBase.State.ATTACK) {
                entity.playSound(entity.getAttackingSound(), 0.3f,1f);
            }
            lastState = entity.getState();
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
        super.start();
    }

    @Override
    public void stop() {
        this.entity.setState(TreeEntBase.State.IDLE);
        super.stop();
    }
}
