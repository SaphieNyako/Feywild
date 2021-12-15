package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.ShroomlingEntity;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class WaveGoal extends Goal {

    protected final World world;
    protected ShroomlingEntity entity;
    private int ticksLeft = 0;
    private PlayerEntity target;

    public WaveGoal(ShroomlingEntity shroomling) {
        this.entity = shroomling;
        this.world = shroomling.level;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 80) {
                this.waving();
                this.entity.playSound(getWaveSound(), 1, 1);

            } else if (this.ticksLeft <= 80 && target != null) {
                this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.target.position());
            }
        }
    }

    private void reset() {
        this.entity.setState(ShroomlingEntity.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    protected SoundEvent getWaveSound() {
        return ModSoundEvents.shroomlingWave;
    }

    private void waving() {this.entity.setState(ShroomlingEntity.State.WAVING);}

    @Override
    public void start() {
        this.ticksLeft = 90;
        this.target = null;
        AxisAlignedBB box = new AxisAlignedBB(this.entity.blockPosition()).inflate(4);
        for (PlayerEntity match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    @Override
    public boolean canUse() {
        return world.random.nextFloat() < 0.003f
                && !(this.entity.getState() == ShroomlingEntity.State.SNEEZING);
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0
                && !(this.entity.getState() == ShroomlingEntity.State.SNEEZING);
    }

}
