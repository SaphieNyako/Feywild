package com.feywild.feywild.entity.goals.mab;

import com.feywild.feywild.entity.Mab;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class SummonVexGoal extends Goal {

    protected final Level level;
    protected final Mab entity;
    private int ticksLeft = 0;
    private Player target;

    public SummonVexGoal(Mab mab) {
        this.entity = mab;
        this.level = mab.level();
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
                this.summonVex(target);
                reset();
            } else if (this.ticksLeft <= 30) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
                this.entity.getNavigation().moveTo(this.target, 0.5);
            } else if (this.ticksLeft == 35) {
                this.spellCasting();
            }
        }
    }

    private void spellCasting() {
        this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.target.position());
        this.entity.setState(Mab.State.SPECIAL);
        this.entity.playSound(ModSoundEvents.spellcastingShort.getSoundEvent(), 1, 1);
    }

    private void summonVex(Player target) {
        Vex vex = new Vex(EntityType.VEX, this.level);
        Random random = new Random();
        vex.setPos(this.entity.getX() + (random.nextInt(3)), this.entity.getY() + (random.nextInt(3)), this.entity.getZ() + (random.nextInt(3)));
        vex.setTarget(target);
        this.level.addFreshEntity(vex);
        this.entity.playSound(ModSoundEvents.mabSummon.getSoundEvent(), 1, 1);
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

    private void reset() {
        this.entity.setState(Mab.State.IDLE);
        this.target = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canUse() {
        return this.entity.level().random.nextFloat() < 0.01f && (this.entity.getState() == Mab.State.IDLE || !(this.entity.getState() == Mab.State.INTIMIDATE) || !(this.entity.getState() == Mab.State.SPECIAL) || !(this.entity.getState() == Mab.State.PHYSICAL));
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
