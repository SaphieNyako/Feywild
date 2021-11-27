package com.feywild.feywild.entity.goals;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.BeeKnightEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class BeeRestrictAttackGoal extends MeleeAttackGoal {

    private final BeeKnightEntity beeKnightEntity;

    public BeeRestrictAttackGoal(BeeKnightEntity creature, double speed, boolean visualContact) {
        super(creature, speed, visualContact);
        this.beeKnightEntity = creature;
    }

    @Override
    public boolean canUse() {
        return beeKnightEntity.isAngry() && beeKnightEntity.getTarget() != null && !beeKnightEntity.getTarget().isDeadOrDying();
    }

    @Override
    public void start() {
        super.start();
        if (beeKnightEntity.getCurrentPointOfInterest() == null) {
            beeKnightEntity.setCurrentTargetPos(beeKnightEntity.blockPosition());
        }
    }

    @Override
    public boolean canContinueToUse() {
        return beeKnightEntity.isAngry() && beeKnightEntity.getCurrentPointOfInterest() != null && beeKnightEntity.getCurrentPointOfInterest().closerThan(beeKnightEntity.position(), 2 * MobConfig.bee_knight.aggrevation_range) && beeKnightEntity.getTarget() != null && !beeKnightEntity.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        beeKnightEntity.setAngry(false);
    }
}
