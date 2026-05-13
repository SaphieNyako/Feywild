package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.SpriteEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SpriteHappyGoal extends Goal {

    protected final Level level;
    protected final SpriteEntity entity;
    private int ticksLeft = 0;

    public SpriteHappyGoal(SpriteEntity sprite) {
        this.entity = sprite;
        this.level = sprite.level;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 60) {
                this.emotion();

            }
        }
    }

    private void reset() {
        this.entity.setState(SpriteEntity.State.IDLE);
        this.ticksLeft = -1;
    }

    private void emotion() {
        this.entity.setState(SpriteEntity.State.HAPPY);
    }

    @Override
    public void start() {
        this.ticksLeft = 70;
    }


    @Override
    public boolean canUse() {
        return this.entity.getState() == SpriteEntity.State.IDLE
                && level.random.nextFloat() < 0.2f &&  this.entity.getState() != SpriteEntity.State.ANGRY;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}