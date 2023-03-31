package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.base.TreeEntBase;
import net.minecraft.world.entity.ai.goal.Goal;

public class TreeEntResetTargetGoal<T extends TreeEntBase> extends Goal {

    private final T entity;

    public TreeEntResetTargetGoal(T entity) {
        this.entity = entity;
    }


    @Override
    public boolean canUse() {
        return this.entity.level.random.nextFloat() < 0.05f;
    }

    public void start() {
        this.entity.stopBeingAngry();
    }
}
