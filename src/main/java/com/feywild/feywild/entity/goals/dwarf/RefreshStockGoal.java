package com.feywild.feywild.entity.goals.dwarf;

import com.feywild.feywild.entity.DwarfBlacksmith;
import net.minecraft.world.entity.ai.goal.Goal;

public class RefreshStockGoal extends Goal {

    protected final DwarfBlacksmith entity;

    public RefreshStockGoal(DwarfBlacksmith entity) {
        this.entity = entity;
    }

    @Override
    public boolean canContinueToUse() {
        return this.entity.shouldRestock();
    }

    @Override
    public boolean canUse() {
        return this.entity.level().isNight();
    }

    @Override
    public void start() {
        if (this.entity.shouldRestock()) {
            this.entity.restock();
        }
    }
}
