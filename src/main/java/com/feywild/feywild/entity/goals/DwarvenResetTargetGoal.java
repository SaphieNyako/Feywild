package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.DwarfBlacksmith;
import net.minecraft.world.entity.ai.goal.Goal;

public class DwarvenResetTargetGoal<T extends DwarfBlacksmith> extends Goal {

    private final T entity;

    public DwarvenResetTargetGoal(T entity) {
        this.entity = entity;
    }


    @Override
    public boolean canUse() {
        return this.entity.level.random.nextFloat() < 0.05f; // && this.entity.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER);
        // this.entity.getLastHurtByMob() != null && this.entity.getLastHurtByMobTimestamp() > lastHurtByPlayerTimestamp
    }

    public void start() {
        this.entity.stopBeingAngry();
    }
}
