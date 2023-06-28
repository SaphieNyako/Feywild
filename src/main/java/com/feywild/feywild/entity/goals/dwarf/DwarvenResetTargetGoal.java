package com.feywild.feywild.entity.goals.dwarf;

import com.feywild.feywild.entity.DwarfBlacksmith;
import net.minecraft.world.entity.ai.goal.Goal;

public class DwarvenResetTargetGoal<T extends DwarfBlacksmith> extends Goal {

    private final T entity;

    public DwarvenResetTargetGoal(T entity) {
        this.entity = entity;
    }
    
    @Override
    public boolean canUse() {
        return this.entity.level().random.nextFloat() < 0.05f;
    }

    @Override
    public void start() {
        this.entity.stopBeingAngry();
    }
}
