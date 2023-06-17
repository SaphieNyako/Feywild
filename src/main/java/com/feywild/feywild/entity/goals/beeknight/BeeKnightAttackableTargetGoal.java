package com.feywild.feywild.entity.goals.beeknight;

import com.feywild.feywild.entity.BeeKnight;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class BeeKnightAttackableTargetGoal extends NearestAttackableTargetGoal {

    BeeKnight beeKnight;

    public BeeKnightAttackableTargetGoal(BeeKnight beeKnight, Class targetType, boolean mustSee) {
        super(beeKnight, targetType, mustSee);
        this.beeKnight = beeKnight;
    }


    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.findTarget();
            if (this.target != null) {
                beeKnight.setAngry(true);
            }
            return this.target != null;
        }
    }
}
