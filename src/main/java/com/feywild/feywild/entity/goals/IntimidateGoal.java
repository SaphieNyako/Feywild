package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.Mab;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class IntimidateGoal extends Goal {

    private final Mab entity;
    private Player target;
    private int ticksLeft = 0;

    public IntimidateGoal(Mab mab) {
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
                this.entity.playSound(ModSoundEvents.iceCracking, 1, 1);
            } else if (this.ticksLeft <= 34) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
            } else if (this.ticksLeft == 35) {
                this.spellCasting();
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 36;
        this.target = null;
        AABB box = new AABB(this.entity.blockPosition()).inflate(32);
        for (Player match : this.entity.level.getEntities(EntityType.PLAYER, box, e -> !e.isSpectator())) {
            this.target = match;
            break;
        }
    }

    protected void reset() {
        this.entity.setState(Mab.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    private void spellCasting() {
        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
        this.entity.setState(Mab.State.INTIMIDATE);
        this.target.setTicksFrozen(180);
        this.target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Math.max(120, 180), 3));
        this.entity.playSound(ModSoundEvents.mabIntimidate, 1, 1);
    }


    @Override
    public boolean canUse() {
        return this.entity.level.random.nextFloat() < 0.02f && (this.entity.getState() == Mab.State.IDLE || !(this.entity.getState() == Mab.State.INTIMIDATE) || !(this.entity.getState() == Mab.State.SPECIAL) || !(this.entity.getState() == Mab.State.PHYSICAL));
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
