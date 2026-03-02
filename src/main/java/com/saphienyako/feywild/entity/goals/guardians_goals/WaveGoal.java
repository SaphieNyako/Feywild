package com.saphienyako.feywild.entity.goals.guardians_goals;

import com.saphienyako.feywild.entity.ShroomlingEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class WaveGoal extends Goal {

    protected final Level level;
    protected final ShroomlingEntity entity;
    private int ticksLeft = 0;
    private Player target;

    public WaveGoal(ShroomlingEntity shroomling) {
        this.entity = shroomling;
        this.level = shroomling.level;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 20) {
                this.waving();
                this.entity.playSound(entity.getWaveSound(), 1, 1);

            } else if (this.ticksLeft <= 20 && target != null) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
            }
        }
    }

    private void reset() {
        this.entity.setState(ShroomlingEntity.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    private void waving() {
        this.entity.setState(ShroomlingEntity.State.WAVE);
    }

    @Override
    public void start() {
        this.ticksLeft = 30;
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(4);
        for (Player match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }


    @Override
    public boolean canUse() {
        return this.entity.getState() == ShroomlingEntity.State.IDLE
                && level.random.nextFloat() < 0.005f &&  this.entity.getState() != ShroomlingEntity.State.SNEEZE;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
