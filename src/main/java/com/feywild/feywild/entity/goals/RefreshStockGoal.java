package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;

public class RefreshStockGoal extends Goal {

    protected final World worldLevel;
    protected DwarfBlacksmithEntity entity;

    public RefreshStockGoal(DwarfBlacksmithEntity entity) {
        this.entity = entity;
        this.worldLevel = entity.level;

    }

    @Override
    public boolean canContinueToUse() {
        //boolean restocked
        return entity.shouldRestock();
    }

    @Override
    public boolean canUse() {
        return worldLevel.isNight();
    }

    @Override
    public void start() {
        if (entity.shouldRestock()) {
            entity.restock();
        }
    }
}

