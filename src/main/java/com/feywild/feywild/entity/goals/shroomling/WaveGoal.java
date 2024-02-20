package com.feywild.feywild.entity.goals.shroomling;

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
    protected final Shroomling entity;
    private int ticksLeft = 0;
    private Player target;

    public WaveGoal(Shroomling shroomling) {
        this.entity = shroomling;
        this.level = shroomling.level();
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 40) {
                this.waving();
                this.entity.playSound(getWaveSound(), 1, 1);

            } else if (this.ticksLeft <= 40 && target != null) {
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
        return ModSoundEvents.shroomlingWave.getSoundEvent();
    }

    private void waving() {
        this.entity.setState(Shroomling.State.WAVING);
    }

    @Override
    public void start() {
        this.ticksLeft = 50;
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(4);
        for (Player match : this.entity.level().getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    @Override
    public boolean canUse() {
        return level.random.nextFloat() < 0.01f && !(this.entity.getState() == Shroomling.State.SNEEZING);
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0 && !(this.entity.getState() == Shroomling.State.SNEEZING);
    }
}
