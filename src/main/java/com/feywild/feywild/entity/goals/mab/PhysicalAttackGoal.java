package com.feywild.feywild.entity.goals.mab;

import com.feywild.feywild.entity.Mab;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class PhysicalAttackGoal extends Goal {

    private final Mab entity;
    private Player target;
    private int ticksLeft = 0;
    
    public PhysicalAttackGoal(Mab mab) {
        this.entity = mab;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            if (this.target == null) {
                this.reset();
                return;
            }
            this.ticksLeft--;
            if (this.ticksLeft <= 0) {
                this.reset();
                this.entity.playSound(ModSoundEvents.mabAttack.getSoundEvent(), 1, 1);
            } else if (this.ticksLeft == 33) {
                this.entity.playSound(ModSoundEvents.swordSwing.getSoundEvent(), 1, 1);
            } else if (this.ticksLeft == 35) {
                moveToPlayer();
                this.spellCasting();
                target.hurt(target.level().damageSources().mobAttack(this.entity), 2);
            }
        }
    }

    protected void reset() {
        this.entity.setState(Mab.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    @Override
    public void start() {
        this.ticksLeft = 36;
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(32);
        for (Player match : this.entity.level().getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    private void moveToPlayer() {
        Random random = new Random();
        this.entity.teleportTo(target.position().x + random.nextInt(5), target.position().y, target.position().z + random.nextInt(5));
        this.entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1, 1);
    }

    private void spellCasting() {
        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
        this.entity.setState(Mab.State.PHYSICAL);
    }

    @Override
    public boolean canUse() {
        return this.entity.level().random.nextFloat() < 0.02f && (this.entity.getState() == Mab.State.IDLE || !(this.entity.getState() == Mab.State.INTIMIDATE) || !(this.entity.getState() == Mab.State.SPECIAL) || !(this.entity.getState() == Mab.State.PHYSICAL));
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
