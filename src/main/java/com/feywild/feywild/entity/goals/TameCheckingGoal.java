package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.base.ITameable;
import net.minecraft.entity.ai.goal.Goal;

import javax.annotation.Nonnull;
import java.util.EnumSet;

// Only use for vanilla goals
public class TameCheckingGoal extends Goal {
    
    private final ITameable entity;
    private final boolean tamed;
    private final Goal parent;

    public TameCheckingGoal(ITameable entity, boolean tamed, Goal parent) {
        this.entity = entity;
        this.tamed = tamed;
        this.parent = parent;
    }

    @Override
    public boolean canContinueToUse() {
        return this.entity.isTamed() == this.tamed && this.parent.canContinueToUse();
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
        return this.entity.isTamed() == this.tamed && this.parent.canUse();
    }
}
