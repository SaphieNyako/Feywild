package com.saphienyako.feywild.entity.goals.mab;

import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class IntimidateGoal extends Goal {

    private final MabEntity entity;
    private int ticksLeft;

    public IntimidateGoal(MabEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();

        return target != null
                && target.isAlive()
                && entity.getRandom().nextFloat() < 0.04F
                && entity.getState() != MabEntity.State.CHANNEL
                && entity.getState() != MabEntity.State.INTIMIDATION
                && entity.getState() != MabEntity.State.ATTACKING;
    }

    @Override
    public void start() {
        this.ticksLeft = 36;
        this.entity.setState(MabEntity.State.INTIMIDATION);
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
            reset();
            return;
        }

        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());

        if (ticksLeft == 30) {
            spellCasting(target);
        }
    }

    private void spellCasting(LivingEntity target) {

        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
        this.entity.playSound(ModSounds.MAB_INTIMIDATE.get(), 1, 1);
        if (target instanceof Player player) {
            player.setTicksFrozen(180);
        }

        target.addEffect(
                new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        180,
                        3
                )
        );
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = entity.getTarget();

        return ticksLeft > 0
                && target != null
                && target.isAlive();
    }

    @Override
    public void stop() {
        reset();
    }

    private void reset() {
        entity.setState(MabEntity.State.IDLE_FLYING);
        ticksLeft = 0;
    }
}
