package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.intereface.ITameable;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class AbilityCheckingGoal extends Goal {

    private final FeyBase entity;
    private final boolean ability_on;
    private final Goal parent;

    public AbilityCheckingGoal(FeyBase entity, boolean ability_on, Goal parent) {
        this.entity = entity;
        this.ability_on = ability_on;
        this.parent = parent;
    }

    @Override
    public boolean canContinueToUse() {
        return this.entity.getAbilityActive() == this.ability_on && this.parent.canContinueToUse();
    }

    @Override
    public boolean isInterruptable() {
        return this.parent.isInterruptable();
    }

    @Override
    public void start() {
        this.parent.start();
    }

    @Override
    public void stop() {
        this.parent.stop();
    }

    @Override
    public void tick() {
        this.parent.tick();
    }

    @Override
    public void setFlags(@Nonnull EnumSet<Flag> flags) {
        this.parent.setFlags(flags);
    }

    @Nonnull
    @Override
    public EnumSet<Flag> getFlags() {
        return this.parent.getFlags();
    }

    @Override
    public boolean canUse() {
        return this.entity.getAbilityActive() == this.ability_on && this.parent.canUse();
    }
}
