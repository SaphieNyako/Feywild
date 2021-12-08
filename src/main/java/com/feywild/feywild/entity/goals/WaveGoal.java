package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.Shroomling;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class WaveGoal extends Goal {

    protected final Level level;
    protected Shroomling entity;
    private int ticksLeft = 0;
    private Player target;

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
                this.entity.playSound(getWaveSound(), 1, 1);

            } else if (this.ticksLeft <= 80 && target != null) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
            }
        }
    }

    private void reset() {
        this.entity.setState(Shroomling.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    protected SoundEvent getWaveSound() {
        return this.level.random.nextBoolean() ? ModSoundEvents.shroomlingWave01 : ModSoundEvents.shroomlingWave02;
    }

    private void waving() {this.entity.setState(Shroomling.State.WAVING);}

    @Override
    public void start() {
        this.ticksLeft = 90;
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(4);
        for (Player match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    @Override
    public boolean canUse() {
        return level.random.nextFloat() < 0.003f
                && !(this.entity.getState() == Shroomling.State.SNEEZING);
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0
                && !(this.entity.getState() == Shroomling.State.SNEEZING);
    }
}
