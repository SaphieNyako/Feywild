package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.base.FeyBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;


public class FeyAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private FeyBase user;

    public FeyAttackableTargetGoal(MobEntity p_i50313_1_, Class<T> p_i50313_2_, boolean p_i50313_3_) {
        super(p_i50313_1_, p_i50313_2_, p_i50313_3_);
        if(p_i50313_1_ instanceof FeyBase)
            this.user = (FeyBase) p_i50313_1_;
            targetConditions.selector(entity -> entity.getUUID() != user.getOwnerId());
    }
}
