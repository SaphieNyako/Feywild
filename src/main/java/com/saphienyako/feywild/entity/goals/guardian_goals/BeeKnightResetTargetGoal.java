package com.saphienyako.feywild.entity.goals.guardian_goals;

import com.saphienyako.feywild.entity.BeeKnightEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class BeeKnightResetTargetGoal <T extends BeeKnightEntity> extends Goal {

    private final T entity;

    public BeeKnightResetTargetGoal(T entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return this.entity.level().getRandom().nextFloat() < 0.02f;
    }

    @Override
    public void start() {
        this.entity.stopBeingAngry();
    }
}
