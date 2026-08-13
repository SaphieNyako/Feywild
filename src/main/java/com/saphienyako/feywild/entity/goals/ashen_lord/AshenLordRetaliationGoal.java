package com.saphienyako.feywild.entity.goals.ashen_lord;

import com.saphienyako.feywild.entity.AshenLordEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class AshenLordRetaliationGoal extends Goal {

    private static final int RETALIATION_COOLDOWN = 20 * 8;

    private final AshenLordEntity entity;

    private int cooldownTicks;

    public AshenLordRetaliationGoal(AshenLordEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        if (cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        if (!entity.hasRetaliationRequest()) {
            return false;
        }

        LivingEntity target = entity.getTarget();

        return target != null
                && target.isAlive()
                && entity.getState() != AshenLordEntity.State.CHANNEL;
    }

    @Override
    public void start() {
        AshenLordEntity.ForcedAbility[] abilities = AshenLordEntity.ForcedAbility.values();
        AshenLordEntity.ForcedAbility selected = abilities[entity.getRandom().nextInt(abilities.length)];

        entity.setForcedAbility(selected);
        entity.consumeRetaliationRequest();

        cooldownTicks = RETALIATION_COOLDOWN;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}