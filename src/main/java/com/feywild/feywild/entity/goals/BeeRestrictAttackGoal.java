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
        return beeKnight.isAggravated() && beeKnight.getTarget() != null && !beeKnight.getTarget().isDeadOrDying();
    }

    @Override
    public void start() {
        super.start();
        if (beeKnight.getTreasurePos() == null) {
            beeKnight.setTreasurePos(beeKnight.blockPosition());
        }
    }

    @Override
    public boolean canContinueToUse() {
        return beeKnight.isAggravated() && beeKnight.getTreasurePos() != null && beeKnight.getTreasurePos().closerThan(beeKnight.blockPosition(), 2 * MobConfig.bee_knight.aggrevation_range) && beeKnight.getTarget() != null && !beeKnight.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        beeKnight.setAggravated(false);
    }
}
