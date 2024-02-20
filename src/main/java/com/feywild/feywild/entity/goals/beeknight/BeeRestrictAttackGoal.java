package com.feywild.feywild.entity.goals.beeknight;

import com.feywild.feywild.entity.BeeKnight;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class BeeRestrictAttackGoal extends MeleeAttackGoal {

    private final BeeKnight beeKnight;
    private int ticksLeft = 0;

    public BeeRestrictAttackGoal(BeeKnight creature, double speed, boolean visualContact) {
        super(creature, speed, visualContact);
        this.beeKnight = creature;
    }

    @Override
    public void start() {
        this.ticksLeft = 300;
    }

    @Override
    public void stop() {
        super.stop();
        this.beeKnight.setAngry(false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.ticksLeft > 0) {
            this.ticksLeft--;
        }
        if (this.ticksLeft <= 0) {
            beeKnight.setAngry(false);
            this.ticksLeft = -1;
        } else if (this.ticksLeft <= 300 && beeKnight.getTarget() != null) {
            this.beeKnight.lookAt(EntityAnchorArgument.Anchor.EYES, this.beeKnight.getTarget().position());
            this.beeKnight.getNavigation().moveTo(this.beeKnight.getTarget(), 1.0);
        }
    }

    @Override
    public boolean canUse() {
        return beeKnight.isAngry() && beeKnight.getTarget() != null && !beeKnight.getTarget().isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        return beeKnight.isAngry() && beeKnight.getTarget() != null && !beeKnight.getTarget().isDeadOrDying();
    }
}
