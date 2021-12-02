package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.Shroomling;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class WaveGoal extends Goal {

    protected final Level level;
    protected Shroomling entity;
    private int ticksLeft = 0;

    public WaveGoal(Shroomling shroomling) {
        this.entity = shroomling;
        this.level = shroomling.level;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 80) {
                this.waving();
                this.entity.playSound(SoundEvents.VILLAGER_CELEBRATE, 1, 1);
            }
        }
    }

    private void reset() {
        this.entity.setState(Shroomling.State.IDLE);
        this.ticksLeft = -1;
    }

    private void waving() {this.entity.setState(Shroomling.State.WAVING);}

    @Override
    public void start() {
        this.ticksLeft = 90;
    }

    @Override
    public boolean canUse() {
        return level.random.nextFloat() < 0.003f;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
