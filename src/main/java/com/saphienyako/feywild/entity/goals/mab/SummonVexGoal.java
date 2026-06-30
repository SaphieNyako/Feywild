package com.saphienyako.feywild.entity.goals.mab;

import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.Random;

public class SummonVexGoal extends Goal {

    protected final Level level;
    protected final MabEntity entity;
    private int ticksLeft = 0;

    public SummonVexGoal(MabEntity mab) {
        this.entity = mab;
        this.level = mab.level();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {

        LivingEntity target = entity.getTarget();

        return target != null
                && target.isAlive()
                && entity.level().random.nextFloat() < 0.03F
                && entity.getState() != MabEntity.State.CHANNEL
                && entity.getState() != MabEntity.State.INTIMIDATION
                && entity.getState() != MabEntity.State.ATTACKING;
    }

    @Override
    public void start() {
        this.ticksLeft = 36;
        this.entity.setState(MabEntity.State.CHANNEL);
    }

    @Override
    public void tick() {

        LivingEntity target = entity.getTarget();

        if (target == null || !target.isAlive()) {
            reset();
            return;
        }

        ticksLeft--;

        if (ticksLeft <= 0) {
            summonVex(target);
            reset();
            return;
        }

        if (ticksLeft == 35) {
            spellCasting(target);
        }

        if (ticksLeft <= 30) {
            entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
            entity.getNavigation().moveTo(target, 0.5D);
        }
    }

    private void spellCasting(LivingEntity target) {
        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
        entity.setState(MabEntity.State.CHANNEL);
    }

    private void summonVex(LivingEntity target) {
        Vex vex = new Vex(EntityType.VEX, this.level);
        Random random = new Random();
        vex.setPos(this.entity.getX() + (random.nextInt(3)), this.entity.getY() + (random.nextInt(3)), this.entity.getZ() + (random.nextInt(3)));

        this.level.addFreshEntity(vex);
        vex.setOwner(this.entity);
        vex.setLimitedLife(1200);
        vex.setTarget(target);
        vex.setAggressive(true);
        this.entity.playSound(ModSounds.MAB_SUMMON.get(), 1, 1);
    }

    private void reset() {
        entity.setState(MabEntity.State.IDLE_FLYING);
        ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return ticksLeft > 0;
    }
}