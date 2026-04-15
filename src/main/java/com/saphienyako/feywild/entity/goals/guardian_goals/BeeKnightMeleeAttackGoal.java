package com.saphienyako.feywild.entity.goals.guardian_goals;

import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class BeeKnightMeleeAttackGoal extends MeleeAttackGoal {

    protected final BeeKnightEntity entity;

    public BeeKnightMeleeAttackGoal(BeeKnightEntity entity, double speedModifier, boolean followEvenIfNotSeen) {
        super(entity, speedModifier, followEvenIfNotSeen);
        this.entity = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.isTimeToAttack() && this.canTreeEntAttack(target)) {
            this.resetAttackCooldown();

            if (!target.level().isClientSide) {
                if (entity.getRandom().nextFloat() < 0.4F) {
                    entity.playSound(entity.getAttackSound());
                }
            }

            this.mob.doHurtTarget(target);

            if (!target.level().isClientSide) {
                if (entity.getRandom().nextFloat() < 0.2F) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
                }
            }
        }
    }

    protected boolean canTreeEntAttack(LivingEntity target) {
        double distSqr = this.mob.distanceToSqr(target);
        double reachSqr = this.getTreeEntAttackReachSqr(target);
        return distSqr <= reachSqr;
    }

    protected double getTreeEntAttackReachSqr(LivingEntity target) {
        float width = this.mob.getBbWidth();
        float reach = width * 3.5F;
        return (double)(reach * reach + target.getBbWidth());
    }

    @Override
    public void start() {
        entity.setState(BeeKnightEntity.State.ATTACK);
        super.start();
    }

    @Override
    public void stop() {
        this.entity.setState(BeeKnightEntity.State.IDLE);
        super.stop();
    }

}
