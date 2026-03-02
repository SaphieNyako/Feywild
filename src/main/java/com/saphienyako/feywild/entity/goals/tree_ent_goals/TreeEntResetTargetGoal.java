package com.saphienyako.feywild.entity.goals.tree_ent_goals;

import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.world.entity.ai.goal.Goal;

public class TreeEntResetTargetGoal <T extends TreeEntBase> extends Goal {

    private final T entity;

    public TreeEntResetTargetGoal(T entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return this.entity.level.random.nextFloat() < 0.05f;
    }

    @Override
    public void start() {
        this.entity.stopBeingAngry();
    }
}
