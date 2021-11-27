package com.feywild.feywild.entity.goals;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.BeeKnight;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class BeeRestrictAttackGoal extends MeleeAttackGoal {

    private final BeeKnight beeKnight;

    public BeeRestrictAttackGoal(BeeKnight creature, double speed, boolean visualContact) {
        super(creature, speed, visualContact);
        this.beeKnight = creature;
    }

    @Override
    public boolean canUse() {
        return beeKnight.isAngry() && beeKnight.getTarget() != null && !beeKnight.getTarget().isDeadOrDying();
    }

    @Override
    public void start() {
        super.start();
        if (beeKnight.getCurrentPointOfInterest() == null) {
            beeKnight.setCurrentTargetPos(beeKnight.blockPosition());
        }
    }

    @Override
    public boolean canContinueToUse() {
        return beeKnight.isAngry() && beeKnight.getCurrentPointOfInterest() != null && beeKnight.getCurrentPointOfInterest().closerThan(beeKnight.position(), 2 * MobConfig.bee_knight.aggrevation_range) && beeKnight.getTarget() != null && !beeKnight.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        beeKnight.setAngry(false);
    }
}
