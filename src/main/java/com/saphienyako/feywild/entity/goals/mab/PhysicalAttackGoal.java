package com.saphienyako.feywild.entity.goals.mab;

import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

public class PhysicalAttackGoal extends Goal {

    private final MabEntity entity;
    private int ticksLeft = 0;

    public PhysicalAttackGoal(MabEntity mab) {
        this.entity = mab;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {

        LivingEntity target = entity.getTarget();

        return target != null
                && target.isAlive()
                && entity.level.random.nextFloat() < 0.06F
                && entity.getState() != MabEntity.State.CHANNEL
                && entity.getState() != MabEntity.State.INTIMIDATION
                && entity.getState() != MabEntity.State.ATTACKING;
    }

    @Override
    public void start() {
        this.ticksLeft = 36;
        this.entity.setState(MabEntity.State.ATTACKING);
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

        if (ticksLeft == 35) {
            moveToTarget(target);
            spellCasting(target);
        }

        if (ticksLeft == 30) {
            target.hurt(DamageSource.mobAttack(entity),
                    6.0F
            );
        }

        if (ticksLeft == 33) {
            this.entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
            this.entity.playSound(ModSounds.MAB_ATTACK.get(), 1, 1);
        }
    }

    private void moveToTarget(LivingEntity target) {

        Vec3 behind = target.position().subtract(target.getLookAngle().scale(2.0));
        entity.teleportTo(behind.x, behind.y, behind.z);
        entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }

    private void spellCasting(LivingEntity target) {
        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
        entity.setState(MabEntity.State.ATTACKING);
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