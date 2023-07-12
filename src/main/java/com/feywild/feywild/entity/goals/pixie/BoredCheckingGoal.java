package com.feywild.feywild.entity.goals.pixie;

import com.feywild.feywild.entity.base.Pixie;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class BoredCheckingGoal extends Goal {

    private final Pixie entity;
    private final boolean shouldBeBored;
    private final Goal parent;

    public BoredCheckingGoal(Pixie entity, boolean shouldBeBored, Goal parent) {
        this.entity = entity;
        this.shouldBeBored = shouldBeBored;
        this.parent = parent;
    }

    private boolean isBored() {
        return this.entity.getBoredom() == Pixie.MAX_BOREDOM;
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
