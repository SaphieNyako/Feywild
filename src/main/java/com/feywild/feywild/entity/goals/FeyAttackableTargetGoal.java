package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.base.IOwnable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class FeyAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public FeyAttackableTargetGoal(Mob mob, Class<T> target, boolean requiresLineOfSight) {
        super(mob, target, requiresLineOfSight);
        targetConditions.selector(entity -> !(entity instanceof Player p) || !p.isCreative());
        if (mob instanceof IOwnable) {
            targetConditions.selector(entity -> !Objects.equals(entity.getUUID(), ((IOwnable) mob).getOwner()));
        }
    }
}
