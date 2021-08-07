package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

public class RefreshStockGoal extends Goal {

    protected final DwarfBlacksmithEntity entity;

    public RefreshStockGoal(DwarfBlacksmithEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canContinueToUse() {
        return this.entity.shouldRestock();
    }

    @Override
    public boolean canUse() {
        return this.entity.level.isNight();
    }

    @Override
    public void start() {
        if (this.entity.shouldRestock()) {
            this.entity.restock();
        }
    }
}

