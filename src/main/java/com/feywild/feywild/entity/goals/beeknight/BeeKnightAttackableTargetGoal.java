package com.feywild.feywild.entity.goals.beeknight;

import com.feywild.feywild.entity.BeeKnight;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class BeeKnightAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final BeeKnight beeKnight;

    public BeeKnightAttackableTargetGoal(BeeKnight beeKnight, Class<T> targetType, boolean mustSee) {
        super(beeKnight, targetType, mustSee);
        this.beeKnight = beeKnight;
    }

    @Override
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
