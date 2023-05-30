package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.base.Fey;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class BoredCheckingGoal extends Goal {

    private final Fey entity;
    private final boolean shouldBeBored;
    private final Goal parent;

    public BoredCheckingGoal(Fey entity, boolean shouldBeBored, Goal parent) {
        this.entity = entity;
        this.shouldBeBored = shouldBeBored;
        this.parent = parent;
    }

    private boolean isBored() {
        return this.entity.getBored() < 1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.isBored() == this.shouldBeBored && this.parent.canContinueToUse();
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

    @Nonnull
    @Override
    public EnumSet<Flag> getFlags() {
        return this.parent.getFlags();
    }

    @Override
    public void setFlags(@Nonnull EnumSet<Flag> flags) {
        this.parent.setFlags(flags);
    }

    @Override
    public boolean canUse() {
        return this.isBored() == this.shouldBeBored && this.parent.canUse();
    }
}
